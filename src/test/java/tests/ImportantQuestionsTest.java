package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.MainPage;

import java.time.Duration;

public class ImportantQuestionsTest extends BaseTest {

    private MainPage mainPage;
    private WebDriverWait wait;

    @BeforeEach
    public void setUpTest() {
        startDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void checkFAQ() {
        mainPage.acceptCookies();
        mainPage.scrollToFAQ();
        mainPage.clickQuestion1();

        WebElement answer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("accordion__panel-0")));

        Assertions.assertTrue(answer.isDisplayed(), "Ответ на вопрос не отображается");
        Assertions.assertTrue(answer.getText().contains("Стоимость рассчитывается исходя из"),
                "Текст ответа не соответствует ожидаемому");
    }
}