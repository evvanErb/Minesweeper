package com.evvanErb.minesweeper.view.terminalview;

import com.evvanErb.minesweeper.model.board.GameStatus;
import com.evvanErb.minesweeper.viewmodel.gamemanager.GameManager;

import java.util.Scanner;

public class TerminalView {

    GameManager currentGame;
    boolean gameRunning;
    boolean currentGameRunning;
    Scanner scanner;

    public void main() {

        this.scanner = new Scanner(System.in);
        this.gameRunning = true;
        this.currentGameRunning = true;
        this.currentGame = new GameManager();

        while (this.gameRunning) {

            int boardSize = this.getDesiredBoardSize();
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
                    try {
                        resultingStatusAfterReveal = this.currentGame.revealCell(Integer.parseInt(toRevealPoint[0]), Integer.parseInt(toRevealPoint[1]));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

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

    protected int getDesiredBoardSize() {

        boolean gotInteger = false;
        int size = 0;

        do {
            try {
                System.out.print("Enter a board size: ");
                size = Integer.parseInt(this.scanner.nextLine());
                gotInteger = true;
            } catch (NumberFormatException error) { System.out.println("[!] Please enter a number"); }
        } while(! gotInteger);

        return size;
    }
}
