package com.evvanErb.minesweeper.model.board;

import java.util.ArrayList;
import java.util.Random;

import com.evvanErb.minesweeper.model.cell.Cell;

public class Board {
    
    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<Cell> nonMineCells;
    private int size;
    private int MIN_BOARD_SIZE = 10;
    private int MAX_BOARD_SIZE = 50;

    public Board() {   }

    public Board(int size) {
        this.size = size;
        this.cells = this.generateBoard(this.size);

        //TODO iterate over cells and get all non-mines
    }

    public void setBoard(ArrayList<ArrayList<Cell>> cells, int size) {
        this.cells = cells;
        this.size = size;
    }

    public ArrayList<ArrayList<Cell>> getBoard() {
        return this.cells;
    }

    public void mineRevealed() {
        //TODO tell handler game over player lost
    }

    public void reveal(int toRevealXPosition, int toRevealYPosition) {
        
        //out of bounds case
        if (toRevealXPosition < 0 || toRevealYPosition < 0 || toRevealXPosition >= size || toRevealYPosition >= size) {
            return;
        }

        this.cells.get(toRevealYPosition).get(toRevealXPosition).reveal();
    }

    public void changeFlag(int toRevealXPosition, int toRevealYPosition) {

        Cell toChangeFlag = this.cells.get(toRevealYPosition).get(toRevealXPosition);
        toChangeFlag.changeFlag();
    }

    protected ArrayList<ArrayList<Cell>> generateBoard(int size) {

        if (size < MIN_BOARD_SIZE || size > MAX_BOARD_SIZE) {
            return null;
        }

        ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(size);

        ArrayList<int[]> minePositions = this.generateMinePositions(size);
        
        for(int column = 0; column < size; column++) {

            ArrayList<Cell> currentRow = new ArrayList<Cell>(size);

            for(int row = 0; row < size; row++) {

                boolean isMine = this.isMine(minePositions, column, row);
                int numMinesAdjacent = this.numAdjacentMines(minePositions, column, row);

                Cell currentCell = new Cell(this, isMine, numMinesAdjacent, column, row);

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

        if (size <= 0) { return null; }

        int numMines = size * size * 15 / 100;
        ArrayList<int[]> minePositions = new ArrayList<int[]>();

        for(int i = 0; i < numMines; i++) {
            int[] currentMine = this.generateRandomCoordsNoDups(minePositions, size);

            minePositions.add(currentMine);
        }

        return minePositions;
    }

    protected int[] generateRandomCoordsNoDups(ArrayList<int[]> exisitingCoords, int size) {
        
        if (exisitingCoords == null || exisitingCoords.size() >= size*size || size <= 0) { return null; }

        Random r = new Random();
        boolean alreadyExists;
        int[] newCoord;

        do {
            alreadyExists = false;

            newCoord = new int[]{(r.nextInt(size)), (r.nextInt(size))};

            for (int i = 0; i < exisitingCoords.size(); i++) {

                if (newCoord[0] == exisitingCoords.get(i)[0] && newCoord[1] == exisitingCoords.get(i)[1]) {
                    alreadyExists = true;
                }
            }
        } while(alreadyExists);

        return newCoord;
    }
}
