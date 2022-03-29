package com.evvanErb.minesweeper.model.board;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import java.util.ArrayList;
import com.evvanErb.minesweeper.model.board.Board;

public class BoardTest {

  private Board board;

  @Before
  public void setUp() throws Exception {
    this.board = new Board();
  }
  
  @Test
  public void testIsMineTrue() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();

    for (int i = 0; i < 8; i++) {
      int[] point = {i, i};
      minePositions.add(point);
    }


    assertEquals(true, this.board.isMine(minePositions, 7, 7));
  }

  @Test
  public void testIsMineFalse() {
    ArrayList<int[]> minePositions = new ArrayList<int[]>();

    for (int i = 0; i < 5; i++) {
      int[] point = {i, i};
      minePositions.add(point);
    }


    assertEquals(false, this.board.isMine(minePositions, 7, 7));
  }

  @Test
  public void testIsMineNull() {
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

    for (int i = 0; i < 7; i++) {
      int[] point = {i, i};
      minePositions.add(point);
    }

    int[] point = {6, 5};
    minePositions.add(point);

    int[] pointTwo = {4, 6};
    minePositions.add(pointTwo);


    assertEquals(1, this.board.numAdjacentMines(minePositions, 2, 4));
    assertEquals(2, this.board.numAdjacentMines(minePositions, 3, 4));
    assertEquals(3, this.board.numAdjacentMines(minePositions, 5, 4));
    assertEquals(4, this.board.numAdjacentMines(minePositions, 5, 6));
  }

  @Test
  public void testNumAdjacentMinesNull() {
    assertEquals(0, this.board.numAdjacentMines(null, 7, 7));
  }

  @Test
  public void testGenerateMinePositions() {
      
  }
}

