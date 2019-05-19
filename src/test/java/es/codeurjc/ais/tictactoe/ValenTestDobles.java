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

    private ArgumentCaptor<Player> argument;
    private Object eventCaptor;

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

        argument = ArgumentCaptor.forClass(Player.class);
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

        // 8
        // Why times(2) cause test to fail???
        // But two times(1) is ok???
        verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                argument.capture());
        eventCaptor = argument.getValue();
        assertEquals(player1, eventCaptor);
        verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                argument.capture());
        eventCaptor = argument.getValue();

        assertEquals(player1, eventCaptor);
        reset(connection1);
    }

    @Test
    public void winDobleTest() {
        ticTacToeGameDoblesTest();
        /* X O X
           O X O
           X
         */
        for (int i = 0; i < 7; i++) {
            if (i < 6) {
                autoMark(i, true);
            } else {
                autoMark(i, false);
            }
        }
        // 9
        verify(connection1).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        eventCaptor = argument.getValue();
        TicTacToeGame.WinnerValue winnerActual = (TicTacToeGame.WinnerValue) eventCaptor;

        TicTacToeGame.WinnerValue winnerExpected = new TicTacToeGame.WinnerValue();
        winnerExpected.player = player1;
        winnerExpected.pos = new int[]{6, 4, 2};

        //assertEquals(winner, eventCaptor);  // Different object...
        assertEquals(winnerExpected.player, winnerActual.player);
        assertThat(winnerExpected.pos, equalTo(winnerActual.pos));
    }

    private void autoMark(int i, boolean isSetTurn) {
        if (game.checkTurn(player1.getId())) {
            game.mark(i);
            if (isSetTurn) {
                verify(connection2, times(2)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                        argument.capture());
                eventCaptor = argument.getValue();

                assertEquals(player2, eventCaptor);
                reset(connection2);
            }
        } else {  // player 2
            game.mark(i);
            if (isSetTurn) {
                verify(connection1, times(2)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                        argument.capture());
                eventCaptor = argument.getValue();

                assertEquals(player1, eventCaptor);
                reset(connection1);
            }
        }
    }

}
