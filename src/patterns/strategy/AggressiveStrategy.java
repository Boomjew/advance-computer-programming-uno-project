package patterns.strategy;

import cards.Card;
import game.GameState;
import java.util.List;

/**
 * Aggressive: prefers action cards (SKIP, REVERSE, DRAW_TWO, WILD_DRAW_FOUR)
 * to disrupt opponents as much as possible.
 */
public class AggressiveStrategy implements PlayStrategy {

    @Override
    public Card chooseCard(List<Card> playable, GameState state) {
        if (playable.isEmpty()) return null;

        // Prefer draw-penalty cards first
        for (Card c : playable)
            if (c.getType().contains("DRAW")) return c;

        // Then skip/reverse
        for (Card c : playable)
            if (c.getType().equals("SKIP") || c.getType().equals("REVERSE")) return c;

        // Then any wild
        for (Card c : playable)
            if (c.getColor().equals("WILD")) return c;

        return playable.get(0);
    }
}
