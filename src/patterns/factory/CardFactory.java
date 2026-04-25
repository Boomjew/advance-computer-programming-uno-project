package patterns.factory;

import cards.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================
 *  DESIGN PATTERN 1: FACTORY METHOD PATTERN
 * ============================================================
 * CardFactory centralizes card creation.  Clients request card
 * objects without knowing which concrete subclass to instantiate.
 * This decouples the card hierarchy from the code that uses it.
 *
 * Roles:
 *   Creator  → CardFactory
 *   Products → NumberCard, ActionCard, WildCard
 */
public class CardFactory {

    private static final String[] COLORS = {"RED", "YELLOW", "GREEN", "BLUE"};

    /** Factory method: create a numbered card */
    public static Card createNumberCard(String color, int value) {
        return new NumberCard(color, value);
    }

    /** Factory method: create an action card */
    public static Card createActionCard(String color, String type) {
        return new ActionCard(color, type);
    }

    /** Factory method: create a wild card */
    public static Card createWildCard(String type) {
        return new WildCard(type);
    }

    /**
     * Build a complete, shuffled 108-card UNO deck using the factory
     * methods above.  This is the single entry point for deck creation.
     */
    public static List<Card> createFullDeck() {
        List<Card> deck = new ArrayList<>();

        for (String color : COLORS) {
            // One 0-card per color
            deck.add(createNumberCard(color, 0));

            // Two of each 1–9 and each action per color
            for (int v = 1; v <= 9; v++) {
                deck.add(createNumberCard(color, v));
                deck.add(createNumberCard(color, v));
            }
            for (String action : new String[]{"SKIP", "REVERSE", "DRAW_TWO"}) {
                deck.add(createActionCard(color, action));
                deck.add(createActionCard(color, action));
            }
        }

        // 4 WILD + 4 WILD_DRAW_FOUR
        for (int i = 0; i < 4; i++) {
            deck.add(createWildCard("WILD"));
            deck.add(createWildCard("WILD_DRAW_FOUR"));
        }

        Collections.shuffle(deck);
        return deck;
    }
}
