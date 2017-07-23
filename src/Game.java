/**
 * Created by Kenzie on 4/25/2017.
 */

import java.io.*;
import java.util.Scanner;
import java.util.Random;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Game {

    private Player currentPlayer;
    private Player player1;
    private Player player2;
    private Player winner;
    private Scanner scan;
    private Random rand;
    private boolean isGameOver = false;
    private final String FILENAME = "winners.txt";




    public Game() {

        scan = new Scanner(System.in);
        rand = new Random();
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        currentPlayer = player1;
        gameLoop();
    }

    public static void main(String[] args) {
        Game game = new Game();
    }

    public void gameLoop() {
        displayPastWinners();
        welcome();
        while (isGameOver == false) {
            takeTurn();
            changeTurn();
            checkIfGameOver();
        }
        displayWinner();
        testingAppending();
    }

    public void welcome() {
        System.out.println("Welcome to Pig!\nPlayer 1 will go first! He/she will roll the die.\nIf they roll a 1, they" +
                " score 0 points and it becomes player 2's turn.\nIf they roll any other number, they may decide to hold " +
                " and add it to their final score,\nor to roll again in the hopes of rolling anything except a 1." +
                "\nThe first player to 100 points wins! Good luck!");
    }

    public void displayPastWinners() {
        File file = new File("winners.txt");
        System.out.println("Previous winners of Pig and their scores: ");
        try {
            Scanner scan = new Scanner(file);
            scan.useDelimiter(",");
            int line = 0;
            while (scan.hasNext()) {
                if (line == 0) {
                    System.out.print("Name: " + scan.next());
                    line = 1;
                }else if (line == 1) {
                    System.out.print(" Score: " + scan.next() + "\n");
                    line = 0;
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changeTurn() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        }else if (currentPlayer == player2) {
            currentPlayer = player1;
        }
    }

    public void takeTurn() {
        final int hold = 1;
        final int roll = 2;
        int currentRoll;
        int currentTurnRolls = 0;
        int answer;
        boolean continueTurn = true;
        while (continueTurn == true) {
            System.out.println("It is " + currentPlayer.getName() + "'s turn.");
            currentRoll = rand.nextInt(6) + 1;
            if (currentRoll == 1) {
                System.out.println("You rolled a 1! Unfortunate!");
                continueTurn = false;
            }else {
                currentTurnRolls = currentTurnRolls + currentRoll;
                System.out.println("You have rolled a " + currentRoll + ". Would you like to add " + currentTurnRolls + " to " +
                        "your current total score of " + currentPlayer.getPlayerScore() + ", or roll again?\n\t1. Hold\n\t2. Roll");
                answer = scan.nextInt();
                if (answer == hold) {
                    currentPlayer.setPlayerScore(currentPlayer.getPlayerScore() + currentTurnRolls);
                    continueTurn = false;
                } else if (answer == roll) {
                    System.out.println("Good luck.");
                }
            }
        }
    }

    public void checkIfGameOver() {
        final int winningScore = 100;
        if (player1.getPlayerScore() >= winningScore) {
            winner = player1;
            isGameOver = true;
        }else if (player2.getPlayerScore() >= winningScore) {
            winner = player2;
            isGameOver = true;
        }
    }

    public void displayWinner() {
        System.out.println(winner.getName() + " has won with a score of " + winner.getPlayerScore() + "! Congratulations!");
    }

    /*public void writeToWinners() {
        File file = new File("winners.txt");
        Writer output;
        try {
            output = new BufferedWriter(new FileWriter(file, true));
            output.append("," + winner.getName() + "," + winner.getPlayerScore());
        } catch (IOException e) {
            System.out.println("IOException.");
        }
    }*/
    public void writeToWinners() {
        try {
            BufferedWriter bw = null;
            FileWriter fw = null;
            File file = new File("winners.txt");
            String output = ("," + winner.getName() + "," + winner.getPlayerScore());
            System.out.println(output);
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testingAppending() {
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            String newContent = ("," + winner.getName() + "," + winner.getPlayerScore());
            File file = new File(FILENAME);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(newContent);
            System.out.println("Writing to file complete.");
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
