package com.evvanErb.minesweeper.model.cell;

import com.evvanErb.minesweeper.model.board.Board;

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

    public void reveal() {

        if (! this.isRevealed) {
            this.isRevealed = true;

            if (this.isFlagged) {
                this.isFlagged = false;
            }

            if (this.isMine) {
                this.board.mineRevealed();
            }
            else if (this.numMinesAdjacent == 0) {
                //reveal adjacent cells
                this.board.reveal((this.xPosition - 1), this.yPosition);
                this.board.reveal((this.xPosition + 1), this.yPosition);
                this.board.reveal(this.xPosition, (this.yPosition - 1));
                this.board.reveal(this.xPosition, (this.yPosition + 1));
                this.board.reveal((this.xPosition - 1), (this.yPosition - 1));
                this.board.reveal((this.xPosition + 1), (this.yPosition + 1));
                this.board.reveal((this.xPosition + 1), (this.yPosition - 1));
                this.board.reveal((this.xPosition - 1), (this.yPosition + 1));
            }
        }
    }

    public void changeFlag() {
        if (! this.isRevealed) {
            this.isFlagged = ! this.isFlagged;
        }
    }

    public boolean getIsRevealed() {
        return this.isRevealed;
    }

    public boolean getIsFlagged() {
        return this.isFlagged;
    }

    public boolean getIsMine() {
        return this.isMine;
    }

    public int getNumMinesAdjacent() {
        return this.numMinesAdjacent;
    }

    public int[] getPosition() {
        return new int[]{xPosition, yPosition};
    }

}