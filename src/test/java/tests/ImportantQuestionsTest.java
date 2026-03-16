package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobjects.MainPage;

import java.time.Duration;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("faqData")
    public void checkFAQ(int index, String expectedText) {

        mainPage.acceptCookies();
        mainPage.scrollToFAQ();
        mainPage.clickQuestion(index);

        WebElement answer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("accordion__panel-" + index)));

        Assertions.assertTrue(answer.isDisplayed(), "Ответ на вопрос не отображается");

        Assertions.assertEquals(expectedText, answer.getText().trim(),
                "Текст ответа не соответствует ожидаемому");
    }

    static Stream<Arguments> faqData() {
        return Stream.of(
                Arguments.of(0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of(1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of(2, "Дополнительно можно заказать чехол для самоката."),
                Arguments.of(3, "Можно продлить заказ, если не планируете возвращать самокат вовремя."),
                Arguments.of(4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of(5, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of(6, "Да, обязательно. Всем самокатов! И Москве, и Московской области."),
                Arguments.of(7, "Да, можно. Если вдруг самокат сломается, мы починим его или заменим.")
        );
    }
}