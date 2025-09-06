package library.player;

import library.container.Hand;
import library.card.Card;

import java.util.List;

/**
 * Everything is immutable, but hand can still have its elements modified.
 * Allows for easy serialization should the GameState be saved in case of disconnections, or for further analysis, or
 * saving and loading from a database, JSON file, etc.
 */
public class Player {
    private final Hand hand;
    // Given there are no saving systems, and players are by index, duplicate names are fine, no UUID needed.
    private final String name;
    public final SortingPreference sortingPreference;

    public Player(String name, SortingPreference sortingPreference){
        this.name = name;
        hand = new Hand();
        this.sortingPreference = sortingPreference;
    }

    /**
     * @return Player's name.
     */
    public String getName(){
        return name;
    }

    /**
     * @return {@code hand} Player's hand that contains a list of cards.
     */
    public Hand getHand(){
        return hand;
    }

    /**
     * @param cards List of cards to be added to the player's hand.
     * Adds the provided cards to the player's hand, then sorts the hand based on the {@link SortingPreference}.
     */
    public void addCards(List<Card> cards){
        getHand().addCards(cards, sortingPreference);
    }
}
