package library.container;

import library.card.Card;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeckTest {
    /**
     addCards includes two functions:
     1. Ensuring the deck is always shuffled when anything is added.
     2. Validates that the updated deck is valid.
     Here, we test if the deck was shuffled correctly, by removing a card from a list, and the direct deck cardcontainer
     and re-add the card, to ensure the error for an invalid deck does not get thrown.
     */
    @Test
    void addCards() {
        Deck deck = new Deck();
        List<Card> contentsBeforeShuffle = new ArrayList<>(deck.getCardsContained());
        Card toMove = contentsBeforeShuffle.getFirst();
        deck.getCardsContained().remove(toMove);
        contentsBeforeShuffle.remove(toMove);
        contentsBeforeShuffle.add(toMove);
        deck.addCards(List.of(toMove));
        assertNotEquals(contentsBeforeShuffle, deck.getCardsContained());
    }
}
