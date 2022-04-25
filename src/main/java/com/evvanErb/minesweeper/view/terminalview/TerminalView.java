package com.evvanErb.minesweeper.view.terminalview;

import com.evvanErb.minesweeper.model.board.GameStatus;
import com.evvanErb.minesweeper.viewmodel.gamemanager.GameManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class TerminalView {

    GameManager currentGame;
    boolean gameRunning;
    boolean currentGameRunning;

    public void main() throws IOException {

        Scanner scanner = new Scanner(System.in);
        this.gameRunning = true;
        this.currentGameRunning = true;
        this.currentGame = new GameManager();

        while (this.gameRunning) {

            int boardSize = this.getDesiredBoardSize(System.in, System.out);
            this.currentGame.startGame(boardSize);
            this.currentGameRunning = true;

            while (this.currentGameRunning) {
                System.out.println(this.currentGame.getBoardAsString());
                System.out.print("Enter a command: ");

                String userInput = scanner.nextLine();
                userInput = userInput.toLowerCase();

                if (userInput.equals("quit") || userInput.equals("q")) {
                    this.gameRunning = false;
                    this.currentGameRunning = false;
                }
                else if (userInput.equals("new game") || userInput.equals("new") || userInput.equals("n")) {
                    this.currentGameRunning = false;
                }
                else if (userInput.length() > 1 && userInput.charAt(0) == 'f') {
                    String[] toFlagPoint = userInput.substring(1).split(",");
                    this.currentGame.changeCellFlag(Integer.parseInt(toFlagPoint[0]), Integer.parseInt(toFlagPoint[1]));
                }
                else {
                    String[] toRevealPoint = userInput.split(",");

                    GameStatus resultingStatusAfterReveal = null;
                    resultingStatusAfterReveal = this.currentGame.revealCell(Integer.parseInt(toRevealPoint[0]), Integer.parseInt(toRevealPoint[1]));

                    if (resultingStatusAfterReveal == GameStatus.VICTORY) {
                        System.out.println("YOU WON!");
                        this.currentGameRunning = false;
                    }
                    else if (resultingStatusAfterReveal == GameStatus.LOST) {
                        System.out.println(this.currentGame.getBoardAsString());
                        System.out.println("YOU LOST!");
                        this.currentGameRunning = false;
                    }
                }
            }
        }
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
