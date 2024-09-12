package com.ridango.game.game;

import com.ridango.game.constants.AppConstants;
import com.ridango.game.model.Drink;
import com.ridango.game.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Component
public class Game {
    @Autowired
    private DrinkService drinkService;

    public Game() {
    }

    /**
     * This is the starting point of the game.
     */
    public void play() {
        int currentHighScore = 0;
        int currentScore = 0;
        boolean play = true;

        while (play) {
            Drink drink = drinkService.getNewDrink();
            printIntro(currentScore, currentHighScore);
            int roundScore = runRound(drink, currentScore);
            currentScore = roundScore != 0 ? currentScore + roundScore : roundScore;
            currentHighScore = Math.max(currentScore, currentHighScore);
            play = roundScore != 0 || continueGame();
        }
        System.out.println("Highest score achieved: " + currentHighScore + "\n");
        System.exit(0);
    }

    /**
     * This method is responsible for printing the intro to the game.
     *
     * @param currentScore
     * @param currentHighScore
     */

    private void printIntro(int currentScore, int currentHighScore) {
        System.out.println("==================================");
        System.out.println("Welcome to the COCKTAIL GAME !!!!");
        System.out.println("==================================\n");
        System.out.println("Current Score: " + currentScore);
        System.out.println("Current High Score: " + currentHighScore);
        System.out.println("==================================\n");
    }

    /**
     * This method is responsible for the processing of each round based on the drink name provided.
     *
     * @param drink
     * @param currentScore
     * @return current round score as {@link int}
     */
    private int runRound(Drink drink, int currentScore) {
        int drinkLength = drink.getStrDrink().length();
        int livesRemaining = AppConstants.MAX_LIVES;
        int exposedLetters = 0;
        Set<Integer> indexes = new HashSet<>();
        Set<Integer> spaceIndexes = new HashSet<>();

        Scanner s = new Scanner(System.in);

        for (int i = 0; i < drinkLength; i++) {
            if (drink.getStrDrink().charAt(i) == ' ') {
                spaceIndexes.add(i);
            }
        }

        System.out.println("The drink in question has " + (drinkLength - spaceIndexes.size()) + " letters.");
        System.out.println("Instructions on how to make the drink (hint): " + drink.getStrInstructions() + "\n");

        while (livesRemaining > 0) {
            if (livesRemaining == AppConstants.MAX_LIVES) {
                printWithHint(drink.getStrDrink(), 0, indexes, spaceIndexes);
            } else {
                System.out.println("********************");
                System.out.println("Attempts left: " + livesRemaining + "   |");
                System.out.println("********************\n");
                System.out.println("Instructions on how to make the drink (hint): " + drink.getStrInstructions() + "\n");

                printWithHint(drink.getStrDrink(), exposedLetters, indexes, spaceIndexes);
            }
            System.out.print("\nYour answer: ");
            String answer = s.nextLine();

            if (answer.equalsIgnoreCase(drink.getStrDrink())) {
                System.out.println("Correct !!!!!");
                return livesRemaining;
            }
            livesRemaining--;
            exposedLetters++;
            System.out.println("\nWrong answer !!");
        }
        System.out.println("Correct answer: " + drink.getStrDrink());
        System.out.println("Your final score: " + currentScore);
        return 0;
    }

    /**
     * This method prints the hints and dashes after evaluating the provided params.
     * The indexes {@link Set} contains the indexes that indicates which letter in the game can be exposed to the user.
     *
     * @param drinkName
     * @param numberOfHints
     * @param indexes
     * @param spaceIndexes
     */
    private void printWithHint(String drinkName, int numberOfHints, Set<Integer> indexes, Set<Integer> spaceIndexes) {
        while (numberOfHints != 0 && indexes.size() != numberOfHints) {
            int exposedIndex = generateRandomNumber(drinkName.length());
            if (!spaceIndexes.contains(exposedIndex)) {
                indexes.add(exposedIndex);
            }
        }

        for (int i = 0; i < drinkName.length(); i++) {
            if (indexes != null && indexes.contains(i)) {
                System.out.print(drinkName.charAt(i));
            } else if (!spaceIndexes.contains(i)) {
                System.out.print("-");
            } else {
                System.out.print(" ");
            }
        }
    }

    /**
     * This method returns a random number that falls in the provided range.
     *
     * @param range
     * @return {@link int}
     */
    private int generateRandomNumber(int range) {
        return (int) (Math.random() * range);
    }

    /**
     * This method is used for asking user for input regarding continuation of the game.
     *
     * @return {@link boolean}
     */
    private boolean continueGame() {
        Scanner s = new Scanner(System.in);
        System.out.print("\nContinue Playing (y/n)? : ");
        String answer = s.nextLine();
        return answer.equalsIgnoreCase("y");
    }

}
