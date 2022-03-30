package com.evvanErb.minesweeper.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import static junit.framework.TestCase.fail;
import java.util.ArrayList;

import com.evvanErb.minesweeper.model.cell.Cell;
import com.evvanErb.minesweeper.model.board.Board;

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

            Cell currentCell = new Cell(board, isMine, numMinesAdjacent, column, row);

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
}
