package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Event;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class ValenTestDobles {

    private TicTacToeGame game;
    private Connection connection1, connection2;
    private Player player1, player2;

    @Before
    public void setUp() {
        // given
        // 1
        game = new TicTacToeGame();
        // 2
        connection1 = mock(Connection.class);
        connection2 = mock(Connection.class);
        // 4
        player1 = new Player(0, "X", "a");
        player2 = new Player(1, "O", "b");
    }

    private void ticTacToeGameDoblesTest() {
        // given
        Event event = new Event();
        event.type = TicTacToeGame.EventType.JOIN_GAME;
        // 3
        game.addConnection(connection1);
        game.addConnection(connection2);

        // when
        // 5
        game.addPlayer(player1);
        game.addPlayer(player2);

        // then
        // 6 and 7
        verify(connection1, times(2)).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME),
                argThat(hasItems(player1, player2)));
        verify(connection2, times(2)).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME),
                argThat(hasItems(player1, player2)));
    }

    @Test
    public void winDobleTest() {
        ticTacToeGameDoblesTest();
        // 8
        ArgumentCaptor<Player> argument = ArgumentCaptor.forClass(Player.class);
        // Why times(2) cause test to fail???
        // But two times(1) is ok???
        verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                argument.capture());
        Object event = argument.getValue();
        assertEquals(player1, event);
        verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                argument.capture());
        event = argument.getValue();

        assertEquals(player1, event);
        reset(connection1);

        /* X O X
           O X O
           X
         */
        for (int i = 0; i < 7; i++) {
            if (game.checkTurn(player1.getId())) {
                game.mark(i);
                if (i != 6) {  // Player 1 win, so no more SET_TURN
                    verify(connection2, times(2)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                            argument.capture());
                    event = argument.getValue();

                    assertEquals(player2, event);
                    reset(connection2);
                }
            } else {  // player 2
                game.mark(i);
                verify(connection1, times(2)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                        argument.capture());
                event = argument.getValue();

                assertEquals(player1, event);
                reset(connection1);
            }

        }
        // 9
        verify(connection1).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        event = argument.getValue();
        TicTacToeGame.WinnerValue winnerActual = (TicTacToeGame.WinnerValue) event;

        TicTacToeGame.WinnerValue winnerExpected = new TicTacToeGame.WinnerValue();
        winnerExpected.player = player1;
        winnerExpected.pos = new int[]{6, 4, 2};

        //assertEquals(winner, event);  // Different object...
        assertEquals(winnerExpected.player, winnerActual.player);
        assertThat(winnerExpected.pos, equalTo(winnerActual.pos));
    }

}
