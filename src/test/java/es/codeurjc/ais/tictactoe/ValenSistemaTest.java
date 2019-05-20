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

    private WebDriver driverLei;
    private WebDriver driverValen;

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
        driverLei = new FirefoxDriver();
        driverValen = new FirefoxDriver();
    }

    @After
    public void teardown() {
        if (driverLei != null) {
            driverLei.quit();
        }
        if (driverValen != null) {
            driverValen.quit();
        }
    }

    @Test
    public void winTest() throws InterruptedException {
        String url = "http://127.0.0.1:8080";
        driverLei.get(url);
        driverValen.get(url);

        String player1 = "Lei";
        String player2 = "Valen";

        String nickname = "nickname";
        String startBtn = "startBtn";

        driverLei.findElement(By.id(nickname)).sendKeys(player1);
        driverLei.findElement(By.id(startBtn)).click();
        driverValen.findElement(By.id(nickname)).sendKeys(player2);
        driverValen.findElement(By.id(startBtn)).click();

        /* X O X
           O X O
           X
         */

        String cell_x = "cell-";
        for (int i = 0; i < 7; i++) {
            if (i % 2 == 0) {
                driverLei.findElement(By.id(cell_x + i)).click();
            } else {
                driverValen.findElement(By.id(cell_x + i)).click();
            }
        }

        String winMessage = "Lei wins! Valen looses.";

        String leiMessage = driverLei.switchTo().alert().getText();
        String valenMessage = driverValen.switchTo().alert().getText();

        assertThat(winMessage).isEqualTo(leiMessage);
        assertThat(winMessage).isEqualTo(valenMessage);

        Thread.sleep(5000);
    }

}
