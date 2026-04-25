package cards;

/**
 * Abstract base class for all UNO cards.
 * Demonstrates OOP: Abstraction + Encapsulation
 */
public abstract class Card {
    protected String color;   // RED, YELLOW, GREEN, BLUE, WILD
    protected String type;    // number or action name

    public Card(String color, String type) {
        this.color = color;
        this.type  = type;
    }

    public String getColor() { return color; }
    public String getType()  { return type; }

    public void setColor(String color) { this.color = color; }

    /** True if this card can legally be played on top of 'other'. */
    public boolean canPlayOn(Card other) {
        return this.color.equals("WILD")
            || this.color.equals(other.color)
            || this.type.equals(other.type);
    }

    /** Apply the card's special effect to the game state. */
    public abstract void applyEffect(game.GameState state);

    @Override
    public String toString() {
        return "[" + color + " " + type + "]";
    }
}
