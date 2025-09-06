package library.card;

import library.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Constraints added to what values the cards can be by way of an enum.
 * Also maps card values to integers, making them easier to iterate and print.
 */
public enum CardValue {
    ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4),
    FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9),
    SKIP, REVERSE, WILD, WILD_DRAW_FOUR, DRAW_TWO;

    private final int cardValue;

    CardValue(int cardValue) { this.cardValue = cardValue; }
    CardValue(){ this.cardValue = -1; }

    private static final Map<Integer, CardValue> cardValues = new HashMap<>();

    static {
        for(CardValue card : CardValue.values()){
            if(card.cardValue != -1){
                cardValues.put(card.cardValue, card);
            }
        }
    }

    /**
     * @param cardValue numeric representation of the card value.
     * @return enumeration representation of the card value.
     */
    public static CardValue fromInteger(int cardValue) {
        return cardValues.get(cardValue);
    }

    /**
     * @return a neat string, to be combined with {@code CardColour.toString()}.
     */
    @Override
    public String toString(){
        if(cardValue != -1){
            return cardValue + "";
        }
        return Utils.neatCapitalize(super.toString().replace("_", " "));
    }

}
