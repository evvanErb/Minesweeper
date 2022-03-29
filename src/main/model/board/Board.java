package main.model.board;

import java.util.ArrayList;

import main.model.cell.Cell;

public class Board {
    
    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<Cell> nonMineCells;
    private int size;

    public Board(int size) {
        this.cells = this.generateBoard(size);

        //generate cells
    }

    public void mineRevealed() {
        //TODO tell handler game over player lost
    }

    public void reveal(int toRevealXPosition, int toRevealYPosition, int callerXPosition, int callerYPosition) {
        //TODO add out of bound handling

        cells.get(toRevealYPosition).get(toRevealXPosition).reveal(callerXPosition, callerYPosition);
    }

    protected ArrayList<ArrayList<Cell>> generateBoard(int size) {

        ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(size);

        ArrayList<int[]> minePositions = this.generateMinePositions(size);
        
        for(int column = 0; column < size; column++) {

            ArrayList<Cell> currentRow = new ArrayList<Cell>(size);

            for(int row = 0; row < size; row++) {

                //TODO calc values for param
                boolean isMine = false;
                int numMinesAdjacent = 0;
                
                Cell currentCell = new Cell(this, isMine, numMinesAdjacent, column, row);

                currentRow.add(currentCell);
            }

            cells.add(currentRow);
        }

        return cells;
    }

    protected ArrayList<int[]> generateMinePositions(int size) {
        //TODO add random generation of points
        return new ArrayList<int[]>();
    }
}
