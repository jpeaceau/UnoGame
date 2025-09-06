package library.container;

import library.card.Card;
import library.player.SortingPreference;
import library.utils.Utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A card container for each player to represent their hands.
 * Includes sorting algorithms as required.
 */
public class Hand extends CardContainer {

    /**
     * Sorts the hand container first by colour, then by value.
     */
    public void sortByValue(){
        getCardsContained().sort(Comparator.comparing(Card::getColour));
        getCardsContained().sort(Comparator.comparing(Card::getCardValue));
    }

    /**
     * Sorts the hand container first by value, then by colour.
     */
    public void sortByColour(){
        getCardsContained().sort(Comparator.comparing(Card::getCardValue));
        getCardsContained().sort(Comparator.comparing(Card::getColour));
    }

    /**
     * @param face The face card from the relevant game instance.
     * @return {@code List<Card>} a list of cards that the player can potentially play.
     * Compares cards' colour and value via the {@code Utils.isPlayable(face, card)} function.
     */
    public List<Card> getPlayableCards(Card face){
        List<Card> playableCards = new ArrayList<>();
        for(Card card : getCardsContained()){
            if(Utils.isPlayable(face, card)){
                playableCards.add(card);
            }
        }
        return playableCards;
    }

    /**
     * @param cards to be added to the player's hand.
     * @param preference player's sorting preference.
     * Adds the provided card to the players' hand. Ideally used via {@code Player#addCards} for the appropriate
     * sorting method to be used. Additional parameter included to help enforce this.
     */
    public void addCards(List<Card> cards, SortingPreference preference){
        getCardsContained().addAll(cards);
        sort(preference);
    }

    /**
     * @param preference player's sorting preference.
     * Uses the player's sorting preference to determine which algorithm order to use.
     */
    public void sort(SortingPreference preference){
        switch(preference){
            case BY_COLOUR -> sortByColour();
            case BY_VALUE -> sortByValue();
        }
    }
}
