package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.OrderPage;

import java.time.Duration;

public class OrderScooterTest {

    WebDriver driver;
    OrderPage orderPage;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        orderPage = new OrderPage(driver);
    }

    @ParameterizedTest
    @ValueSource(strings = {"top", "bottom"})
    public void createOrder(String buttonPosition) {

        if (buttonPosition.equals("top")) {
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Заказать']"))).click();
        } else {
            WebElement bottomButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("(//button[text()='Заказать'])[2]")));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", bottomButton);

            bottomButton.click();
        }

        // Первая форма
        orderPage.fillFirstForm(
                "Иван",
                "Иванов",
                "Москва, ул. Тестовая, 1",
                "Черкизовская",
                "+79991234567"
        );

        // Вторая форма
        orderPage.fillSecondForm(
                "15.04.2026",
                "двое суток",
                "black",
                "Тестовый комментарий"
        );

        // Проверка попапа
        WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(),'Заказ оформлен')]")));

        Assertions.assertTrue(popup.isDisplayed(),
                "Окно подтверждения заказа не отображается");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}