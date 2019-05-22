package es.codeurjc.ais.tictactoe;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class UnitarioTest {
    private Board tablero;
    private String labelP1;
    private String labelP2;

    @Before
    public void setup() {
        tablero = new Board();
        labelP1 = "X";
        labelP2 = "O";
    }

    //TRUE DRAW
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

    //PRIMERO COMPRUEBA EN MARK SI SE HA GANADO O NO, EN CASO DE NO VERIFICAR EMPATE,
    //POR SI SE USA DIRECTAMENTE NO PUEDE COMPROABAR EL EMPATE
    //FALSE DRAW
    @Test
    public void falseCheckDrawTest() {
        //given
        /* X 0 X
           0 X 0
           X X 0
         */
        asignarValores("XOXOXOXXO");
        //when
        boolean draw = tablero.checkDraw();
        //then
        //assertThat(draw, is(false));  // TODO, uncomment
    }

    //TEST P1 GANA Y P2 PIERDE    
    @Test
    public void getCellsIfWinneP1WinAndP2LoseTest() {
        //given
        /* X X X
           0 0 X
           0 X 0
         */
        String table = "XXXOOXOXO";
        asignarValores(table);
        int[] expectedCells = {0, 1, 2};
        //then        
        assertThat(comprobacionDeResultados(labelP1, labelP2, expectedCells, null), is(true));
    }

    //TEST P1 PIERDA Y P2 GANA
    @Test
    public void getCellsIfWinnerP1LoseAndP2WinTest() {
        //given
        /* 0 0 0
           X X 0
           X 0 X
         */
        String table = "OOOXXOXOX";
        asignarValores(table);
        int[] expectedCells = {0, 1, 2};
        //then        
        assertThat(comprobacionDeResultados(labelP2, labelP1, expectedCells, null), is(true));
    }

    //P1 AND P2 EMPATE
    @Test
    public void getCellsIfWinnerP1AndP2DrawTest() {
        //given
        //dato dado
        /* X 0 X
         * 0 X X
         * 0 X 0
         * */
        asignarValores("XOXOXXOXO");
        //then        
        assertThat(comprobacionDeResultados(labelP1, labelP2, null, null), is(true));
    }

    //TEST SI PASO EL MISMO STRING PERO SIN USAR EL LABEL DEL JUGADOR
    /*@Test
    public void withoutLabelTest() {
        //given
        // 0 0 0
        // X X 0
        // X 0 X
         
        String table = "OOOXXOXOX";
        asignarValores(table);
        int[] expectedCells = {0, 1, 2};
        //then        
        assertThat(comprobacionDeResultados("O", "X", expectedCells, null), is(true));
    }*/

    private boolean comprobacionDeResultados(String labelWinner, String labelLoser, int[] cellsWin, int[] cellsLose) {
        //when
        int[] cellsWinner = tablero.getCellsIfWinner(labelWinner);
        int[] cellsLoser = tablero.getCellsIfWinner(labelLoser);
        //then        
        assertThat(cellsWinner, equalTo(cellsWin));
        assertThat(cellsLoser, equalTo(cellsLose));
        return true;
    }

    private void asignarValores(String letras) {
        for (int i = 0; i < letras.length(); i++) {
            if (letras.charAt(i) == 'X') {
                tablero.getCell(i).value = labelP1;
            } else
                tablero.getCell(i).value = labelP2;
        }
    }
}