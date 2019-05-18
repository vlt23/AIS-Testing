package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Event;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItems;
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

    @Test
    public void TicTacToeGameDoblesTest() {
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

}
