package library.card.special;

import library.game.Game;
import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;
import library.utils.Utils;

import java.util.Scanner;

/**
 * Manages the logic behind the wild card.
 */
public class WildCard extends Card {

    public WildCard(){
        super(CardValue.WILD, CardColour.WILD);
    }

    /**
     * @param game The relevant game instance.
     * Enacts the card's logic.
     */
    @Override
    public void playCard(Game game){
        Scanner scanner = game.getScanner();
        CardColour colour = Utils.promptForColour(scanner);
        game.setWildColour(this.getCardValue(), colour);
        super.playCard(game);
    }

    /**
     * @param game The relevant game instance.
     * Enacts the card's behavior if it's the first face card.
     */
    @Override
    public void initialFaceBehavior(Game game) {
        playCard(game);
    }
}
