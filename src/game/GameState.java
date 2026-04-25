package game;

import cards.Card;
import patterns.singleton.Deck;
import players.Player;
import java.util.List;

/**
 * GameState holds all mutable state for one round of UNO.
 * Passed into card effects so they can modify the game
 * without needing a direct reference to UnoGame.
 *
 * OOP: Encapsulation — all state is private; mutated only
 *      through well-defined methods.
 */
public class GameState {

    private List<Player> players;
    private int          currentIndex;
    private int          direction;      // +1 clockwise, -1 counter-clockwise
    private Deck         deck;
    private boolean      gameOver;
    private Player       winner;

    public GameState(List<Player> players) {
        this.players      = players;
        this.currentIndex = 0;
        this.direction    = 1;
        this.deck         = Deck.getInstance();
        this.gameOver     = false;
    }

    // -------------------------------------------------------
    //  Accessors
    // -------------------------------------------------------

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public Card getTopCard() {
        return deck.peekTopDiscard();
    }

    public List<Player> getPlayers() { return players; }

    public boolean isGameOver()   { return gameOver; }
    public Player  getWinner()    { return winner; }
    public int     getDirection() { return direction; }

    // -------------------------------------------------------
    //  Turn control (called by card effects)
    // -------------------------------------------------------

    /** Move to the next player in the current direction. */
    public void advanceTurn() {
        currentIndex = nextIndex(1);
    }

    /** Skip the next player (advance TWO steps). */
    public void skipNextPlayer() {
        currentIndex = nextIndex(2);
    }

    /** Flip the play direction. */
    public void reverseDirection() {
        direction = -direction;
    }

    /** Force the next player to draw n cards (without consuming their turn). */
    public void drawCardsForNext(int n) {
        int targetIndex = nextIndex(1);
        players.get(targetIndex).drawCards(n);
        System.out.println("  >> " + players.get(targetIndex).getName()
            + " was forced to draw " + n + " card(s).");
    }

    // -------------------------------------------------------
    //  Discard
    // -------------------------------------------------------

    public void discardCard(Card card) {
        deck.discard(card);
    }

    // -------------------------------------------------------
    //  Win detection
    // -------------------------------------------------------

    public void checkWinCondition() {
        for (Player p : players) {
            if (p.getHandSize() == 0) {
                gameOver = true;
                winner   = p;
                return;
            }
        }
    }

    // -------------------------------------------------------
    //  Helpers
    // -------------------------------------------------------

    /** Compute the index that is 'steps' turns away in direction. */
    private int nextIndex(int steps) {
        int n = players.size();
        return ((currentIndex + direction * steps) % n + n) % n;
    }

    public Player peekNextPlayer() {
        return players.get(nextIndex(1));
    }
}
