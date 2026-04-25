package patterns.strategy;

import cards.Card;
import game.GameState;
import java.util.List;

/**
 * ============================================================
 *  DESIGN PATTERN 3: STRATEGY PATTERN
 * ============================================================
 * PlayStrategy encapsulates an algorithm for choosing which
 * card to play (or whether to draw).  AI players are assigned
 * different strategies at runtime without changing the Player class.
 *
 * Roles:
 *   Strategy interface → PlayStrategy (this)
 *   Concrete Strategies→ AggressiveStrategy, DefensiveStrategy,
 *                         RandomStrategy
 *   Context            → AIPlayer
 */
public interface PlayStrategy {
    /**
     * Choose a card from 'playableCards' to play, or return null to draw.
     * @param playableCards subset of the player's hand that can legally be played
     * @param state         current game state (for reading top card, etc.)
     */
    Card chooseCard(List<Card> playableCards, GameState state);
}
