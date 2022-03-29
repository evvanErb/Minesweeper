package com.evvanErb.minesweeper.model.cell;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.evvanErb.minesweeper.model.board.Board;

@RunWith(MockitoJUnitRunner.class)
public class CellTest {

  @Mock
  private Board mockBoard;

  @Before
  public void setUp() throws Exception {

  }
  
  @Test
  public void testChangeFlag() {

    Cell cell = new Cell(this.mockBoard, false, 1, 1, 1);
    assertEquals(false, cell.getIsFlagged());
    cell.changeFlag();
    assertEquals(true, cell.getIsFlagged());
    cell.changeFlag();
    assertEquals(false, cell.getIsFlagged());
    cell.changeFlag();
    assertEquals(true, cell.getIsFlagged());

    cell.reveal();
    assertEquals(false, cell.getIsFlagged());
    cell.changeFlag();
    assertEquals(false, cell.getIsFlagged());    
  }

  @Test
  public void testRevealNonMine() {

    Cell cell = new Cell(this.mockBoard, false, 1, 5, 5);
    assertEquals(false, cell.getIsRevealed());

    cell.reveal();

    assertEquals(true, cell.getIsRevealed());
  }

  @Test
  public void testRevealMine() {

    Cell cell = new Cell(this.mockBoard, true, 1, 5, 5);
    assertEquals(false, cell.getIsRevealed());

    cell.reveal();

    assertEquals(true, cell.getIsRevealed());
  }

}
