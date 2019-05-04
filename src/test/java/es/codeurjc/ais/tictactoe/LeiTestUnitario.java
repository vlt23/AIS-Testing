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

    @BeforeClass
    public static void setupTest() {

    }

    @AfterClass
    public static void teardown() {

    }

    @Before
    public void setup() {
        tablero = new Board();
        tablero.checkDraw();
    }

    @After
    public void close() {

    }

    @Test
    public void trueDrawTest() {
        //given
        //dato dado
        /* X 0 X
         * 0 X X
         * 0 X 0
         * */
        asignarValores("X0X0XX0X0");
        //when
        boolean draw = tablero.checkDraw();
        //then
        assertThat(draw, is(true));
    }

    @Test
    public void falseDrawTest() {
        //given
        //dato dado
        /* X 0 X
         * 0 X X
         * 0 X 0
         * */
        tablero.getCell(0).value = "X";
        tablero.getCell(1).value = "0";
        tablero.getCell(2).value = "X";

        tablero.getCell(3).value = "0";
        tablero.getCell(4).value = "X";
        tablero.getCell(5).value = "X";

        tablero.getCell(6).value = "0";
        tablero.getCell(7).value = "X";
        tablero.getCell(8).value = "0";


    }

    // FAIL
    @Test
    public void drawTestFail() {
        /* X 0 X
           0 X 0
           X X 0
         */
        asignarValores("XOX0X0XX0");
        assertThat(tablero.checkDraw(), is(false));
    }

    private void asignarValores(String letras) {
        for (int i = 0; i < letras.length(); i++) {
            tablero.getCell(i).value = Character.toString(letras.charAt(i));
        }
    }
}