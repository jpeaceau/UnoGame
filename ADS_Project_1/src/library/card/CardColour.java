package library.card;

import library.utils.Utils;

import java.util.List;

/**
 * Enum representation of card colours to serve as a constraint.
 * Each value has an ANSI value corresponding to the colour.
 */
public enum CardColour {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    WILD("\u001B[35m");

    private final String ansi;

    CardColour(String ansi){
        this.ansi = ansi;
    }

    /**
     * @return the corresponding ANSI colour code.
     */
    public String toAnsi(){
        return ansi;
    }

    /**
     * @return an easy means of iterating over the primary colours.
     */
    public static List<CardColour> getPrimaryColours(){
        return List.of(RED, GREEN, YELLOW, BLUE);
    }

    /**
     * @return returns a neat string, to be used with {@code CardValue.toString()}.
     */
    @Override
    public String toString() {
        if(this == CardColour.WILD){
            return toAnsi();
        }
        return toAnsi() + Utils.neatCapitalize(super.toString()) + " ";
    }
}
