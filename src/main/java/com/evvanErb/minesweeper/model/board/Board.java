package com.evvanErb.minesweeper.model.board;

import java.util.ArrayList;
import java.util.Random;

import com.evvanErb.minesweeper.model.cell.Cell;

public class Board {
    
    private ArrayList<ArrayList<Cell>> cells;
    private ArrayList<Cell> nonMineCells;
    private int size;
    protected int MIN_BOARD_SIZE = 10;
    protected int MAX_BOARD_SIZE = 35;
    private GameStatus gameStatus;

    public Board() { this.gameStatus = GameStatus.RUNNING; }

    public Board(int size) {
        this.size = size;
        this.cells = this.generateBoard(this.size);
        this.gameStatus = GameStatus.RUNNING;
        this.nonMineCells = new ArrayList<Cell>();

        for (ArrayList<Cell> rowList : this.cells) {
            for (Cell cell : rowList) {

                if (! cell.getIsMine()) {
                    this.nonMineCells.add(cell);
                }
            }
        }
    }

    public void setBoard(ArrayList<ArrayList<Cell>> cells, int size) {
        this.cells = cells;
        this.size = size;
        this.nonMineCells = new ArrayList<Cell>();

        for (ArrayList<Cell> rowList : this.cells) {
            for (Cell cell : rowList) {

                if (! cell.getIsMine()) {
                    this.nonMineCells.add(cell);
                }
            }
        }
    }

    public ArrayList<ArrayList<Cell>> getBoard() {
        return this.cells;
    }

    public void mineRevealed() {
        this.gameStatus = GameStatus.LOST;
    }

    public GameStatus checkForVictory() {

        if (this.gameStatus == GameStatus.RUNNING) {

            this.gameStatus = GameStatus.VICTORY;
            for(Cell cell : this.nonMineCells) {

                if (! cell.getIsRevealed()) {
                    this.gameStatus = GameStatus.RUNNING;
                    break;
                }
            }
        }
        return this.gameStatus;
    }

    public void reveal(int toRevealXPosition, int toRevealYPosition) {
        
        //out of bounds case
        if (toRevealXPosition < 0 || toRevealYPosition < 0 || toRevealXPosition >= size || toRevealYPosition >= size) {
            return;
        }

        Cell cellToReveal = this.cells.get(toRevealYPosition).get(toRevealXPosition);

        if (cellToReveal.getIsMine()) {
            cellToReveal.reveal();
            this.mineRevealed();
        }
        else if (! cellToReveal.getIsRevealed()) {

            cellToReveal.reveal();
            if (cellToReveal.getNumMinesAdjacent() == 0) {
                this.reveal((toRevealXPosition - 1), toRevealYPosition);
                this.reveal((toRevealXPosition + 1), toRevealYPosition);
                this.reveal(toRevealXPosition, (toRevealYPosition - 1));
                this.reveal(toRevealXPosition, (toRevealYPosition + 1));
                this.reveal((toRevealXPosition - 1), (toRevealYPosition - 1));
                this.reveal((toRevealXPosition + 1), (toRevealYPosition + 1));
                this.reveal((toRevealXPosition + 1), (toRevealYPosition - 1));
                this.reveal((toRevealXPosition - 1), (toRevealYPosition + 1));
            }
        }
    }

    public String getBoardAsString() {
        String boardString = "";
        for (ArrayList<Cell> row : this.cells) {
            for (Cell cell : row) {
                if (cell.getIsFlagged()) {
                    boardString += ("(F)");
                }
                else if (cell.getIsRevealed() && cell.getIsMine()) {
                    boardString += ("(*)");
                }
                else if (cell.getIsRevealed()) {
                    boardString += ("(" + cell.getNumMinesAdjacent() + ")");
                }
                else {
                    boardString += ("(X)");
                }
            }
            boardString += "\n";
        }

        return boardString;
    }

    public String getBoardAsAPI() {
        String boardAPIString = "";
        for (ArrayList<Cell> row : this.cells) {
            for (Cell cell : row) {
                if (cell.getIsFlagged()) {
                    boardAPIString += ("F,");
                }
                else if (cell.getIsRevealed() && cell.getIsMine()) {
                    boardAPIString += ("*,");
                }
                else if (cell.getIsRevealed()) {
                    boardAPIString += cell.getNumMinesAdjacent() + ",";
                }
                else {
                    boardAPIString += ("X,");
                }
            }
            boardAPIString = boardAPIString.substring(0, boardAPIString.length() - 1);
            boardAPIString += "\n";
        }
        boardAPIString = boardAPIString.substring(0, boardAPIString.length() - 1);

        return boardAPIString;
    }

    public String getBoardAsStringAllRevealed() {
        String boardString = "";
        for (ArrayList<Cell> row : this.cells) {
            for (Cell cell : row) {
                if (cell.getIsMine()) {
                    boardString += ("(*)");
                }
                else {
                    boardString += ("(" + cell.getNumMinesAdjacent() + ")");
                }
            }
            boardString += "\n";
        }
        return boardString;
    }

    public void changeFlag(int toFlagXPosition, int toFlagYPosition) {

        //out of bounds case
        if (toFlagXPosition < 0 || toFlagYPosition < 0 || toFlagXPosition >= size || toFlagYPosition >= size) {
            return;
        }

        Cell toChangeFlag = this.cells.get(toFlagYPosition).get(toFlagXPosition);
        toChangeFlag.changeFlag();
    }

    protected ArrayList<ArrayList<Cell>> generateBoard(int size) {

        if (size < MIN_BOARD_SIZE) {
            size = MIN_BOARD_SIZE;
            this.size = size;
        }
        else if (size > MAX_BOARD_SIZE) {
            size = MAX_BOARD_SIZE;
            this.size = size;
        }

        ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>(size);

        ArrayList<int[]> minePositions = this.generateMinePositions(size);
        
        for(int row = 0; row < size; row++) {

            ArrayList<Cell> currentRow = new ArrayList<Cell>(size);

            for(int column = 0; column < size; column++) {

                boolean isMine = this.isMine(minePositions, column, row);
                int numMinesAdjacent = this.numAdjacentMines(minePositions, column, row);

                Cell currentCell = new Cell(isMine, numMinesAdjacent, column, row);

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

        Random r = new Random(System.currentTimeMillis());
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

    public int getBoardSize() { return this.size; }
}
