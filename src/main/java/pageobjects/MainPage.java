package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Cookie кнопка
    private By cookieButton = By.id("rcc-confirm-button");

    // FAQ
    private By question1 = By.id("accordion__heading-0");
    private By answer1 = By.id("accordion__panel-0");

    // Кнопки заказа
    private By orderButtonTop = By.xpath("//button[text()='Заказать']");
    private By orderButtonBottom = By.xpath("(//button[text()='Заказать'])[2]");

    public void acceptCookies() {
        driver.findElement(cookieButton).click();
    }

    public void scrollToFAQ() {
        ((JavascriptExecutor) driver)
                .executeScript("document.getElementById('accordion__heading-0').scrollIntoView();");
    }

    public void clickQuestion(int index) {
        By question = By.id("accordion__heading-" + index);

        ((JavascriptExecutor) driver)
                .executeScript("document.getElementById('accordion__heading-" + index + "').scrollIntoView();");

        driver.findElement(question).click();
    }

    public String getAnswer(int index) {
        By answer = By.id("accordion__panel-" + index);
        return driver.findElement(answer).getText();
    }

    public void clickOrderTop() {
        driver.findElement(orderButtonTop).click();
    }

    public void clickOrderBottom() {
        ((JavascriptExecutor) driver)
                .executeScript("window.scrollTo(0, document.body.scrollHeight)");
        driver.findElement(orderButtonBottom).click();
    }
}