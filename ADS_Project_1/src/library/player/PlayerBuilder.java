package library.player;

import library.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Helper methods for constructing the {@link Player} class, separated due to their length.
 */
public class PlayerBuilder {

    /**
     * @param scanner instance passed from {@code Game}.
     * @return [@Code List<Player>} The list of players to be included in the game.
     * Provides a series of prompts to get details to construct Player classes.
     */
    public static List<Player> promptForPlayers(Scanner scanner){
        int numOfPlayers = -1;
        while(numOfPlayers == -1){
            System.out.println("Please enter the number of players (between 2 and 10):");
            String input = scanner.nextLine();
            if(!Utils.isValid(input, 2, 10)){
                System.out.println("Invalid input.");
                continue;
            }
            numOfPlayers = Integer.parseInt(input);
        }

        List<Player> playerList = new ArrayList<>();

        for(int i = 0; i < numOfPlayers; i++){
            playerList.add(new Player(getPlayerName(scanner), getSortingPreference(scanner)));
        }
        return playerList;
    }

    /**
     * @param scanner instance passed from {@code Game}.
     * @return {@link SortingPreference} The player's sorting preference.
     * Provides a prompt to confirm the players' sorting preferences.
     */
    private static SortingPreference getSortingPreference(Scanner scanner){
        int sortingPreference = -1;
        while(sortingPreference == -1){
            System.out.println("Enter your sorting preference:\n0 for none,\n1 to sort by colour,\n2 to sort by value.");
            String input = scanner.nextLine();
            if(!Utils.isValid(input, 0, 2)){
                System.out.println("Invalid input.");
                continue;
            }
            sortingPreference = Integer.parseInt(input);
        }
        return SortingPreference.fromValue(sortingPreference);
    }

    /**
     * @param scanner instance passed from {@code Game}.
     * @return {@code String} next player's name.
     * Provides a prompt to confirm players' names.
     */
    private static String getPlayerName(Scanner scanner){
        String toReturn = null;
        while(toReturn == null){
            String errorMsg = null;
            System.out.println("Please enter the current player name (between 2 and 12 characters):");
            String input = scanner.nextLine();

            if(input.length() < 2) errorMsg = "Name too short!";
            else if(input.length() > 12) errorMsg = "Name too long!";
            else if(input.contains(" ")) errorMsg = "Only one word is allowed!";

            if(errorMsg != null){
                System.out.println(errorMsg);
                continue;
            }
            toReturn = input;
        }
        return Utils.neatCapitalize(toReturn);
    }
}
