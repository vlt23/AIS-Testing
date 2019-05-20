package es.codeurjc.ais.tictactoe;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class LeiDoblesTest {
    private TicTacToeGame game;
    private Connection connection1, connection2;
    private Player player1, player2;

    @Before
    public void setUp() {
        // given
        game = new TicTacToeGame();  // 1
        // 2
        connection1 = mock(Connection.class);
        connection2 = mock(Connection.class);
    }

    @Test
    public void checkFlujoEvento() {
        // given
        Event event = new Event();
        event.type = TicTacToeGame.EventType.JOIN_GAME;
        //doNothing().when(connection1).sendEvent(event);
        // 3
        game.addConnection(connection1);
        game.addConnection(connection2);

        // 4
        player1 = new Player(0, "X", "a");
        player2 = new Player(1, "O", "b");

        List<Object> l = new ArrayList<>();
        l.add(player1);
        l.add(player2);
        // when
        // 5
        game.addPlayer(player1);
        game.addPlayer(player2);

        // then
        verify(connection1, times(2)).sendEvent(TicTacToeGame.EventType.JOIN_GAME, l);  // 6
        verify(connection2, times(2)).sendEvent(TicTacToeGame.EventType.JOIN_GAME, l);  // 7

    }

}
