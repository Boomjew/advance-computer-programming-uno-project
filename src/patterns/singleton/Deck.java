package patterns.singleton;

import cards.Card;
import patterns.factory.CardFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ============================================================
 *  DESIGN PATTERN 4: SINGLETON PATTERN
 * ============================================================
 * Deck ensures there is exactly ONE deck object per game
 * session. All players and the game engine share this single
 * instance, preventing duplicate deck creation.
 *
 * Roles:
 *   Singleton → Deck (this class)
 */
public class Deck {
    private static Deck instance;          // the one-and-only instance
    private Queue<Card> drawPile;
    private List<Card>  discardPile;

    /** Private constructor — callers must use getInstance(). */
    private Deck() {
        drawPile    = new LinkedList<>(CardFactory.createFullDeck());
        discardPile = new LinkedList<>();
    }

    /** Returns the singleton instance, creating it on first call. */
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    /** Reset for a fresh game (keeps the Singleton alive). */
    public static void reset() {
        instance = new Deck();
    }

    public Card draw() {
        if (drawPile.isEmpty()) reshuffle();
        return drawPile.poll();
    }

    public void discard(Card card) {
        discardPile.add(card);
    }

    public Card peekTopDiscard() {
        return discardPile.isEmpty() ? null
            : discardPile.get(discardPile.size() - 1);
    }

    public int drawPileSize() { return drawPile.size(); }

    /** Move discard pile back to draw pile and shuffle. */
    private void reshuffle() {
        System.out.println("[DECK] Draw pile empty — reshuffling discard pile.");
        Card topCard = discardPile.remove(discardPile.size() - 1);
        drawPile.addAll(discardPile);
        discardPile.clear();
        discardPile.add(topCard);
        java.util.Collections.shuffle((java.util.List<Card>) drawPile);
    }
}
