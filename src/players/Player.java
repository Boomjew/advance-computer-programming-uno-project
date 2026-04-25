package players;

import cards.Card;
import game.GameState;
import patterns.singleton.Deck;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract base for all player types (Human, AI).
 * Demonstrates Abstraction + Encapsulation.
 */
public abstract class Player {
    protected String     name;
    protected List<Card> hand = new ArrayList<>();

    public Player(String name) { this.name = name; }

    public String     getName()     { return name; }
    public List<Card> getHand()     { return hand; }
    public int        getHandSize() { return hand.size(); }

    public void addCard(Card c) { hand.add(c); }

    public void removeCard(Card c) { hand.remove(c); }

    /** Draw 'n' cards from the deck into hand. */
    public void drawCards(int n) {
        Deck deck = Deck.getInstance();
        for (int i = 0; i < n; i++) {
            Card drawn = deck.draw();
            if (drawn != null) hand.add(drawn);
        }
    }

    /**
     * Return cards in hand that can legally be played on topCard.
     */
    public List<Card> getPlayableCards(Card topCard) {
        return hand.stream()
            .filter(c -> c.canPlayOn(topCard))
            .collect(Collectors.toList());
    }

    /** Each subclass decides HOW to choose and play a card. */
    public abstract Card takeTurn(GameState state);

    /** Each subclass decides HOW to pick a color after a Wild. */
    public abstract String chooseColor();

    @Override
    public String toString() {
        return name + " (" + hand.size() + " cards)";
    }
}
