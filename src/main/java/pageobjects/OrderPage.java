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

    // ===== ЛОКАТОРЫ =====

    // Модальное окно
    private final By MODAL = By.xpath("//div[contains(@class,'Order_Modal')]");

    // Заголовок модалки
    private final By MODAL_HEADER = By.xpath("//div[contains(@class,'Order_ModalHeader')]");

    // Кнопка "Да"
    private final By YES_BUTTON = By.xpath("//button[normalize-space()='Да']");

    // Кнопка "Нет"
    private final By NO_BUTTON = By.xpath("//button[normalize-space()='Нет']");

    // Модалка успешного заказа
    private final By SUCCESS_MODAL = By.xpath("//div[contains(@class,'Order_Overlay')]");


    // ===== Первая форма =====
    public void fillFirstForm(String name, String surname, String address,
                              String metroStation, String phone) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='* Имя']"))).sendKeys(name);

        driver.findElement(By.xpath("//input[@placeholder='* Фамилия']"))
                .sendKeys(surname);

        driver.findElement(By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']"))
                .sendKeys(address);

        WebElement metroInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='* Станция метро']")));

        metroInput.click();
        metroInput.sendKeys(metroStation);
        metroInput.sendKeys(Keys.ARROW_DOWN);
        metroInput.sendKeys(Keys.ENTER);

        driver.findElement(By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']"))
                .sendKeys(phone);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Далее']"))).click();
    }


    // ===== Вторая форма =====
    public void fillSecondForm(String date, String rentTime, String scooterColor,
                               String comment) {

        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@placeholder='* Когда привезти самокат']")));
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);

        WebElement rentDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'Dropdown-control')]")));
        rentDropdown.click();

        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[contains(@class,'Dropdown-option') and text()='" + rentTime + "']")))
                .click();

        if (scooterColor.equalsIgnoreCase("black")) {
            driver.findElement(By.id("black")).click();
        } else if (scooterColor.equalsIgnoreCase("grey")) {
            driver.findElement(By.id("grey")).click();
        }

        driver.findElement(By.xpath("//input[@placeholder='Комментарий для курьера']"))
                .sendKeys(comment);
    }


    // ===== КНОПКА "ЗАКАЗАТЬ" =====
    public void clickOrderButton() {
        WebElement orderBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[contains(.,'Заказать')])[last()]")));
        orderBtn.click();
    }


    // ===== ПОЛУЧИТЬ ТЕКСТ МОДАЛКИ =====
    public String getConfirmModalText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL));

        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_HEADER))
                .getText();

        return text.replace("\n", "").trim();
    }


    // ===== КЛИК "ДА" =====
    public void clickConfirmYes() {

        driver.findElement(By.xpath("//button[normalize-space()='Да']")).click();
    }


    // ===== КЛИК "НЕТ" =====
//    public void clickConfirmNo() {
//        wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL));
//
//        WebElement noBtn = wait.until(ExpectedConditions.elementToBeClickable(NO_BUTTON));
//
//        try {
//            noBtn.click();
//        } catch (Exception e) {
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", noBtn);
//        }
//    }


    // ===== ТЕКСТ УСПЕШНОГО ЗАКАЗА =====
    public String getOrderSuccessText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MODAL)).getText();
    }
}