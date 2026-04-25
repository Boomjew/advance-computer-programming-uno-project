import game.UnoGame;
import patterns.strategy.AggressiveStrategy;
import patterns.strategy.DefensiveStrategy;
import players.AIPlayer;
import players.HumanPlayer;
import players.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * ============================================================
 *  UNO CARD GAME — Human vs AI
 * ============================================================
 *  YOU play as a Human against 2 AI opponents.
 *  Type your move in the terminal each turn.
 *
 *  Design Patterns demonstrated:
 *    1. Factory   → CardFactory builds all card types
 *    2. Observer  → TurnLogger & ScoreTracker observe events
 *    3. Strategy  → AI players use different play strategies
 *    4. Singleton → Deck has exactly one instance per game
 *
 * ============================================================
 */
public class Main2 {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        printBanner();

        // Ask the human for their name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) playerName = "Player";

        // ---------------------------------------------------------
        // Create players: 1 Human + 2 AI with different strategies
        // ---------------------------------------------------------
        HumanPlayer human = new HumanPlayer(playerName);
        AIPlayer    alice = new AIPlayer("Alice (Aggressive)", new AggressiveStrategy());
        AIPlayer    bob   = new AIPlayer("Bob (Defensive)",    new DefensiveStrategy());

        List<Player> players = Arrays.asList(human, alice, bob);

        System.out.println("\nPlayers in this game:");
        System.out.println("  >> " + playerName + "  <- YOU (Human)");
        System.out.println("  >> Alice  (Aggressive AI - targets you with draw cards)");
        System.out.println("  >> Bob    (Defensive AI  - saves wild cards)");
        System.out.println("\nHOW TO PLAY:");
        System.out.println("  - When it is YOUR turn, type the INDEX number of the card to play");
        System.out.println("  - Type -1 to draw a card from the deck instead");
        System.out.println("  - After playing a Wild, you will be asked to choose a color");
        System.out.println("\nPress ENTER to start...");
        scanner.nextLine();

        // ---------------------------------------------------------
        // Game loop — play rounds until the human quits
        // ---------------------------------------------------------
        UnoGame game = new UnoGame(players);
        int round = 1;

        while (true) {
            System.out.println("\n========================================");
            System.out.println("              ROUND " + round);
            System.out.println("========================================");

            Player winner = game.playGame();

            System.out.println("\n>> Winner of Round " + round + ": " + winner.getName());

            if (winner.getName().equals(playerName)) {
                System.out.println("Congratulations! You won this round!");
            } else {
                System.out.println("Better luck next round!");
            }

            game.printScoreboard();

            System.out.print("\nPlay another round? (yes / no): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            if (answer.equals("no") || answer.equals("n")) break;

            round++;
        }

        System.out.println("\nThanks for playing UNO! Goodbye!");
        scanner.close();
    }

    private static void printBanner() {
        System.out.println("==========================================");
        System.out.println("           UNO CARD GAME                 ");
        System.out.println("      Java OOP Design Patterns           ");
        System.out.println("==========================================");
        System.out.println("  Patterns: Factory  | Observer          ");
        System.out.println("            Strategy | Singleton         ");
        System.out.println("==========================================");
        System.out.println();
    }
}
