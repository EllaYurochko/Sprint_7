package ru.yandex.praktikum.tests;
import io.qameta.allure.junit4.DisplayName;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.client.OrderClient;
import ru.yandex.praktikum.pojo.OrderRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @DisplayName("Получение списка заказов")
    @Test
    public void orderListNotBeEmpty() {
        OrderRequest orderRequest = new OrderRequest();
        orderClient.getOrderList(orderRequest).assertThat().statusCode(SC_OK).and().body("orders", notNullValue());
    }
}