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
                By.xpath("//button[text()='Далее']"))).click();
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
                        By.xpath("//div[@class='Dropdown-option' and text()='" + rentTime + "']")))
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
                By.xpath("(//button[text()='Заказать'])[last()]")));
        orderBtn.click();
    }

    // ===== ПОЛУЧИТЬ ТЕКСТ МОДАЛКИ "Хотите оформить заказ?" =====
    public String getConfirmModalText() {
        WebElement confirmModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("Order_ModalHeader__3FDaJ")));
        return confirmModal.getText();
    }

    // ===== КНОПКА "ДА" =====
    public void clickConfirmYes() {
        WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'Order_Modal')]//button[contains(@class,'Button_Button__ra12g') and text()='Да']")));
        yesBtn.click();
    }

    // ===== ПОЛУЧИТЬ ТЕКСТ УСПЕШНОГО ЗАКАЗА =====
    public String getOrderSuccessText() {
        WebElement successModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'Order_Overlay__3KW-T')]")));
        return successModal.getText();
    }
}