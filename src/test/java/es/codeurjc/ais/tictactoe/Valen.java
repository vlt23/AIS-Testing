package es.codeurjc.ais.tictactoe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Valen {

    private Board board;

    @Before
    public void setUp() {
        WebApp.start();
        board = new Board();
    }

    @After
    public void shutdown() {
        WebApp.stop();
    }

    @Test
    public void win() {
        /* X O X
           O X O
           X
         */
        for (int i = 0; i < 7; i++) {
            TicTacToeGame.Cell cell = board.getCell(i);
            if (i % 2 == 0) {
                cell.value = "X";
            } else {
                cell.value = "O";
            }
        }
        assertThat(board.checkDraw(), is(false));
        int[] result = {6, 4, 2};
        assertThat(board.getCellsIfWinner("X"), is(result));
    }

}
