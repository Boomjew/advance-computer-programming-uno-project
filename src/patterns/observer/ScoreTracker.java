package patterns.observer;

import cards.Card;
import players.Player;
import java.util.HashMap;
import java.util.Map;

/** Concrete Observer: tracks how many rounds each player has won. */
public class ScoreTracker implements GameObserver {
    private Map<String, Integer> wins = new HashMap<>();

    @Override public void onCardPlayed(Player p, Card c) {}
    @Override public void onCardDrawn(Player p, int n)   {}
    @Override public void onTurnChanged(Player p)        {}
    @Override public void onUnoCallout(Player c, Player t) {}

    @Override
    public void onGameEnd(Player winner) {
        wins.merge(winner.getName(), 1, Integer::sum);
        System.out.println("[SCORE] " + winner.getName()
            + " total wins: " + wins.get(winner.getName()));
    }

    public void printScoreboard() {
        System.out.println("\n=== SCOREBOARD ===");
        wins.forEach((name, w) ->
            System.out.println("  " + name + " : " + w + " win(s)"));
        System.out.println("==================");
    }
}
