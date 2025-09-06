package library.card.special;

import library.game.Game;
import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;

/**
 * Invokes the logic behind the reverse card with respect to Game.
 * Some logic managed in Game itself, as to not expose the variables.
 */
public class ReverseCard extends Card {

    public ReverseCard(CardColour colour){
        super(CardValue.REVERSE, colour);
    }

    /**
     * @param game The relevant game instance.
     * Enacts the card's logic.
     */
    @Override
    public void playCard(Game game){
        game.reverse(false);
    }

    /**
     * @param game The relevant game instance.
     * A distinct function was needed, because if there are only 2 players, and it's the initial card, the current
     * player's turn is skipped instead of the next player's turn.
     */
    @Override
    public void initialFaceBehavior(Game game) {
        game.reverse(true);
    }
}
