package com.evvanErb.minesweeper.model;

import com.evvanErb.minesweeper.model.board.GameStatus;
import org.junit.Test;
import org.junit.Before;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import java.util.ArrayList;

import com.evvanErb.minesweeper.model.cell.*;
import com.evvanErb.minesweeper.model.board.*;

public class BoardCellTest {

  @Before
  public void setUp() throws Exception {

  }

  private Board buildBoard() {

    Board board = new Board();

    ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();

    for(int row = 0; row < 10; row++) {

        ArrayList<Cell> currentRow = new ArrayList<Cell>();

        for(int column = 0; column < 10; column++) {

            //make diagnols mines for testing
            boolean isMine = false;
            int numMinesAdjacent = 0;

            if (row == column) {
                isMine = true;
                numMinesAdjacent = 3;
            }
            else if (column - 1 == row || column + 1 == row) {
                numMinesAdjacent = 2;
            }
            else if (column - 2 == row || column + 2 == row) {
                numMinesAdjacent = 1;
            }

            Cell currentCell = new Cell(isMine, numMinesAdjacent, column, row);

            currentRow.add(currentCell);
        }

        cells.add(currentRow);
    }

    board.setBoard(cells, 10);

    return board;
  }
  
  @Test
  public void testRevealNonMine() {
    Board board = this.buildBoard();

    board.reveal(7, 2);
    ArrayList<ArrayList<Cell>> cells = board.getBoard();

    int row = 0;
    for (ArrayList<Cell> rowList : cells) {

        int column = 0;
        for (Cell cell : rowList) {
            
            if (column > row && ! cell.getIsRevealed()) {
                //(1,0) and (9,8) should not be revealed
                if (!((column == 1 && row == 0) || (column == 9 && row == 8))) {
                    fail("[!] Most of right side of diagnol should be visible " + Integer.toString(column) + " " + Integer.toString(row));
                }
            }
            else if (column == row && cell.getIsRevealed()) {
                fail("[!] Mines should not reveal " + Integer.toString(column) + " " + Integer.toString(row));
            }
            else if (column < row && cell.getIsRevealed()) {
                fail("[!] Left side should not reveal " + Integer.toString(column) + " " + Integer.toString(row));
            }

            column++;
        }

        row++;
    }

    board.reveal(2, 7);
    board.reveal(1, 0);
    cells = board.getBoard();

    row = 0;
    for (ArrayList<Cell> rowList : cells) {

        int column = 0;
        for (Cell cell : rowList) {
            
            if (column > row && ! cell.getIsRevealed()) {
                //(9,8) should not be revealed
                if (!(column == 9 && row == 8)) {
                    fail("[!] Most of right side of diagnol should be visible " + Integer.toString(column) + " " + Integer.toString(row));
                }
            }
            else if (column == row && cell.getIsRevealed()) {
                fail("[!] Mines should not reveal " + Integer.toString(column) + " " + Integer.toString(row));
            }
            else if (column < row && ! cell.getIsRevealed()) {
                //(0,1) and (8,9) should not be revealed
                if (!((column == 0 && row == 1) || (column == 8 && row == 9))) {
                    fail("[!] Left side should not reveal " + Integer.toString(column) + " " + Integer.toString(row));
                }
            }

            column++;
        }

        row++;
    }
  }

  @Test
  public void testRevealMine() {
    Board board = this.buildBoard();

    board.reveal(7,7);
    ArrayList<ArrayList<Cell>> cells = board.getBoard();

    int row = 0;
    for (ArrayList<Cell> rowList : cells) {

        int column = 0;
        for (Cell cell : rowList) {

            if (row == 7 && column == 7 && ! cell.getIsRevealed()) {
                fail("[!] Mine should be revealed");
            }
            else if (row != 7 && column != 7 && cell.getIsRevealed()){
                fail("[!] Only Mine should be revealed");
            }

            column++;
        }
        row++;
    }
  }

  @Test
  public void testRevealBadPoint() {
    Board board = this.buildBoard();

    board.reveal(-1,7);
    board.reveal(7, 11);
    ArrayList<ArrayList<Cell>> cells = board.getBoard();

    for (ArrayList<Cell> rowList : cells) {
        for (Cell cell : rowList) {

            if (cell.getIsRevealed()){
                fail("[!] Nothing should be revealed");
            }
        }
    }
  }

  @Test
  public void testChangeFlag() {
    Board board = this.buildBoard();

    board.changeFlag(7,7);
    board.changeFlag(2,8);
    board.changeFlag(9,1);
    ArrayList<ArrayList<Cell>> cells = board.getBoard();

    int row = 0;
    for (ArrayList<Cell> rowList : cells) {

        int column = 0;
        for (Cell cell : rowList) {

            if (((row == 7 && column == 7) || (row == 8 && column == 2) || (row == 1 && column == 9)) && ! cell.getIsFlagged()) {
                fail("[!] These points should be flagged " + Integer.toString(column) + " " + Integer.toString(row));
            }
            else if (cell.getIsFlagged() && !((row == 7 && column == 7) || (row == 8 && column == 2) || (row == 1 && column == 9))){
                fail("[!] Only the three specified points should be flagged " + Integer.toString(column) + " " + Integer.toString(row));
            }

            column++;
        }
        row++;
    }

    board.changeFlag(7,7);
    board.changeFlag(2,8);
    board.changeFlag(9,1);
    cells = board.getBoard();

    for (ArrayList<Cell> rowList : cells) {
        for (Cell cell : rowList) {

            if (cell.getIsFlagged()){
                fail("[!] Nothing should be flagged");
            }
        }
    }
  }

  @Test
  public void testChangeFlagBadPoint() {
    Board board = this.buildBoard();

    board.changeFlag(-1,7);
    board.changeFlag(7, 11);
    ArrayList<ArrayList<Cell>> cells = board.getBoard();

    for (ArrayList<Cell> rowList : cells) {
        for (Cell cell : rowList) {

            if (cell.getIsFlagged()){
                fail("[!] Nothing should be flagged");
            }
        }
    }
  }

    @Test
    public void testGameStausVictory() {
        Board board = this.buildBoard();

        board.reveal(2, 7);
        board.reveal(1, 0);
        assertEquals(GameStatus.RUNNING, board.checkForVictory());
        board.reveal(7, 2);
        board.reveal(0, 1);
        board.reveal(9, 8);
        board.reveal(8, 9);
        assertEquals(GameStatus.VICTORY, board.checkForVictory());
    }

    @Test
    public void testGameStausLost() {
        Board board = this.buildBoard();

        board.reveal(2, 7);
        board.reveal(1, 0);
        assertEquals(GameStatus.RUNNING, board.checkForVictory());
        board.reveal(7, 7);
        assertEquals(GameStatus.LOST, board.checkForVictory());
    }
}
