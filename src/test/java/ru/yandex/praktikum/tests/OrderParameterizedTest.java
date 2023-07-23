package ru.yandex.praktikum.tests;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.pojo.OrderRequest;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.generator.OrderRequestGenerator.getCreateOrderRequest;


@RunWith(Parameterized.class)
public class OrderParameterizedTest {
    private OrderClient orderClient;
    private String[] color;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    public OrderParameterizedTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{{new String[]{"BLACK"}}, {new String[]{"GRAY"}}, {new String[]{"BLACK", "GRAY"}}, {new String[]{""}}};
    }

    @DisplayName("Создание заказа с выбором цветов и без")
    @Test
    public void createOrdersTest() {
        OrderRequest orderRequest = getCreateOrderRequest(color);
        orderClient.createOrder(orderRequest).assertThat().statusCode(SC_CREATED).and().body("track", notNullValue());
    }
}