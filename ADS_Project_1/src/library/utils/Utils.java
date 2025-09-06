package library.utils;

import library.card.Card;
import library.card.CardColour;
import library.player.Player;

import java.util.List;
import java.util.Scanner;

public class Utils {
    private static final String CHALLENGE_PROMPT_SITUATION =
            "You are the target of a wild draw four by %s who has %d cards.%n";
    private static final String CHALLENGE_PROMPT_QUESTION =
            "Do you want to challenge? (y/n):%n";
    private static final String CHALLENGE_RULES =
            "%nRules:%n" +
                    "If a player uses a draw four, but had other cards of mutual colour with the previous card,%n" +
                    "if challenged, the player must draw 4 cards.%n" +
                    "Otherwise, if you guessed wrong, you pick up 6 cards.%n";

    /**
     * @param face The face card for the relevant game
     * @param toPlay The card to potentially be played.
     * @return True if the card can be played given the face card, otherwise false.
     * Checks the card colours and card values to see if the card is playable, given the face card.
     */
    public static boolean isPlayable(Card face, Card toPlay){
        if(toPlay.getColour() == CardColour.WILD){
            return true;
        }

        if(toPlay.getColour() == face.getColour()){
            return true;
        }

        return toPlay.getCardValue() == face.getCardValue();
    }

    /**
     * @param str String to check if it's an integer or not.
     * @return true if integer, false if not.
     */
    public static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
        } catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    /**
     * @param input integer input to be validated.
     * @param lower Required lower range (>= lower)
     * @param higher Required higher range (<= higher)
     * @return whether the input is valid or not.
     */
    public static boolean isValid(int input, int lower, int higher){
        return input >= lower && input <= higher;
    }


    /**
     * @param input String input to be validated.
     * @param lower Required lower range (>= lower)
     * @param higher Required higher range (<= higher)
     * @return whether the input is valid or not.
     */
    public static boolean isValid(String input, int lower, int higher){
        if(!isInteger(input)){
            return false;
        }
        return isValid(Integer.parseInt(input), lower, higher);
    }

    /**
     * @param s string to be made neater.
     * @return the string lower case, with the first character upper-case.
     */
    public static String neatCapitalize(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    /**
     * @param scanner Scanner from the game instance.
     * @return the primary card colour the player has chosen.
     * Prompts the player for a colour choice of the primary colours.
     */
    public static CardColour promptForColour(Scanner scanner){
        CardColour colour = null;
        List<CardColour> colours = CardColour.getPrimaryColours();
        while(colour == null){
            System.out.println("Pick a colour:");
            for(int i = 0; i < colours.size(); i++){
                System.out.println((i + 1) + ". " + colours.get(i).toString() + "\u001B[39m");
            }
            String input = scanner.nextLine();
            if(!isValid(input, 1, 4)){
                System.out.println("Invalid colour!");
                continue;
            }
            colour = colours.get(Integer.parseInt(input) - 1);
        }
        return colour;
    }

    /**
     * @param scanner Scanner from the game instance.
     * @param challenged Player that played the draw four card.
     * @return whether the current player wants to challenge or not.
     */
    public static boolean promptForChallenge(Scanner scanner, Player challenged){
        int choice = -1;
        while(choice == -1){
            System.out.printf(CHALLENGE_PROMPT_SITUATION,
                    challenged.getName(), challenged.getHand().getCardsContained().size());
            System.out.printf(CHALLENGE_PROMPT_QUESTION);
            System.out.printf(CHALLENGE_RULES);
            String input = scanner.nextLine();
            if (List.of("yes", "y", "yeah", "affirmative").contains(input.toLowerCase())) {
                choice = 1;
            } else if(List.of("no", "n", "nah", "negative").contains(input.toLowerCase())){
                choice = 0;
            } else {
                System.out.println("Invalid input.");
            }
        }
        return choice == 1;
    }
}
