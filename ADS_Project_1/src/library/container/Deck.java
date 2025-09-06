package library.container;

import library.game.Game;
import library.card.Card;
import library.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Uno deck, loaded based on a pair of 1-9 numbers per colour, plus one 0 card per colour,
 * 3 pairs of action cards per colour, and 4 lots of both wild card and wild draw four cards (= 108 cards).
 */
public class Deck extends CardContainer {

    public Deck() {
        this.getCardsContained().addAll(DeckValidator.defaultCards);
        Collections.shuffle(this.getCardsContained());
    }

    /**
     * @param toAdd A list of cards to be added to the deck.
     * Ensures the deck's integrity and randomness.
     */
    public void addCards(List<Card> toAdd){
        getCardsContained().addAll(toAdd);
        DeckValidator.isValid(this.getCardsContained());
        Collections.shuffle(getCardsContained());
    }

    /**
     * @param player The relevant player who will be receiving cards.
     * @param cards Number of cards to be picked up.
     * @param forced Is the player forced to receive these cards?
     * @param game Relevant game instance.
     * Draws cards from the deck to the player's hand, accounting for overflow by merging the discard pile
     * where required.
     */
    public void drawCards(Player player, int cards, boolean forced, Game game){
        int overflow = cards - getCardsContained().size();
        List<Card> cardsDrawn = new ArrayList<>();
        if(overflow > 0){
            cardsDrawn.addAll(moveCards(cards - overflow));
            mergeDiscardPile(game);
            cardsDrawn.addAll(moveCards(overflow));
        } else {
            cardsDrawn.addAll(moveCards(cards));
        }
        player.addCards(cardsDrawn);

        String suffix = (cards == 1) ? "card" : "cards";
        String action = forced ? "was forced to draw" : "has drawn";
        System.out.printf("%s %s %d %s%n", player.getName(), action, cards, suffix);
    }

    /**
     * @param cards Number of cards to be moved from the deck and returned.
     * @return a list of cards moved from the deck.
     */
    private List<Card> moveCards(int cards){
        List<Card> cardsList = new ArrayList<>();
        // Two separate loops required, as to avoid ConcurrentModificationException.
        for(int i = 0; i < cards; i++){
            cardsList.add(getCardsContained().get(i));
        }

        // Index-based removal needed, as Card is immutable.
        for(int i = 0; i < cards; i++){
            getCardsContained().removeFirst();
        }
        return cardsList;
    }

    /**
     * @param game relevant game instance.
     * Moves the cards from the discard pile into the deck, and checks the integrity of the deck to ensure there's
     * no errors.
     */
    public void mergeDiscardPile(Game game){
        System.out.println("Deck ran out of cards, shuffling discard pile into deck...");
        CardContainer discardPile = game.getDiscardPile();
        addCards(discardPile.getCardsContained());
        discardPile.getCardsContained().clear();
        // Niche edge case that would not happen, but my programmer sense makes me account for it, I can't help it.
        if(getCardsContained().isEmpty()){
            throw new RuntimeException("Deck and discard pile have no more cards!");
        }
    }
}
