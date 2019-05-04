package es.codeurjc.ais.tictactoe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class LeiTestUnitario {
    private Board tablero;


    @Before
    public void setup() {
        tablero = new Board();
    }

    @Test
    public void trueCheckDrawTest() {
        //given
        //dato dado
        /* X 0 X
         * 0 X X
         * 0 X 0
         * */
        asignarValores("XOXOXXOXO");
        //when
        boolean draw = tablero.checkDraw();
        //then
        assertThat(draw, is(true));
    }

    // FAIL
    @Test
    public void FalseCheckDrawTest() {
    	//given
        /* X 0 X
           0 X 0
           X X 0
         */    	
        asignarValores("XOXOXOXXO");
        //when
        boolean draw = tablero.checkDraw();
        //then
        assertThat(draw, is(false));
    }

    private void asignarValores(String letras) {
        for (int i = 0; i < letras.length(); i++) {
            tablero.getCell(i).value = Character.toString(letras.charAt(i));
        }
    }
}