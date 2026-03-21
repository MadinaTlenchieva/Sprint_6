package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Куки
    private final By COOKIE_BUTTON = By.id("rcc-confirm-button");

    // Кнопки заказа
    private final By ORDER_TOP = By.xpath("//div[contains(@class,'Header')]//button[text()='Заказать']");
    private final By ORDER_BOTTOM = By.xpath("//div[contains(@class,'Home_FinishButton')]//button");

    private final By FAQ_SECTION = By.id("accordion__heading-0");

    public void acceptCookies() {
        wait.until(ExpectedConditions.elementToBeClickable(COOKIE_BUTTON)).click();
    }

    public void clickOrderTop() {
        wait.until(ExpectedConditions.elementToBeClickable(ORDER_TOP)).click();
    }

    public void clickOrderBottom() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(ORDER_BOTTOM));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public void scrollToFAQ() {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(FAQ_SECTION));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public void clickQuestion(int index) {
        By question = By.id("accordion__heading-" + index);
        wait.until(ExpectedConditions.elementToBeClickable(question)).click();
    }
}