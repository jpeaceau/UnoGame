package library.container;

import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;
import library.card.special.*;

import java.util.*;

/**
 * DeckValidator has two functions:
 * 1. Generate the default deck for Deck to use.
 * 2. Use the default deck to validate the integrity of other decks (potentially either Deck, or the discard pile, but
 *    the check is performed after discard pile gets added to the deck anyway).
 */
public class DeckValidator {
    public final static List<Card> defaultCards = new ArrayList<>();

    static {
        for (CardColour colour : CardColour.getPrimaryColours()) {
            // 9 * 8 (2 * 1-9's per colour) + 4
            for (int i = 0; i < 10; i++) {
                // two cards for each number from 1 to 9, only one 0 card.
                CardValue value = CardValue.fromInteger(i);
                defaultCards.add(new Card(value, colour));
                if(i != 0) {
                    defaultCards.add(new Card(value, colour));
                }
            }

            for(int i = 0; i < 2; i++){
                defaultCards.add(new DrawTwoCard(colour));
                defaultCards.add(new SkipCard(colour));
                defaultCards.add(new ReverseCard(colour));
            }
        }
        for(int i = 0; i < 4; i++) {
            defaultCards.add(new WildCard());
            defaultCards.add(new WildDrawFourCard());
        }
    }

    /**
     * @param deck the deck to have its integrity checked.
     * Checks deck integrity by looking for illegal card combinations, or excessive types of cards.
     * Throws an InternalError, as recovery should not be attempted.
     */
    public static void isValid(List<Card> deck){
        // Part 1: Checking card quantities. No need to check deck size, as these checks cover everything.
        Set<Card> uniqueCards = new HashSet<>(deck); //Set to remove duplicates.
        for(Card card : uniqueCards){
            if(Collections.frequency(deck, card) > Collections.frequency(defaultCards, card)){
                throw new InternalError("Deck contains too many instances of " + card);
            }
        }

        // Part 2: Checking for illegal combinations. These combinations may be present in "face", but not the deck, or
        // discard pile.
        for(Card card : deck){
            if(card.getCardValue() == CardValue.WILD || card.getCardValue() == CardValue.WILD_DRAW_FOUR){
                if(card.getColour() != CardColour.WILD){
                    throw new InternalError("Illegal card combination found: " + card);
                }
            } else {
                if(card.getColour() == CardColour.WILD){
                    throw new InternalError("Illegal card combination found: " + card);
                }
            }
        }
    }
}
