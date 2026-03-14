package tests;

import org.junit.jupiter.api.*;
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

        // Нажать первичную кнопку "Заказать" вверху страницы
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Заказать']"))).click();

        orderPage = new OrderPage(driver);
    }

    @Test
    public void createOrder() {

        // Заполнение первой формы, включая метро
        orderPage.fillFirstForm(
                "Иван",
                "Иванов",
                "Москва, ул. Тестовая, 1",
                "Черкизовская",
                "+79991234567"
        );

        // Заполнение второй формы: дата, срок, цвет, комментарий
        orderPage.fillSecondForm(
                "15.04.2026",
                "двое суток",
                "black",
                "Тестовый комментарий"
        );

        // Проверка, что появилось всплывающее окно подтверждения заказа
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