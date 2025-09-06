package library.game;

import library.container.CardContainer;
import library.container.Deck;
import library.container.Hand;
import library.card.Card;
import library.card.CardColour;
import library.card.CardValue;
import library.player.Player;
import library.player.PlayerBuilder;
import library.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Manages everything game-related, acts as the mediator between Player, Hand, (discard pile), and cards.
 * Easy to add a gameID variable and a GameManager class, if multiple game instances were
 * desired. Though, many loops should be streamed as to run asynchronously, and games should be serializable if this were
 * the goal of the assignment.
 */
public class Game {
    private final Deck deck;
    private final CardContainer discardPile;
    private final List<Player> players;
    private Card face;
    private int currentTurn;
    private boolean reverse;
    private final Scanner scanner;
    private CardColour previousCardColour;

    public Game(){
        scanner = new Scanner(System.in);
        players = new ArrayList<>();
        deck = new Deck();
        discardPile = new CardContainer();
    }

    /**
     * @return scanner to ensure consistency.
     */
    public Scanner getScanner() { return scanner; }

    /**
     * @return Player instance that's up next.
     */
    public Player getNextPlayer(){ return players.get(getNextPlayerIndex()); }

    /**
     * @return Current turn's player.
     */
    public Player getCurrentPlayer() { return players.get(currentTurn); }

    /**
     * @return The discard pile.
     */
    public CardContainer getDiscardPile() { return discardPile; }

    /**
     * @return The deck instance for this game.
     */
    public Deck getDeck() { return deck; }

    /**
     * Used primarily for challenge logic for wild draw four.
     * @return Previous card colour.
     */
    public CardColour getPreviousCardColour() { return previousCardColour; }

    /**
     * @return Returns the next player index, considering {@code reverse} and {@code players}.
     */
    public int getNextPlayerIndex(){
        int totalPlayers = players.size();
        int step = reverse ? -1 : 1; // -1 for reverse, +1 for forward
        // Add totalPlayers *before* the modulo to handle negative results and wrap around
        return (currentTurn + step + totalPlayers) % totalPlayers;
    }

    /**
     * @param value Must either be {@code CardValue.WILD}, or {@code CardValue.WILD_DRAW_FOUR}
     * @param colour Must be a non-wild colour.
     * @see CardValue, {@link CardColour}
     * Sets the face to a temporary new Card with the chosen CardValue and CardColour to facilitate wild card colour
     * changes.
     */
    public void setWildColour(CardValue value, CardColour colour){
        if(CardColour.WILD == colour){
            throw new IllegalArgumentException("Colour must not be wild!");
        } else if(value != CardValue.WILD && value != CardValue.WILD_DRAW_FOUR){
            throw new IllegalArgumentException("Wild card, or wild draw four \"value\" required.");
        }
        face = new Card(value, colour);
    }

    /**
     * Begins the game by drawing cards, setting the face card, and starting the first turn.
     */
    public void startGame(){
        players.addAll(PlayerBuilder.promptForPlayers(scanner));
        for(Player player : players){
            //"Forced" is false, as the forced output doesn't make sense here.
            deck.drawCards(player, 7, false, this);
        }
        Card initFace = null;
        while(initFace == null){
            initFace = getDeck().getCardsContained().getFirst();
            if(initFace.getCardValue() == CardValue.WILD_DRAW_FOUR){
                initFace = null;
            }
        }
        face = initFace;
        getDeck().getCardsContained().remove(face);
        face.initialFaceBehavior(this);
        currentTurn = 0;
        System.out.println("Game started!");
        actionTurn();
    }

    /**
     * Prompts the current player accordingly to play the card of their choice.
     * Invokes {@code playCard(Game game)}.
     */
    public void actionTurn(){
        Player currentPlayer = getCurrentPlayer();
        Hand hand = currentPlayer.getHand();
        List<Card> cardsContained = hand.getCardsContained();
        List<Card> playableCards = hand.getPlayableCards(face);
        System.out.printf("It is %s's turn! The face card is %s%n",
                currentPlayer.getName(), face.toString());
        if(playableCards.isEmpty()){
            // forced is false, as from other players' perspectives, they wouldn't know if it was forced or not.
            deck.drawCards(getCurrentPlayer(), 1, false, this);
            nextTurn();
            return;
        }
        System.out.println("Your cards are:");
        for(Card card : cardsContained){
            System.out.println(card);
        }
        System.out.println("Choose the number for the card you wish to play, or (0) to pick up:");
        // Listing only once to prevent spam / information overload.
        System.out.println(0 + ". pick up card");
        for(int i = 0; i < playableCards.size(); i++){
            System.out.println((i + 1) + ". " + playableCards.get(i));
        }
        int index = -1;
        while(index == -1){
            String input = scanner.nextLine();
            if(!Utils.isValid(input, 0, playableCards.size())){
                System.out.println("Invalid input.");
                continue;
            }
            index = Integer.parseInt(input);
        }
        if(index == 0){
            deck.drawCards(getCurrentPlayer(), 1, false, this);
            nextTurn();
            return;
        }
        playCard(playableCards.get(index - 1));
    }


    /**
     * @param card The card that is to be played.
     * Invokes the card's {@code playCard(Game game)} function, moves the previous {@code face} card to discard pile.
     */
    public void playCard(Card card){
        Hand hand = getCurrentPlayer().getHand();
        if(hand.getCardsContained().size() == 1){
            endGame();
            return;
        }
        if(List.of(CardValue.WILD_DRAW_FOUR, CardValue.WILD).contains(face.getCardValue())){
            discardPile.getCardsContained().add(new Card(face.getCardValue(), CardColour.WILD));
        } else {
            discardPile.getCardsContained().add(face);
        }
        getCurrentPlayer().getHand().getCardsContained().remove(card);
        previousCardColour = face.getColour();
        face = card;
        card.playCard(this);
    }

    /**
     * Moves the current player index to the next player index,
     * invokes {@code actionTurn()} to begin prompting the next player.
     */
    // nextTurn and skipTurn managed by cards.
    public void nextTurn(){
        currentTurn = getNextPlayerIndex();
        actionTurn();
    }

    /**
     * @param skipCurrent If true, the current player's turn will be skilled. If false, the next player's turn will be
     *                    skipped.
     * Skips the appropriate player's turn.
     */
    public void skipTurn(boolean skipCurrent){
        // If the current turn is skipped instead of the next player's turn, only 1 turn movement is needed.
        currentTurn = skipCurrent ? currentTurn : getNextPlayerIndex();
        System.out.printf("%s's turn was skipped!%n", getCurrentPlayer().getName());
        nextTurn();
    }

    /**
     * @param isInitial True if this is being invoked from the initial face card, due to skipping the current player's
     *                  turn, rather than the next player's turn.
     * Either skips the player's turn, or reverses the order of turns in accordance with the number of players.
     */
    public void reverse(boolean isInitial){
        if(players.size() > 2) {
            System.out.println("The order of turns has been reversed!");
            reverse = !reverse;
            nextTurn();
        } else {
            skipTurn(isInitial);
        }
    }

    /**
     * Announces the winner of the game, assumed to be the current player.
     */
    public void endGame(){
        System.out.printf("Game over! %s wins!", getCurrentPlayer().getName());
    }
}
