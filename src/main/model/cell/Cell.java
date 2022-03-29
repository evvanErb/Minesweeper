package main.model.cell;

import main.model.board.Board;

public class Cell {

    private Board board;
    private boolean isMine;
    private int numMinesAdjacent;
    private int xPosition;
    private int yPosition;
    private boolean isFlagged;
    private boolean isRevealed;

    public Cell(Board board, boolean isMine, int numMinesAdjacent, int xPosition, int yPosition) {
        this.board = board;
        this.isMine = isMine;
        this.numMinesAdjacent = numMinesAdjacent;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isFlagged = false;
        this.isRevealed = false;
    }

    public void reveal(int callerXPosition, int callerYPosition) {

        if (this.isMine) {
            board.mineRevealed();
            return;
        }

        this.isRevealed = true;

        //reveal adjacent cells
    }

    public void changeFlag() {
        this.isFlagged = ! this.isFlagged;
    }

}