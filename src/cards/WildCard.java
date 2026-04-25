package cards;

import game.GameState;

/**
 * Wild cards: WILD and WILD_DRAW_FOUR.
 * Color is chosen at play time.
 */
public class WildCard extends Card {

    public WildCard(String type) {
        super("WILD", type);  // color starts as WILD
    }

    @Override
    public boolean canPlayOn(Card other) {
        return true;  // Wild cards can always be played
    }

    @Override
    public void applyEffect(GameState state) {
        // The current player picks a new color
        String chosenColor = state.getCurrentPlayer().chooseColor();
        this.setColor(chosenColor);
        System.out.println("  >> Wild played! Color changed to: " + chosenColor);

        if (type.equals("WILD_DRAW_FOUR")) {
            System.out.println("  >> WILD DRAW FOUR! Next player draws 4 cards and is skipped.");
            state.drawCardsForNext(4);
            state.skipNextPlayer();
        } else {
            state.advanceTurn();
        }
    }

    @Override
    public String toString() {
        return "[WILD " + type + "]";
    }
}
