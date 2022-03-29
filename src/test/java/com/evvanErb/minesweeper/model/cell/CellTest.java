package com.evvanErb.minesweeper.model.cell;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import com.evvanErb.minesweeper.model.cell.Cell;
import com.evvanErb.minesweeper.model.board.Board;

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
  }

}
