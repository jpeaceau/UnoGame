package library.container;

import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;
import library.player.SortingPreference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    /*
    I will include a comment here, advising what is going on for sorting tests.
    The CardColour and CardValue Enum classes have an order. Java's Collections.sort() attempts to follow this order.

    Moreover, sorting by colour has it sort by value first, and vice versa.

    As such, the expected output is deterministic, and should follow a 0 to wild draw four, and an R < G < B < Y < W
    colour order.
     */
    Hand hand;
    List<Card> cardsList;

    @BeforeEach
    void setUp() {
        hand = new Hand();
        cardsList = List.of(
                new Card(CardValue.SEVEN, CardColour.RED),
                new Card(CardValue.WILD, CardColour.WILD),
                new Card(CardValue.SIX, CardColour.RED),
                new Card(CardValue.WILD_DRAW_FOUR, CardColour.WILD),
                new Card(CardValue.EIGHT, CardColour.GREEN)
        );
    }

    @Test
    void sortByValue() {
        hand.addCards(cardsList, SortingPreference.BY_VALUE);
        List<Card> expectedValueSortedList = List.of(
                new Card(CardValue.SIX, CardColour.RED),      // Value SIX
                new Card(CardValue.SEVEN, CardColour.RED),    // Value SEVEN
                new Card(CardValue.EIGHT, CardColour.GREEN),  // Value EIGHT
                new Card(CardValue.WILD, CardColour.WILD),      // Value WILD
                new Card(CardValue.WILD_DRAW_FOUR, CardColour.WILD) // Value WILD_DRAW_FOUR
        );
        assertEquals(hand.getCardsContained(), expectedValueSortedList);
    }

    @Test
    void sortByColour() {
        hand.addCards(cardsList, SortingPreference.BY_COLOUR);
        List<Card> expectedColourSortedList = List.of(
                new Card(CardValue.SIX, CardColour.RED),
                new Card(CardValue.SEVEN, CardColour.RED),
                new Card(CardValue.EIGHT, CardColour.GREEN),
                new Card(CardValue.WILD, CardColour.WILD),
                new Card(CardValue.WILD_DRAW_FOUR, CardColour.WILD)
        );
        assertEquals(hand.getCardsContained(), expectedColourSortedList);
    }

    @Test
    void getPlayableCards() {
        List<Card> expectedList = List.of(
                new Card(CardValue.SEVEN, CardColour.RED),
                new Card(CardValue.EIGHT, CardColour.GREEN),
                new Card(CardValue.WILD, CardColour.WILD),
                new Card(CardValue.WILD_DRAW_FOUR, CardColour.WILD)
        );
        hand.addCards(cardsList, SortingPreference.BY_VALUE);
        assertEquals(hand.getPlayableCards(new Card(CardValue.SEVEN, CardColour.GREEN)), expectedList);
    }
}