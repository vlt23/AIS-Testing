package es.codeurjc.ais.tictactoe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class DoblesTest {

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

    private void ticTacToeGameDoblesFlujo() {
        // given
        // 3
        game.addConnection(connection1);
        game.addConnection(connection2);

        // when and then
        game.addPlayer(player1);  // 5
        // 6 and 7
        verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME),
                argThat(hasItems(player1)));
        verify(connection2, times(1)).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME),
                argThat(hasItems(player1)));
        reset(connection1);
        reset(connection2);

        game.addPlayer(player2);  // 5
        // 6 and 7
        verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME),
                argThat(hasItems(player1, player2)));
        verify(connection2, times(1)).sendEvent(eq(TicTacToeGame.EventType.JOIN_GAME),
                argThat(hasItems(player1, player2)));

        // 8
        verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                argument.capture());
        eventCaptor = argument.getValue();
        assertEquals(player1, eventCaptor);

        verify(connection2, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                argument.capture());
        eventCaptor = argument.getValue();
        assertEquals(player1, eventCaptor);

        reset(connection1);
        reset(connection2);
    }

    @Test
    public void player1WinDobleTest() {
        ticTacToeGameDoblesFlujo();
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
        TicTacToeGame.WinnerValue winnerExpected = new TicTacToeGame.WinnerValue();
        winnerExpected.player = player1;
        winnerExpected.pos = new int[]{6, 4, 2};

        verify(connection1).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        eventCaptor = argument.getValue();
        TicTacToeGame.WinnerValue winnerActual = (TicTacToeGame.WinnerValue) eventCaptor;
        //assertEquals(winner, eventCaptor);  // Different object...
        assertEquals(winnerExpected.player, winnerActual.player);
        assertThat(winnerExpected.pos, equalTo(winnerActual.pos));

        verify(connection2).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        eventCaptor = argument.getValue();
        winnerActual = (TicTacToeGame.WinnerValue) eventCaptor;
        assertEquals(winnerExpected.player, winnerActual.player);
        assertThat(winnerExpected.pos, equalTo(winnerActual.pos));
    }

    @Test
    public void drawDobleTest() {
        ticTacToeGameDoblesFlujo();
        /* X O X
           O X X
           O X O
         */
        for (int i = 0; i < 5; i++) {
            autoMark(i, true);
        }
        for (int i = 6; i < 9; i++) {
            autoMark(i, true);
        }
        game.mark(5);  // No more SET_TURN

        verify(connection1).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        eventCaptor = argument.getValue();
        assertNull(eventCaptor);

        verify(connection2).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        eventCaptor = argument.getValue();
        assertNull(eventCaptor);
    }

    @Test
    public void player2WinDobleTest() {
        ticTacToeGameDoblesFlujo();
        /* X O X
             O X
             O
         */
        for (int i = 0; i < 3; i++) {
            autoMark(i, true);
        }
        autoMark(4, true);
        autoMark(5, true);
        autoMark(7, false);

        TicTacToeGame.WinnerValue winnerExpected = new TicTacToeGame.WinnerValue();
        winnerExpected.player = player2;
        winnerExpected.pos = new int[]{1, 4, 7};

        verify(connection1).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        eventCaptor = argument.getValue();
        TicTacToeGame.WinnerValue winnerActual = (TicTacToeGame.WinnerValue) eventCaptor;
        assertEquals(winnerExpected.player, winnerActual.player);
        assertThat(winnerExpected.pos, equalTo(winnerActual.pos));

        verify(connection2).sendEvent(eq(TicTacToeGame.EventType.GAME_OVER), argument.capture());
        eventCaptor = argument.getValue();
        winnerActual = (TicTacToeGame.WinnerValue) eventCaptor;
        assertEquals(winnerExpected.player, winnerActual.player);
        assertThat(winnerExpected.pos, equalTo(winnerActual.pos));
    }

    private void autoMark(int i, boolean isSetTurn) {
        if (game.checkTurn(player1.getId())) {
            game.mark(i);
            if (isSetTurn) {
                verify(connection2, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                        argument.capture());
                eventCaptor = argument.getValue();

                assertEquals(player2, eventCaptor);
                reset(connection2);

                verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                        argument.capture());
                eventCaptor = argument.getValue();

                assertEquals(player2, eventCaptor);
                reset(connection1);
            }
        } else {  // player 2
            game.mark(i);
            if (isSetTurn) {
                verify(connection1, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                        argument.capture());
                eventCaptor = argument.getValue();

                assertEquals(player1, eventCaptor);
                reset(connection1);

                verify(connection2, times(1)).sendEvent(eq(TicTacToeGame.EventType.SET_TURN),
                        argument.capture());
                eventCaptor = argument.getValue();

                assertEquals(player1, eventCaptor);
                reset(connection2);
            }
        }
    }

}
