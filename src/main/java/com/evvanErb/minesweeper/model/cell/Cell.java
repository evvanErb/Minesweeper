package com.evvanErb.minesweeper.model.cell;

import com.evvanErb.minesweeper.model.board.Board;

public class Cell {

    private boolean isMine;
    private int numMinesAdjacent;
    private int xPosition;
    private int yPosition;
    private boolean isFlagged;
    private boolean isRevealed;

    public Cell(boolean isMine, int numMinesAdjacent, int xPosition, int yPosition) {
        this.isMine = isMine;
        this.numMinesAdjacent = numMinesAdjacent;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isFlagged = false;
        this.isRevealed = false;
    }

    public void reveal() {
        this.isRevealed = true;
        this.isFlagged = false;
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