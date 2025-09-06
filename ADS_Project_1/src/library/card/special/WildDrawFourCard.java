package library.card.special;

import library.game.Game;
import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;
import library.player.Player;
import library.utils.Utils;

import java.util.List;
import java.util.Scanner;

/**
 * The class that manages the behavior and logic behind the wild draw four class.
 * There are prompt-questions in Utils, as I had this class focus on its objective only.
 */
public class WildDrawFourCard extends Card {

    public WildDrawFourCard(){
        super(CardValue.WILD_DRAW_FOUR, CardColour.WILD);
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
        Player target = game.getNextPlayer();
        if(Utils.promptForChallenge(scanner, game.getCurrentPlayer())){
            challengeLogic(target, game.getCurrentPlayer(), game);
        } else {
            game.getDeck().drawCards(game.getNextPlayer(), 4, true, game);
            game.skipTurn(false);
        }
    }

    // While inconsistent with numeric selections, I opted to make this
    // selection by string, because the consequences are greater based
    // on the choice.

    /**
     * @param challenger The player that is challenging the draw four.
     * @param challenged The player that played the draw four.
     * @param game The relevant game instance.
     * If the challenged could have played other cards, they pick up 4 cards. Otherwise, the challenger picks up 6.
     */
    private void challengeLogic(Player challenger, Player challenged, Game game){
        System.out.printf("%s's hand:%n", challenged.getName());
        // While the focus is on the coloured cards, in Uno rules, all players typically see the
        // challenged player's hand when they are challenged.
        for(Card card : challenged.getHand().getCardsContained()) System.out.println(card);

        List<Card> playableCards = challenged.getHand().getCardsContained()
                .stream()
                .filter(card -> card.getColour() == game.getPreviousCardColour())
                .toList();

        if(!playableCards.isEmpty()){
            System.out.println("Cards with mutual colour identified:");
            for(Card card : playableCards){
                System.out.println(card);
            }
            game.getDeck().drawCards(challenged, 4, true, game);
            game.nextTurn();
        } else {
            System.out.println("No colour-mutual cards identified.");
            game.getDeck().drawCards(challenger, 6, true, game);
            game.skipTurn(false);
        }
    }
}
