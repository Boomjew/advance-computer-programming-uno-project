package patterns.observer;

import cards.Card;
import players.Player;

/**
 * ============================================================
 *  DESIGN PATTERN 2: OBSERVER PATTERN
 * ============================================================
 * GameObserver is the Observer interface.
 * Any object that wants to receive game-event notifications
 * implements this interface.
 *
 * Roles:
 *   Subject   → GameEventManager
 *   Observer  → GameObserver (this interface)
 *   Concrete  → ScoreTracker, TurnLogger, UnoCallDetector
 */
public interface GameObserver {
    void onCardPlayed(Player player, Card card);
    void onCardDrawn(Player player, int count);
    void onTurnChanged(Player nextPlayer);
    void onUnoCallout(Player caller, Player target);
    void onGameEnd(Player winner);
}
