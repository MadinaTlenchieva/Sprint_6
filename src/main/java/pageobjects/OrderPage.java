package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Заполнение первой формы заказа
    public void fillFirstForm(String name, String surname, String address,
                              String metroStation, String phone) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//input[@placeholder='* Имя']")))
                .sendKeys(name);

        driver.findElement(By.xpath("//input[@placeholder='* Фамилия']"))
                .sendKeys(surname);

        driver.findElement(By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']"))
                .sendKeys(address);

        // ====== ВЫБОР СТАНЦИИ МЕТРО (исправлено) ======
        WebElement metroInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='* Станция метро']")));

        metroInput.click();
        metroInput.sendKeys(metroStation);

        // ждём появления выпадающего списка
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'select-search__select')]")));

        // более гибкий локатор
        WebElement station = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'select-search__row')]//span[contains(text(),'" + metroStation + "')]")));

        // кликаем через JS (устраняет проблемы с перекрытием)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", station);
        // ==============================================

        driver.findElement(By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']"))
                .sendKeys(phone);

        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[text()='Далее']")))
                .click();
    }

    // Заполнение второй формы заказа
    public void fillSecondForm(String date, String rentTime, String scooterColor,
                               String comment) {

        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='* Когда привезти самокат']")));
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);

        // выбор срока аренды
        WebElement rentDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'Dropdown-control')]")));
        rentDropdown.click();

        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@class='Dropdown-option' and text()='" + rentTime + "']")))
                .click();

        // выбор цвета самоката
        if (scooterColor.equalsIgnoreCase("black")) {
            driver.findElement(By.id("black")).click();
        } else if (scooterColor.equalsIgnoreCase("grey")) {
            driver.findElement(By.id("grey")).click();
        }

        driver.findElement(By.xpath("//input[@placeholder='Комментарий для курьера']"))
                .sendKeys(comment);

        driver.findElement(By.xpath("//button[text()='Заказать']"))
                .click();

        // Подтверждение заказа
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[text()='Да']"))).click();
    }

    // Методы для FAQ
    public void clickAccordionItem(String question) {
        WebElement questionDiv = driver.findElement(
                By.xpath("//div[contains(text(),'" + question + "')]"));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", questionDiv);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", questionDiv);
    }

    public boolean isAnswerVisible(String answerText) {
        try {
            WebElement answerDiv = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//div[contains(text(),'" + answerText + "')]")));
            return answerDiv.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}