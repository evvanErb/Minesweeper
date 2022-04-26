package com.evvanErb.minesweeper.view.terminalview;

import static org.junit.Assert.assertEquals;

import com.evvanErb.minesweeper.model.board.Board;
import com.evvanErb.minesweeper.model.cell.Cell;
import com.evvanErb.minesweeper.viewmodel.gamemanager.GameManager;
import org.junit.Test;
import org.junit.Before;

import java.io.*;
import java.util.ArrayList;

import static junit.framework.TestCase.fail;

public class TerminalViewTest {

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
    public void testGetDesiredBoardSize() throws IOException {
        TerminalView terminalView = new TerminalView();
        BufferedOutputStream out = new BufferedOutputStream(OutputStream.nullOutputStream());

        String input = "15";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        assertEquals(15, terminalView.getDesiredBoardSize(in, out));

        input = "-15";
        in = new ByteArrayInputStream(input.getBytes());
        assertEquals(-15, terminalView.getDesiredBoardSize(in, out));

        input = "abc\ndef\n15";
        in = new ByteArrayInputStream(input.getBytes());
        assertEquals(15, terminalView.getDesiredBoardSize(in, out));

        input = "a5c\n2d\n-15";
        in = new ByteArrayInputStream(input.getBytes());
        assertEquals(-15, terminalView.getDesiredBoardSize(in, out));
    }

    @Test
    public void testRegexMatch() {
        TerminalView terminalView = new TerminalView();

        assertEquals(true, terminalView.regexMatch("0,0", "^[0-9]+,[0-9]+$"));
        assertEquals(true, terminalView.regexMatch("01,0", "^[0-9]+,[0-9]+$"));

        assertEquals(false, terminalView.regexMatch("0,0a", "^[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("0,", "^[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch(",0", "^[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("0a,0", "^[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("-1,0", "^[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("0", "^[0-9]+,[0-9]+$"));

        assertEquals(true, terminalView.regexMatch("f0,0", "^f[0-9]+,[0-9]+$"));
        assertEquals(true, terminalView.regexMatch("f01,0", "^f[0-9]+,[0-9]+$"));

        assertEquals(false, terminalView.regexMatch("0,0", "^f[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("f 01,0", "^f[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("0,0f", "^f[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("f0,", "^f[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("f,0", "^f[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("0f,0", "^f[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("f-1,0", "^f[0-9]+,[0-9]+$"));
        assertEquals(false, terminalView.regexMatch("f0", "^f[0-9]+,[0-9]+$"));
    }

    @Test
    public void testRevealCell() throws IOException {
        GameManager gameManager = new GameManager(this.buildBoard(2));
        TerminalView terminalView = new TerminalView();
        BufferedOutputStream out = new BufferedOutputStream(OutputStream.nullOutputStream());

        assertEquals(true, terminalView.revealCell(0,1, gameManager, out));
        assertEquals(false, terminalView.revealCell(1,0, gameManager, out));

        gameManager = new GameManager(this.buildBoard(2));
        assertEquals(false, terminalView.revealCell(0,0, gameManager, out));
    }

    @Test
    public void testHandleUserInput() throws IOException {
        GameManager gameManager = new GameManager(this.buildBoard(2));
        TerminalView terminalView = new TerminalView();
        BufferedOutputStream out = new BufferedOutputStream(OutputStream.nullOutputStream());

        assertEquals(true, terminalView.handleUserInput("0,1", gameManager, out));
        assertEquals(true, terminalView.handleUserInput("f0,0", gameManager, out));
        assertEquals(true, terminalView.handleUserInput("a,1", gameManager, out));
        assertEquals(true, terminalView.handleUserInput("h", gameManager, out));
        assertEquals(false, terminalView.handleUserInput("n", gameManager, out));
        assertEquals(true, terminalView.handleUserInput("f1,1", gameManager, out));
        assertEquals(false, terminalView.handleUserInput("1,1", gameManager, out));
        assertEquals(false, terminalView.handleUserInput("0,1", gameManager, out));
    }

}