package players;

import cards.Card;
import game.GameState;
import java.util.List;
import java.util.Scanner;

/**
 * Human-controlled player: reads moves from stdin.
 * Demonstrates Inheritance (extends Player).
 */
public class HumanPlayer extends Player {
    private Scanner scanner = new Scanner(System.in);

    public HumanPlayer(String name) { super(name); }

    @Override
    public Card takeTurn(GameState state) {
        Card topCard = state.getTopCard();
        List<Card> playable = getPlayableCards(topCard);

        System.out.println("\nYour hand:");
        for (int i = 0; i < hand.size(); i++)
            System.out.println("  " + i + ": " + hand.get(i));
        System.out.println("Top card: " + topCard);

        if (playable.isEmpty()) {
            System.out.println("No playable cards. Drawing 1 card...");
            drawCards(1);
            return null;
        }

        System.out.println("Playable cards:");
        for (int i = 0; i < playable.size(); i++)
            System.out.println("  " + i + ": " + playable.get(i));
        System.out.print("Choose card index (or -1 to draw): ");

        int choice = -1;
        try { choice = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { /* treat as draw */ }

        if (choice < 0 || choice >= playable.size()) {
            System.out.println("Drawing 1 card...");
            drawCards(1);
            return null;
        }
        Card chosen = playable.get(choice);
        hand.remove(chosen);
        return chosen;
    }

    @Override
    public String chooseColor() {
        System.out.print("Choose color (RED/YELLOW/GREEN/BLUE): ");
        String[] valid = {"RED","YELLOW","GREEN","BLUE"};
        String input = "";
        while (true) {
            try { input = scanner.nextLine().trim().toUpperCase(); }
            catch (Exception e) { return "RED"; }
            for (String v : valid) if (v.equals(input)) return input;
            System.out.print("Invalid. Choose RED/YELLOW/GREEN/BLUE: ");
        }
    }
}
