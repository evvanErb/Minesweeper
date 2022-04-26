package com.evvanErb.minesweeper.view.terminalview;

import com.evvanErb.minesweeper.model.board.GameStatus;
import com.evvanErb.minesweeper.viewmodel.gamemanager.GameManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TerminalView {

    public void main() throws IOException {

        boolean gameRunning = true;
        GameManager currentGame = new GameManager();

        while (gameRunning) {
            int boardSize = this.getDesiredBoardSize(System.in, System.out);
            currentGame.startGame(boardSize);
            gameRunning = this.runCurrentGame(currentGame, System.in, System.out);
        }
    }

    protected boolean runCurrentGame(GameManager currentGame, InputStream inputStream, OutputStream outputStream) throws IOException {

        Scanner scanner = new Scanner(inputStream);
        boolean currentGameRunning = true;
        while (currentGameRunning) {

            outputStream.write((currentGame.getBoardAsString() + "\nEnter a command: ").getBytes());
            String userInput = scanner.nextLine();
            userInput = userInput.toLowerCase();

            if (userInput.equals("quit") || userInput.equals("q")) {
                return false;
            }
            else {
                currentGameRunning = this.handleUserInput(userInput, currentGame, outputStream);
            }
        }

        return true;
    }

    protected boolean handleUserInput(String userInput, GameManager currentGame, OutputStream outputStream) throws IOException {
        if (this.regexMatch(userInput, "^f[0-9]+,[0-9]+$")) {
            String[] toFlagPoint = userInput.substring(1).split(",");
            currentGame.changeCellFlag(Integer.parseInt(toFlagPoint[0]), Integer.parseInt(toFlagPoint[1]));
            return true;
        }
        else if (this.regexMatch(userInput, "^[0-9]+,[0-9]+$")) {
            String[] toRevealPoint = userInput.split(",");
            return this.revealCell(Integer.parseInt(toRevealPoint[0]), Integer.parseInt(toRevealPoint[1]), currentGame, outputStream);
        }
        else if (userInput.equals("help") || userInput.equals("h")) {
            outputStream.write(
                    """
                    To Quit: "quit" or "q"
                    For New Game: "new game" or "new" or "n"
                    To Flag a Point: "FX,Y"
                    To Reveal a Point: "X,Y"
                    To Print this Menu: "help" or "h"\n
                    """.getBytes()
            );
            return true;
        }
        else if (userInput.equals("new game") || userInput.equals("new") || userInput.equals("n")) {
            return false;
        }
        else {
            outputStream.write("[!] Unknown Command: type \"help\" for list of commands\n".getBytes());
            return true;
        }
    }

    protected boolean revealCell(int xPos, int yPos, GameManager game, OutputStream outputStream) throws IOException {
        GameStatus resultingStatusAfterReveal = game.revealCell(xPos, yPos);

        if (resultingStatusAfterReveal == GameStatus.VICTORY) {
            outputStream.write("YOU WON!\n".getBytes());
            return false;
        }
        else if (resultingStatusAfterReveal == GameStatus.LOST) {
            outputStream.write(game.getBoardAsString().getBytes());
            outputStream.write("YOU LOST!\n".getBytes());
            return false;
        }

        return true;
    }

    protected boolean regexMatch(String input, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    protected int getDesiredBoardSize(InputStream inputStream, OutputStream outputStream) throws IOException {

        Scanner scanner = new Scanner(inputStream);

        boolean gotInteger = false;
        int size = 0;

        do {
            try {
                outputStream.write("Enter a board size: ".getBytes());
                size = Integer.parseInt(scanner.nextLine());
                gotInteger = true;
            } catch (NumberFormatException error) { outputStream.write("[!] Please enter a number\n".getBytes()); }
        } while(! gotInteger);

        return size;
    }
}
