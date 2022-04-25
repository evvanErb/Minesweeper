package com.evvanErb.minesweeper.view.terminalview;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;

import java.io.*;

import static junit.framework.TestCase.fail;

public class TerminalViewTest {

    @Before
    public void setUp() throws Exception {

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
}