package com.evvanErb.minesweeper.model.board;

import java.util.ArrayList;

import com.evvanErb.minesweeper.model.cell.Cell;

public class Board {
    
    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<Cell> nonMineCells;
    private int size;

    public Board() { }

    public Board(int size) {
        this.cells = this.generateBoard(size);
    }

    public void mineRevealed() {
        //TODO tell handler game over player lost
    }

    public void reveal(int toRevealXPosition, int toRevealYPosition) {
        
        //out of bounds case
        if (toRevealXPosition < 0 || toRevealYPosition < 0 || toRevealXPosition >= size || toRevealYPosition >= size) {
            return;
        }

        Cell toReveal = cells.get(toRevealYPosition).get(toRevealXPosition);

        if (! toReveal.getIsRevealed()) {
            toReveal.reveal();
        }
    }

    public void changeFlag(int toRevealXPosition, int toRevealYPosition) {

        Cell toChangeFlag = cells.get(toRevealYPosition).get(toRevealXPosition);
        if (! toChangeFlag.getIsRevealed()) {
            toChangeFlag.changeFlag();
        }
    }

    protected ArrayList<ArrayList<Cell>> generateBoard(int size) {

        ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(size);

        ArrayList<int[]> minePositions = this.generateMinePositions(size);
        
        for(int column = 0; column < size; column++) {

            ArrayList<Cell> currentRow = new ArrayList<Cell>(size);

            for(int row = 0; row < size; row++) {

                boolean isMine = this.isMine(minePositions, column, row);
                int numMinesAdjacent = this.numAdjacentMines(minePositions, column, row);

                Cell currentCell = new Cell(this, isMine, numMinesAdjacent, column, row);

                if (! isMine) {
                    this.nonMineCells.add(currentCell);
                }

                currentRow.add(currentCell);
            }

            cells.add(currentRow);
        }

        return cells;
    }

    protected boolean isMine(ArrayList<int[]> minePositions, int xPosition, int yPosition) {

        if (minePositions == null) { return false; }

        for (int[] mine : minePositions) {

            if (mine.length != 2) { return false; }

            int mineXPosition = mine[0];
            int mineYPosition = mine[1];

            if (mineXPosition == xPosition && mineYPosition == yPosition) {
                return true;
            }
        }
        return false;
    }

    protected int numAdjacentMines(ArrayList<int[]> minePositions, int xPosition, int yPosition) {
        int numAdjacentMines = 0;

        if (minePositions == null) { return numAdjacentMines; }

        for (int[] mine : minePositions) {

            if (mine.length != 2) { return 0; }

            int mineXPosition = mine[0];
            int mineYPosition = mine[1];

            if ((xPosition - 1 == mineXPosition || xPosition + 1 == mineXPosition || xPosition == mineXPosition) &&
                (yPosition - 1 == mineYPosition || yPosition + 1 == mineYPosition || yPosition == mineYPosition)) {

                numAdjacentMines += 1;
            }
        }

        return numAdjacentMines;
    }

    protected ArrayList<int[]> generateMinePositions(int size) {
        //TODO add random generation of points
        return new ArrayList<int[]>();
    }
}
