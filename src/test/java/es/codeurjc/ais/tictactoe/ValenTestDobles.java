package es.codeurjc.ais.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ValenTestDobles {

    Connection connection1, connection2;
    TicTacToeGame ticTacToeGame;

    Player player1, player2;

    TicTacToeGame.Event event;

    @Before
    public void setUp() {
        connection1 = mock(Connection.class);
        connection2 = mock(Connection.class);

        ticTacToeGame = new TicTacToeGame();
        player1 = new Player(0, "X", "a");
        player2 = new Player(1, "O", "b");

        event = new TicTacToeGame.Event();
        event.type = TicTacToeGame.EventType.JOIN_GAME;
    }

    @Test
    public void addConnectionTest() {
        ticTacToeGame.addConnection(connection1);
        ticTacToeGame.addConnection(connection2);

        ticTacToeGame.addPlayer(player1);
        //ticTacToeGame.addPlayer(player2);

        doNothing().when(connection1).sendEvent(TicTacToeGame.EventType.JOIN_GAME, player1);
        //doNothing().when(connection2).sendEvent(event);

        verify(connection1, times(1)).sendEvent(TicTacToeGame.EventType.JOIN_GAME, player1);
        //verify(connection2, times(1)).sendEvent(event);
    }

}
