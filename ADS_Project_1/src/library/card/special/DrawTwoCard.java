package library.card.special;

import library.game.Game;
import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;

/**
 * Manages the logic behind the draw two card.
 */
public class DrawTwoCard extends Card {
    public DrawTwoCard(CardColour colour) {
        super(CardValue.DRAW_TWO, colour);
    }

    /**
     * @param game The relevant game instance.
     * Enacts the card's logic.
     */
    @Override
    public void playCard(Game game){
        game.getDeck().drawCards(game.getNextPlayer(), 2, true, game);
        game.skipTurn(false);
    }

    /**
     * @param game The relevant game instance.
     * Enacts the card's behavior if it's the first face card.
     */
    @Override
    public void initialFaceBehavior(Game game) {
        game.getDeck().drawCards(game.getCurrentPlayer(), 2, true, game);
        game.skipTurn(true);
    }
}
