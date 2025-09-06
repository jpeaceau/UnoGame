package library.container;

import library.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * While no new methods are added compared to what a List contains, I opted to include this
 * as it is more explicit and deliberate, making the code easier to read. I'd also rather the subclasses
 * not extend List, that would be painful!
 */
public class CardContainer {
    protected final List<Card> cardsContained;

    public CardContainer(){
        cardsContained = new ArrayList<>();
    }

    /**
     * @return the list of cards contained within this instance.
     */
    public List<Card> getCardsContained() {
        return cardsContained;
    }
}
