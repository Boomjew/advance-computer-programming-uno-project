package game;

import cards.Card;
import patterns.observer.GameEventManager;
import patterns.observer.ScoreTracker;
import patterns.observer.TurnLogger;
import patterns.singleton.Deck;
import players.Player;

import java.util.List;

/**
 * UnoGame is the central game engine.
 *
 * It ties together ALL four design patterns:
 *   - Singleton  : uses Deck.getInstance()
 *   - Factory    : deck was already built by CardFactory inside Deck
 *   - Observer   : fires events through GameEventManager
 *   - Strategy   : each AIPlayer already carries its PlayStrategy
 *
 * OOP: Encapsulation — game loop logic is hidden inside this class.
 */
public class UnoGame {

    private List<Player>      players;
    private GameState         state;
    private GameEventManager  eventManager;
    private ScoreTracker      scoreTracker;
    private boolean           unoCallEnabled;

    private static final int STARTING_HAND_SIZE = 7;

    public UnoGame(List<Player> players) {
        this.players       = players;
        this.eventManager  = new GameEventManager();
        this.scoreTracker  = new ScoreTracker();
        this.unoCallEnabled = true;

        // Register observers (Observer pattern)
        eventManager.addObserver(new TurnLogger());
        eventManager.addObserver(scoreTracker);
    }

    // -------------------------------------------------------
    //  Public API
    // -------------------------------------------------------

    /** Play a complete game (single round) and return the winner. */
    public Player playGame() {
        setup();
        return gameLoop();
    }

    /** Print the cumulative scoreboard. */
    public void printScoreboard() {
        scoreTracker.printScoreboard();
    }

    // -------------------------------------------------------
    //  Setup
    // -------------------------------------------------------

    private void setup() {
        // Singleton pattern: reset and get the one deck
        Deck.reset();

        state = new GameState(players);

        // Deal 7 cards to each player
        for (Player p : players) {
            p.getHand().clear();
            p.drawCards(STARTING_HAND_SIZE);
        }

        // Flip the first card onto the discard pile
        Card firstCard = Deck.getInstance().draw();
        // Re-draw if first card is a Wild
        while (firstCard.getColor().equals("WILD")) {
            Deck.getInstance().discard(firstCard);
            firstCard = Deck.getInstance().draw();
        }
        Deck.getInstance().discard(firstCard);

        printSeparator("GAME START");
        System.out.println("Players: ");
        for (Player p : players)
            System.out.println("  - " + p.getName());
        System.out.println("Starting card: " + firstCard);
        printSeparator("");
    }

    // -------------------------------------------------------
    //  Main Loop
    // -------------------------------------------------------

    private Player gameLoop() {
        int maxRounds = 300;   // safety cap to prevent infinite loops
        int round     = 0;

        while (!state.isGameOver() && round < maxRounds) {
            round++;
            Player current = state.getCurrentPlayer();

            // Notify observers: turn changed
            eventManager.notifyTurnChanged(current);

            // Player takes their turn
            Card played = current.takeTurn(state);

            if (played != null) {
                // Place card on discard pile
                state.discardCard(played);

                // Notify observers: card played
                eventManager.notifyCardPlayed(current, played);

                // Apply the card's effect (advances turn internally)
                played.applyEffect(state);

                // Check if the player just won
                state.checkWinCondition();
                if (state.isGameOver()) break;

                // UNO call check — if next player has 1 card, AI can call
                checkUno(current);

            } else {
                // Player drew a card
                eventManager.notifyCardDrawn(current, 1);
                state.advanceTurn();
            }
        }

        Player winner = state.getWinner();
        if (winner == null) {
            System.out.println("Round ended (max rounds reached).");
            winner = findPlayerWithLeastCards();
        }

        eventManager.notifyGameEnd(winner);
        printSeparator("GAME OVER — Winner: " + winner.getName());
        return winner;
    }

    // -------------------------------------------------------
    //  UNO call logic
    // -------------------------------------------------------

    private void checkUno(Player justPlayed) {
        if (!unoCallEnabled) return;
        for (Player p : players) {
            if (p != justPlayed && p.getHandSize() == 1) {
                // Any OTHER player can call UNO on them
                for (Player caller : players) {
                    if (caller != p) {
                        eventManager.notifyUnoCallout(caller, p);
                        System.out.println("  [UNO!] " + p.getName()
                            + " has 1 card left! " + caller.getName() + " calls UNO!");
                        break;
                    }
                }
            }
        }
    }

    // -------------------------------------------------------
    //  Helpers
    // -------------------------------------------------------

    private Player findPlayerWithLeastCards() {
        Player best = players.get(0);
        for (Player p : players)
            if (p.getHandSize() < best.getHandSize()) best = p;
        return best;
    }

    private void printSeparator(String label) {
        System.out.println("\n==========================================");
        if (!label.isEmpty()) System.out.println("  " + label);
        System.out.println("==========================================\n");
    }
}
