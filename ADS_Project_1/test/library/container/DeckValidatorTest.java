package library.container;

import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckValidatorTest {

    @Test
    void isValidTooManyCards() {
        Deck deck = new Deck();
        assertThrows(InternalError.class, () -> {
            deck.addCards(List.of(new Card(CardValue.FIVE, CardColour.RED)));
        }, "Having more than 2 1-9 cards for any colour should throw InternalError.");
    }

    @Test
    void isValidCardCombination(){
        Deck deck = new Deck();
        Card card = new Card(CardValue.WILD, CardColour.WILD);
        deck.getCardsContained().remove(card);
        assertThrows(InternalError.class, () -> {
            deck.addCards(List.of(new Card(CardValue.WILD, CardColour.RED)));
        }, "Illegal combination should throw InternalError");
    }
}