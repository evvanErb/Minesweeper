package com.evvanErb.minesweeper.model.cell;

public class Cell {

    private final boolean isMine;
    private final int numMinesAdjacent;
    private final int xPosition;
    private final int yPosition;
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