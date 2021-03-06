package com.evvanErb.minesweeper.model.board;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import static junit.framework.TestCase.fail;
import java.util.ArrayList;

import com.evvanErb.minesweeper.model.cell.Cell;

public class BoardTest {

  private Board board;

  @Before
  public void setUp() throws Exception {
    this.board = new Board();
  }
  
  @Test
  public void testIsMine() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();

    for (int i = 0; i < 8; i++) {
      int[] point = {i, i};
      minePositions.add(point);
    }


    assertEquals(true, this.board.isMine(minePositions, 7, 7));
    assertEquals(false, this.board.isMine(minePositions, 6, 7));
  }

  @Test
  public void testIsMineNegativeCases() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();

    int[] point = {6};
    minePositions.add(point);

    for (int i = 0; i < 9; i++) {
      int[] pointOne = {i, i};
      minePositions.add(pointOne);
    }

    assertEquals(false, this.board.isMine(minePositions, 7, 7));

    assertEquals(false, this.board.isMine(null, 7, 7));
  }

  @Test
  public void testNumAdjacentMinesZero() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();

    for (int i = 0; i < 4; i++) {
      int[] point = {i, i};
      minePositions.add(point);
    }


    assertEquals(0, this.board.numAdjacentMines(minePositions, 7, 7));
  }

  @Test
  public void testNumAdjacentMinesNonZero() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();

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

    assertEquals(1, this.board.numAdjacentMines(minePositions, 2, 4));
    assertEquals(2, this.board.numAdjacentMines(minePositions, 3, 4));
    assertEquals(3, this.board.numAdjacentMines(minePositions, 5, 4));
    assertEquals(4, this.board.numAdjacentMines(minePositions, 5, 6));
    assertEquals(8, this.board.numAdjacentMines(minePositions, 8, 8));
  }

  @Test
  public void testNumAdjacentMinesNull() {
    assertEquals(0, this.board.numAdjacentMines(null, 7, 7));
  }

  @Test
  public void testNumAdjacentMinesBadMine() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();

    int[] point = {6};
    minePositions.add(point);

    for (int i = 0; i < 9; i++) {
      int[] pointOne = {i, i};
      minePositions.add(pointOne);
    }

    assertEquals(0, this.board.numAdjacentMines(minePositions, 7, 7));
  }

  @Test
  public void testGenerateMinePositions() {
    ArrayList<int[]> minePositions = this.board.generateMinePositions(25);

    int numberOfMines = minePositions.size();
    if (numberOfMines != (25 * 25 * 15 / 100)) {
      fail("[!] Wrong amount of mines");
    }

    for (int i = 0; i < numberOfMines; i++) {

      int[] mine = minePositions.get(i);

      if (mine.length != 2) { fail("[!] Wrong mine array coordiantes size"); }

      int mineXPosition = mine[0];
      int mineYPosition = mine[1];

      if (mineXPosition < 0 || mineXPosition >= 25 || mineYPosition < 0 || mineYPosition >= 25) {
        fail("[!] Mine out of bounds");
      }

      for (int j = i+1; j < numberOfMines; j++) {

        int[] comparingMine = minePositions.get(j);

        if (comparingMine.length != 2) { fail("[!] Wrong mine array coordiantes size"); }
        
        if (comparingMine[0] == mineXPosition && comparingMine[1] == mineYPosition) {
          fail("[!] Duplicate mine");
        }
      }
    }
  }

  @Test
  public void testGenerateMinePositionsSmallSizes() {
    ArrayList<int[]> minePositions = this.board.generateMinePositions(0);
    assertEquals(null, minePositions);
    minePositions = this.board.generateMinePositions(2);
    assertEquals(0, minePositions.size());
    minePositions = this.board.generateMinePositions(3);
    assertEquals(1, minePositions.size());
  }

  @Test
  public void testGenerateRandomCoordsNoDups() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();
    for (int i = 0; i < 2; i++) {
      int[] point = {i, i};
      minePositions.add(point);
    }
    int[] pointOne = {0, 1};
    minePositions.add(pointOne);

    int[] mine = this.board.generateRandomCoordsNoDups(minePositions, 2);
    if (mine.length != 2) { fail("[!] Wrong mine array coordiantes size"); }

    if (mine[0] != 1 || mine[1] != 0) {
      fail("[!] Wrong position for mine. Only one was possible");
    }
  }

  @Test
  public void testGenerateRandomCoordsNoDupsNotPossible() {

    ArrayList<int[]> minePositions = new ArrayList<int[]>();
    for (int i = 0; i < 2; i++) {
      int[] point = {i, i};
      minePositions.add(point);
    }
    int[] pointOne = {0, 1};
    minePositions.add(pointOne);
    int[] pointTwo = {1, 0};
    minePositions.add(pointTwo);

    assertEquals(null, this.board.generateRandomCoordsNoDups(minePositions, 2));

    minePositions = new ArrayList<int[]>();
    assertEquals(null, this.board.generateRandomCoordsNoDups(minePositions, -1));
  }

  @Test
  public void testGenerateRandomCoordsNoDupsNull() {
    assertEquals(null, this.board.generateRandomCoordsNoDups(null, 10));
  }

  @Test
  public void testGenerateBoard() {

    ArrayList<ArrayList<Cell>> cells = this.board.generateBoard(25);

    if (cells.size() != 25) {
      fail("[!] Wrong amount of rows");
    }
    for (int row = 0; row < 25; row++) {
      if (cells.get(row).size() != 25) {
        fail("[!] Wrong amount of columns");
      }
    }

    int numMines = 0;
    for (int row = 0; row < 25; row++) {
      for (int column = 0; column < 25; column++) {
        if (cells.get(row).get(column).getIsMine()) {
          numMines++;
        }
      }
    }

    if (numMines != (25 * 25 * 15 / 100)) {
      fail("[!] Wrong number of mines");
    }
  }

  @Test
  public void testGenerateBoardSizeOutOfBounds() {
    assertEquals(this.board.MIN_BOARD_SIZE, this.board.generateBoard(9).size());
    assertEquals(this.board.MAX_BOARD_SIZE, this.board.generateBoard(51).size());
  }

  @Test
  public void testGenerateBoardInOrder() {
    ArrayList<ArrayList<Cell>> cells = this.board.generateBoard(10);

    int rowIndex = 0;
    for (ArrayList<Cell> row : cells) {
      int columnIndex = 0;
      for (Cell cell : row) {
        int[] cellPosition = cell.getPosition();
        if (columnIndex != cellPosition[0] && rowIndex != cellPosition[1]) {
          fail("[!] Cells misaligned in board");
        }
        columnIndex += 1;
      }
      rowIndex += 1;
    }
  }
}

