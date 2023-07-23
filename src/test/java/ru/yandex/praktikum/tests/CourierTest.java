package ru.yandex.praktikum.tests;
import io.qameta.allure.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.yandex.praktikum.client.CourierClient;
import ru.yandex.praktikum.pojo.CourierRequest;
import ru.yandex.praktikum.pojo.LoginRequest;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.praktikum.generator.CourierRequestGenerator.*;
import static ru.yandex.praktikum.generator.LoginRequestGenerator.getLoginRequest;

public class CourierTest {
    private CourierClient courierClient;
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(id).assertThat().statusCode(SC_OK).and().body("ok", equalTo(true));
        }
    }

    @Test
    @DisplayName("Создание курьера, получение id")
    public void courierShouldBeCreatedTest() {
        CourierRequest randomCourierRequest = getCourierRequestWithoutFirstName();
        courierClient.create(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));

        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);

        id = courierClient.login(loginRequest).assertThat().statusCode(SC_OK).and().body("id", notNullValue()).extract().path("id");
    }

    @DisplayName("Создание курьера c повторным логином")
    @Test
    public void courierShouldNotBeCreatedWithNotUniqueLoginTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));
        courierClient.create(randomCourierRequest).assertThat().statusCode(SC_CONFLICT).and().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Создание курьера с пустым login")
    @Test
    public void courierShouldNotBeCreatedWithLoginIsNullTest() {

        CourierRequest courierRequest = getCourierRequestWithoutLogin();
        courierClient.create(courierRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @DisplayName("Создание курьера с пустым password")
    @Test
    public void courierShouldNotBeCreatedWithPasswordIsNullTest() {

        CourierRequest courierRequest = getCourierRequestWithoutPassword();
        courierClient.create(courierRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым firstName")
    public void courierShouldBeCreatedWithFirstNameIsNullTest() {
        CourierRequest randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));

        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        id = courierClient.login(loginRequest).assertThat().statusCode(SC_OK).and().body("id", notNullValue()).extract().path("id");
    }
}