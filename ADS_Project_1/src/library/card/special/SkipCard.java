package library.card.special;

import library.game.Game;
import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;

/**
 * Manages the logic behind the skip card.
 */
public class SkipCard extends Card {

    public SkipCard(CardColour colour){
        super(CardValue.SKIP, colour);
    }

    /**
     * @param game The relevant game instance.
     * Enacts the card's logic.
     */
    @Override
    public void playCard(Game game){
        game.skipTurn(false);
    }

    /**
     * @param game The relevant game instance.
     * Skip normally skips the next player's turn, hence a distinct method for if skip is the first face card.
     */
    @Override
    public void initialFaceBehavior(Game game) {
        game.skipTurn(true);
    }
}
