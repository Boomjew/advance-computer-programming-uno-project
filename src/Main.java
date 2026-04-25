import game.UnoGame;
import patterns.strategy.AggressiveStrategy;
import patterns.strategy.DefensiveStrategy;
import patterns.strategy.RandomStrategy;
import players.AIPlayer;
import players.Player;

import java.util.Arrays;
import java.util.List;

/**
 * ============================================================
 *  UNO CARD GAME — Java OOP Design Patterns Project
 * ============================================================
 *
 *  Design Patterns demonstrated:
 *    1. Factory  Pattern  → CardFactory builds all card types
 *    2. Observer Pattern  → TurnLogger & ScoreTracker observe events
 *    3. Strategy Pattern  → AI players use different play strategies
 *    4. Singleton Pattern → Deck has exactly one instance per game
 *
 *  OOP Principles:
 *    - Abstraction   : Card (abstract), Player (abstract), interfaces
 *    - Inheritance   : NumberCard/ActionCard/WildCard extend Card
 *                      HumanPlayer/AIPlayer extend Player
 *    - Polymorphism  : applyEffect(), takeTurn(), chooseColor()
 *    - Encapsulation : private fields + controlled accessors
 *
 *  Run (from src/):
 *    javac -d ../out $(find . -name "*.java")
 *    java  -cp ../out Main
 * ============================================================
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("===========================================");
        System.out.println("   UNO CARD GAME — OOP Design Patterns");
        System.out.println("===========================================\n");

        // ---------------------------------------------------------
        // Create AI players with DIFFERENT strategies (Strategy DP)
        // ---------------------------------------------------------
        AIPlayer alice = new AIPlayer("Alice (Aggressive)", new AggressiveStrategy());
        AIPlayer bob   = new AIPlayer("Bob   (Defensive)",  new DefensiveStrategy());
        AIPlayer carol = new AIPlayer("Carol (Random)",     new RandomStrategy());

        List<Player> players = Arrays.asList(alice, bob, carol);

        // ---------------------------------------------------------
        // Play 2 rounds to show Observer/Singleton/Factory in action
        // ---------------------------------------------------------
        UnoGame game = new UnoGame(players);

        System.out.println(">>> ROUND 1 <<<");
        game.playGame();

        System.out.println("\n>>> ROUND 2 <<<");
        game.playGame();

        // ---------------------------------------------------------
        // Print scoreboard (Observer ScoreTracker collected results)
        // ---------------------------------------------------------
        game.printScoreboard();

        // ---------------------------------------------------------
        // Strategy hot-swap demo: change Alice to Defensive mid-game
        // ---------------------------------------------------------
        System.out.println("\n--- Strategy Hot-Swap Demo ---");
        System.out.println("Alice switches from Aggressive → Defensive strategy.");
        alice.setStrategy(new DefensiveStrategy());

        System.out.println("\n>>> ROUND 3 (Alice now Defensive) <<<");
        game.playGame();

        game.printScoreboard();

        System.out.println("\nAll design patterns successfully demonstrated!");
    }
}
