package es.codeurjc.ais.tictactoe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class ValenSistemaTest {

    private WebDriver driver1;
    private WebDriver driver2;

    private String player1 = "Lei";
    private String player2 = "Valen";

    private String nickname = "nickname";
    private String startBtn = "startBtn";

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.firefoxdriver().setup();
        WebApp.start();
    }

    @AfterClass
    public static void teardownClass() {
        WebApp.stop();
    }

    @Before
    public void setupTest() {
        driver1 = new FirefoxDriver();
        driver2 = new FirefoxDriver();

        // Add connection
        String url = "http://127.0.0.1:8080";
        driver1.get(url);
        driver2.get(url);

        // Join game
        driver1.findElement(By.id(nickname)).sendKeys(player1);
        driver1.findElement(By.id(startBtn)).click();
        driver2.findElement(By.id(nickname)).sendKeys(player2);
        driver2.findElement(By.id(startBtn)).click();
    }

    @After
    public void teardown() {
        if (driver1 != null) {
            driver1.quit();
        }
        if (driver2 != null) {
            driver2.quit();
        }
    }

    @Test
    public void player1WinTest() throws InterruptedException {
        /* X O X
           O X O
           X
         */
        String cell_x = "cell-";
        for (int i = 0; i < 7; i++) {
            if (i % 2 == 0) {
                driver1.findElement(By.id(cell_x + i)).click();
            } else {
                driver2.findElement(By.id(cell_x + i)).click();
            }
        }

        String winMessage = player1 + " wins! " + player2 + " looses.";

        String p1Message = driver1.switchTo().alert().getText();
        String p2Message = driver2.switchTo().alert().getText();

        assertThat(winMessage).isEqualTo(p1Message);
        assertThat(winMessage).isEqualTo(p2Message);

        Thread.sleep(5000);
    }

}
