package library.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    /*
    Testing is rather limited, due to Game instance being complex, and heavily interaction-based, for things like
    playCard(). JUnit tests require no input.
     */
    private Card redSeven, blueSkip, wildCard;

    @BeforeEach
    void setUp() {
        redSeven = new Card(CardValue.SEVEN, CardColour.RED);
        blueSkip = new Card(CardValue.SKIP, CardColour.BLUE);
        wildCard = new Card(CardValue.WILD, CardColour.WILD);
    }

    @Test
    public void testEquals(){
        assertEquals(new Card(CardValue.SEVEN, CardColour.RED), redSeven);
        assertNotEquals(redSeven, wildCard);
    }

    @Test
    public void getColour(){
        assertEquals(CardColour.WILD, wildCard.getColour());
    }

    @Test
    public void getCardValue(){
        assertEquals(CardValue.SKIP, blueSkip.getCardValue());
    }

    @Test
    void testToString() {
        assertEquals("Skip", CardValue.SKIP.toString());
        assertEquals("\u001B[34mBlue ", CardColour.BLUE.toString());
    }
}