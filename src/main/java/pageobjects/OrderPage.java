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

    // Модалка подтверждения
    private final By MODAL = By.xpath("//div[contains(@class,'Order_Modal')]");
    private final By MODAL_HEADER = By.xpath("//div[contains(@class,'Order_ModalHeader')]");

    // Кнопки
    private final By YES_BUTTON = By.xpath("//button[normalize-space()='Да']");
    private final By ORDER_BUTTON = By.xpath("(//button[contains(.,'Заказать')])[last()]");


    private final By SUCCESS_TEXT = By.xpath("//div[contains(@class,'Order_ModalHeader')]");

    // ===== ПЕРВАЯ ФОРМА =====
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

    // ===== ВТОРАЯ ФОРМА =====
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

    //КНОПКА "ЗАКАЗАТЬ"
    public void clickOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(ORDER_BUTTON)).click();
    }

    public String getConfirmModalText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL));

        return wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_HEADER))
                .getText()
                .replace("\n", "")
                .trim();
    }

    public void clickConfirmYes() {
        wait.until(ExpectedConditions.elementToBeClickable(YES_BUTTON)).click();
    }

    //ТЕКСТ УСПЕШНОГО ЗАКАЗА
    public String getOrderSuccessText() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_TEXT));
        wait.until(ExpectedConditions.textToBePresentInElement(element, "Заказ оформлен"));
        return element.getText();
    }
}