package library.utils;

import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void isPlayable() {
        Card card1 = new Card(CardValue.FIVE, CardColour.RED);
        // Normally an illegal combination, but allowable for "face".
        Card face = new Card(CardValue.WILD, CardColour.RED);
        assertTrue(Utils.isPlayable(face, card1));
    }

    @Test
    void isInteger() {
        assertFalse(Utils.isInteger("potato"));
        assertTrue(Utils.isInteger("3"));
    }

    @Test
    void isValid() {
        assertTrue(Utils.isValid("5", 3, 8));
    }

    @Test
    void neatCapitalize() {
        assertEquals("Potato", Utils.neatCapitalize("POTATO"));
    }
}