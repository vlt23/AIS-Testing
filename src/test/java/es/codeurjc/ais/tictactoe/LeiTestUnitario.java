package es.codeurjc.ais.tictactoe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
		tablero=new Board();
		tablero.checkDraw();
	}
	@After
	public void close() {
		
	}
	@Test
	public void drawTest() {
		//given
		/* X 0 X
		 * 0 X X
		 * 0 X 0
		 * */
		tablero.getCell(0).value="X";tablero.getCell(1).value="0";tablero.getCell(2).value="X";
				
		tablero.getCell(3).value="0";tablero.getCell(4).value="X";tablero.getCell(5).value="X";
		
		tablero.getCell(6).value="0";tablero.getCell(7).value="X";tablero.getCell(8).value="0";
		//when
		boolean draw=tablero.checkDraw();
		//then
		assertThat(tablero.checkDraw(),equals(true));
	}
}