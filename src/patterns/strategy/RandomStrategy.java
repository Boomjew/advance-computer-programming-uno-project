package patterns.strategy;

import cards.Card;
import game.GameState;
import java.util.List;
import java.util.Random;

/**
 * Random: picks any playable card at random.
 * Used as a baseline / "easy" AI mode.
 */
public class RandomStrategy implements PlayStrategy {
    private Random rng = new Random();

    @Override
    public Card chooseCard(List<Card> playable, GameState state) {
        if (playable.isEmpty()) return null;
        return playable.get(rng.nextInt(playable.size()));
    }
}
