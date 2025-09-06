package library.card;

import library.game.Game;

import java.util.Objects;

/**
 * An entirely immutable Card class. I had this deliberately immutable to ensure
 * easy debugging, as well as if it were to be expanded, an easy means of serialization, asynchronous operations,
 * and multiple games to run at once. Functionality for this would however need to be added.
 */
public class Card {
    private final CardValue cardValue;
    private final CardColour cardColour;

    public Card(CardValue cardValue, CardColour cardColour) {
        this.cardValue = cardValue;
        this.cardColour = cardColour;
    }

    /**
     * @return an enumerated representation of the card's value.
     */
    public CardValue getCardValue() { return cardValue; }
    /**
     * @return an enumerated representation of the card's colour.
     */
    public CardColour getColour() { return cardColour; }
    // To override.
    public void initialFaceBehavior(Game game){}

    /**
     * @return A output-friendly string of the card.
     */
    @Override
    public String toString() {
        return cardColour.toString() + cardValue.toString() + "\u001B[39m";
    }

    /**
     * @param obj card to compare to.
     * @return Whether the cards are the same. Given immutability, the comparison is straight forward.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card other)) return false;
        return this.cardValue == other.cardValue && this.cardColour == other.cardColour;
    }

    /**
     * @return Overwritten hashcode based on cardValue and cardColour.
     * Makes queries faster, particularly in {@link library.container.DeckValidator}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cardValue, cardColour);
    }

    /**
     * @param game The relevant game instance.
     * Default card behavior - turn moves to the next player.
     */
    public void playCard(Game game){
        game.nextTurn();
    }
}
