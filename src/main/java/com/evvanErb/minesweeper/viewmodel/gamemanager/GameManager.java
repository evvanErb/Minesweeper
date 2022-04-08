package com.evvanErb.minesweeper.viewmodel.gamemanager;

import com.evvanErb.minesweeper.model.board.*;

public class GameManager {

    private Board currentBoard;

    public GameManager() {    }

    public GameManager(Board starterBoard) {
        this.currentBoard = starterBoard;
    }

    public void startGame(int size) {
        this.currentBoard = new Board(size);
    }

    public String getBoardAsString() {
        return this.currentBoard.getBoardAsString();
    }

    public GameStatus revealCell(int xPositionToReveal, int yPositionToReveal) throws InterruptedException {
        this.currentBoard.reveal(xPositionToReveal, yPositionToReveal);
        Thread.sleep(500);
        return this.currentBoard.checkForVictory();
    }

    public void changeCellFlag(int xPositionToFlag, int yPositionToFlag) {
        this.currentBoard.changeFlag(xPositionToFlag, yPositionToFlag);
    }

    public Board getCurrentBoard() {
        return this.currentBoard;
    }

    public int getBoardSize() { return this.currentBoard.getBoardSize(); }

    public String getBoardAsAPI() {
        return this.currentBoard.getBoardAsAPI();
    }
}
