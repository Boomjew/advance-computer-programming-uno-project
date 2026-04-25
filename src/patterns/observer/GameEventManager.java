package patterns.observer;

import cards.Card;
import players.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * GameEventManager is the Subject/Publisher.
 * It maintains a list of observers and notifies them
 * when important game events occur.
 */
public class GameEventManager {
    private List<GameObserver> observers = new ArrayList<>();

    public void addObserver(GameObserver o)    { observers.add(o); }
    public void removeObserver(GameObserver o) { observers.remove(o); }

    public void notifyCardPlayed(Player player, Card card) {
        for (GameObserver o : observers) o.onCardPlayed(player, card);
    }
    public void notifyCardDrawn(Player player, int count) {
        for (GameObserver o : observers) o.onCardDrawn(player, count);
    }
    public void notifyTurnChanged(Player next) {
        for (GameObserver o : observers) o.onTurnChanged(next);
    }
    public void notifyUnoCallout(Player caller, Player target) {
        for (GameObserver o : observers) o.onUnoCallout(caller, target);
    }
    public void notifyGameEnd(Player winner) {
        for (GameObserver o : observers) o.onGameEnd(winner);
    }
}
