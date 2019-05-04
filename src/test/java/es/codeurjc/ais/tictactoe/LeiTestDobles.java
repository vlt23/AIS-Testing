package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.codeurjc.ais.tictactoe.TicTacToeGame.Event;

public class LeiTestDobles {
	private TicTacToeGame juego;
	private Connection connection1,connection2;
	private Player player1,player2;
    @Before
    public void setUp() {
    	//given
        juego=new TicTacToeGame();
        connection1 =mock(Connection.class);
        connection2 =mock(Connection.class);    
    }

    @Test
    public void checkFlujoEvento() {
        //given
    	Event event = new Event();
    	event.type=TicTacToeGame.EventType.JOIN_GAME;
    	//doNothing().when(connection1).sendEvent(event);  
    	juego.addConnection(connection1);
    	juego.addConnection(connection2);    	    	    	
    	
    	player1=new Player(0, "X", "a");
    	player2=new Player(1, "O", "b");    	    
    	
    	List<Object> l=new ArrayList<Object>();
    	l.add(player1);
    	l.add(player2);
    	//when
    	juego.addPlayer(player1);
    	juego.addPlayer(player2);
    	
    	//then    	
    	verify(connection1,times(2)).sendEvent(TicTacToeGame.EventType.JOIN_GAME, l);
    	verify(connection2,times(2)).sendEvent(TicTacToeGame.EventType.JOIN_GAME, l);
    	
    }

}
