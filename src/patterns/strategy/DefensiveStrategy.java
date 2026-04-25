package patterns.strategy;

import cards.Card;
import game.GameState;
import java.util.List;

/**
 * Defensive: conserves wild/special cards and plays plain number cards
 * first, saving powerful cards for emergencies.
 */
public class DefensiveStrategy implements PlayStrategy {

    @Override
    public Card chooseCard(List<Card> playable, GameState state) {
        if (playable.isEmpty()) return null;

        // Prefer plain number cards (least powerful)
        for (Card c : playable)
            if (isNumber(c.getType())) return c;

        // Then action cards
        for (Card c : playable)
            if (!c.getColor().equals("WILD") && !isNumber(c.getType())) return c;

        // Wilds last
        return playable.get(0);
    }

    private boolean isNumber(String type) {
        try { Integer.parseInt(type); return true; }
        catch (NumberFormatException e) { return false; }
    }
}
