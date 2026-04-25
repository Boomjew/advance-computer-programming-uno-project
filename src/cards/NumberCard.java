package cards;

import game.GameState;

/**
 * A numbered UNO card (0–9).
 * Inherits from Card — demonstrates Inheritance.
 */
public class NumberCard extends Card {
    private int value;

    public NumberCard(String color, int value) {
        super(color, String.valueOf(value));
        this.value = value;
    }

    public int getValue() { return value; }

    @Override
    public void applyEffect(GameState state) {
        // Number cards have no special effect — just advances the turn
        state.advanceTurn();
    }
}
