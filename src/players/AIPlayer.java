package players;

import cards.Card;
import game.GameState;
import patterns.strategy.PlayStrategy;
import java.util.List;

/**
 * AI-controlled player.
 * Uses the Strategy Pattern: the PlayStrategy can be swapped
 * at runtime (Aggressive / Defensive / Random).
 * Demonstrates Polymorphism — behaves differently depending
 * on which strategy is injected.
 */
public class AIPlayer extends Player {
    private PlayStrategy strategy;

    public AIPlayer(String name, PlayStrategy strategy) {
        super(name);
        this.strategy = strategy;
    }

    /** Allow hot-swapping strategy during gameplay. */
    public void setStrategy(PlayStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Card takeTurn(GameState state) {
        Card topCard = state.getTopCard();
        List<Card> playable = getPlayableCards(topCard);

        System.out.println("[AI " + name + "] Hand size: " + hand.size());

        Card chosen = strategy.chooseCard(playable, state);
        if (chosen == null) {
            System.out.println("[AI " + name + "] No playable card — drawing 1.");
            drawCards(1);
            return null;
        }
        hand.remove(chosen);
        System.out.println("[AI " + name + "] Plays " + chosen);
        return chosen;
    }

    @Override
    public String chooseColor() {
        // AI picks the color it has the most of
        int[] counts = new int[4];
        String[] colors = {"RED","YELLOW","GREEN","BLUE"};
        for (Card c : hand) {
            for (int i = 0; i < colors.length; i++)
                if (colors[i].equals(c.getColor())) counts[i]++;
        }
        int best = 0;
        for (int i = 1; i < 4; i++) if (counts[i] > counts[best]) best = i;
        System.out.println("[AI " + name + "] Chooses color: " + colors[best]);
        return colors[best];
    }
}
