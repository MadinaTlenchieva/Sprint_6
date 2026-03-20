package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import pageobjects.MainPage;
import pageobjects.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderScooterTest extends BaseTest {

    private MainPage mainPage;
    private OrderPage orderPage;

    @BeforeEach
    public void setUp() {
        startDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/order");

        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);

        // Принять куки
        mainPage.acceptCookies();
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void createOrder(String buttonPosition, String name, String surname,
                            String address, String metro, String phone,
                            String date, String rent, String color, String comment) {

        // Клик по кнопке заказа (верх/низ)
        if (buttonPosition.equals("top")) {
            mainPage.clickOrderTop();
        } else {
            mainPage.clickOrderBottom();
        }

        // Заполнение форм
        orderPage.fillFirstForm(name, surname, address, metro, phone);
        orderPage.fillSecondForm(date, rent, color, comment);

        // Клик "Заказать"
        orderPage.clickOrderButton();

        // ===== Проверка текста модалки "Хотите оформить заказ?" =====
        String confirmText = orderPage.getConfirmModalText();
        assertTrue(confirmText.contains("Хотите оформить заказ?"),
                "Текст модального окна перед подтверждением заказа не соответствует ожидаемому");

        // Подтверждение "Да"
        orderPage.clickConfirmYes();

        // ===== Проверка финального успешного заказа =====
        String successText = orderPage.getOrderSuccessText();
        assertTrue(successText.contains("Заказ оформлен"),
                "Нет подтверждения успешного заказа");
    }

    static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of("top", "Иван", "Иванов", "Москва, ул. 1",
                        "Черкизовская", "+79991111111",
                        "15.04.2026", "сутки", "black", "тест 1"),
                Arguments.of("bottom", "Петр", "Петров", "Москва, ул. 2",
                        "Сокольники", "+79992222222",
                        "16.04.2026", "двое суток", "grey", "тест 2")
        );
    }
}