package cards;

import game.GameState;

/**
 * Action cards: SKIP, REVERSE, DRAW_TWO.
 * Demonstrates Polymorphism via overridden applyEffect().
 */
public class ActionCard extends Card {

    public ActionCard(String color, String type) {
        super(color, type);
    }

    @Override
    public void applyEffect(GameState state) {
        switch (type) {
            case "SKIP":
                System.out.println("  >> SKIP! Next player loses their turn.");
                state.skipNextPlayer();
                break;
            case "REVERSE":
                System.out.println("  >> REVERSE! Play direction flipped.");
                state.reverseDirection();
                state.advanceTurn();
                break;
            case "DRAW_TWO":
                System.out.println("  >> DRAW TWO! Next player draws 2 cards and is skipped.");
                state.drawCardsForNext(2);
                state.skipNextPlayer();
                break;
            default:
                state.advanceTurn();
        }
    }
}
