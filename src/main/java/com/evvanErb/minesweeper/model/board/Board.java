package com.evvanErb.minesweeper.model.board;

import java.util.ArrayList;

import com.evvanErb.minesweeper.model.cell.Cell;

public class Board {
    
    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<Cell> nonMineCells;
    private int size;
    private int MIN_BOARD_SIZE = 10;
    private int MAX_BOARD_SIZE = 50;

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

        cells.get(toRevealYPosition).get(toRevealXPosition).reveal();
    }

    public void changeFlag(int toRevealXPosition, int toRevealYPosition) {

        Cell toChangeFlag = cells.get(toRevealYPosition).get(toRevealXPosition);
        toChangeFlag.changeFlag();
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

        if (size < MIN_BOARD_SIZE || size > MAX_BOARD_SIZE) {
            return null;
        }

        int numMines = size * 15 / 100;
        ArrayList<int[]> minePositions = new ArrayList<int[]>();

        for(int i = 0; i < numMines; i++) {
            int[] currentMine = this.generateRandomCoordsNoDups(minePositions, size);
        }

        //TODO add random generation of points
        return minePositions;
    }

    protected int[] generateRandomCoordsNoDups(ArrayList<int[]> exisitingCoords, int size) {
        for (int i = 0; i < 8; i++) {
            int[] point = {i, i};
            minePositions.add(point);
        }
        int[] point = {6, 5};
        minePositions.add(point);
        int[] pointTwo = {4, 6};
        minePositions.add(pointTwo);
        int[] pointThree = {7, 8};
        minePositions.add(pointThree);
        int[] pointFour = {7, 9};
        minePositions.add(pointFour);
        int[] pointFive = {8, 9};
        minePositions.add(pointFive);
        int[] pointSix = {9, 9};
        minePositions.add(pointSix);
        int[] pointSeven = {9, 8};
        minePositions.add(pointSeven);
        int[] pointEight = {9, 7};
        minePositions.add(pointEight);
        int[] pointNine = {8, 7};
        minePositions.add(pointNine);
    }
}
