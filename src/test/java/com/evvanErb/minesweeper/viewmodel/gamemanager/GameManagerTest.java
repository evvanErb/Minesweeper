package com.evvanErb.minesweeper.viewmodel.gamemanager;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Before;
import static junit.framework.TestCase.fail;

public class GameManagerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testInitialGameConditions() {
        GameManager game = new GameManager();

        game.startGame(12);
        assertEquals(12, game.getBoardSize());
    }
}
