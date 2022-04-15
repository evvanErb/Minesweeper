package com.evvanErb.minesweeper.viewmodel.gamemanager;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

import com.evvanErb.minesweeper.model.board.Board;
import com.evvanErb.minesweeper.model.board.GameStatus;
import com.evvanErb.minesweeper.model.cell.Cell;
import static junit.framework.TestCase.assertEquals;
import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;


public class GameManagerTest {

    @Before
    public void setUp() throws Exception {

    }

    private Board buildBoard(int size) {

        Board board = new Board();

        ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();

        for(int row = 0; row < size; row++) {

            ArrayList<Cell> currentRow = new ArrayList<Cell>();

            for(int column = 0; column < size; column++) {

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

        board.setBoard(cells, size);

        return board;
    }

    @Test
    public void testStartGameAndBoardSize() {
        GameManager game = new GameManager();

        game.startGame(15);
        assertEquals(15, game.getBoardSize());
    }

    @Test
    public void testRevealFlagBoardAPIandString() {
        Board board = this.buildBoard(3);
        GameManager game = new GameManager(board);

        game.revealCell(1,0);
        game.changeCellFlag(1, 1);
        assertEquals("(X)(2)(X)\n(X)(F)(X)\n(X)(X)(X)\n", game.getBoardAsString());
        assertEquals("X,2,X\nX,F,X\nX,X,X", game.getBoardAsAPI());
        if (GameStatus.RUNNING != game.getGameStatus()) {
            fail("[!] Game should still be running");
        }
        if (board != game.getCurrentBoard()) {
            fail("[!] Board pointers should be the same");
        }
    }
}
