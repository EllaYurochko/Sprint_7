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
import static ru.yandex.praktikum.generator.CourierRequestGenerator.getRandomCourierRequest;
import static ru.yandex.praktikum.generator.LoginRequestGenerator.getLoginRequest;

public class CourierLoginTest {
    private CourierClient courierClient;
    private CourierRequest randomCourierRequest;
    private Integer id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        randomCourierRequest = getRandomCourierRequest();
        courierClient.create(randomCourierRequest).assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        if (id != null) {
            courierClient.delete(id).assertThat().statusCode(SC_OK).and().body("ok", equalTo(true));
        }
    }

    @DisplayName("Авторизация курьера с корректными параметрами")
    @Test
    public void courierShouldBeAuthorizedTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        id = courierClient.login(loginRequest).assertThat().statusCode(SC_OK).and().body("id", notNullValue()).extract().path("id");
    }

    @DisplayName("Авторизация курьера с пустым login")
    @Test
    public void courierShouldNotBeAuthorizedWithLoginIsEmptyTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setLogin("");
        courierClient.login(loginRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Авторизация курьера с пустым password")
    @Test
    public void courierShouldNotBeAuthorizedWithPasswordIsEmptyTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setPassword("");
        courierClient.login(loginRequest).assertThat().statusCode(SC_BAD_REQUEST).and().body("message", equalTo("Недостаточно данных для входа"));
    }

    @DisplayName("Авторизация курьера с неверным login")
    @Test
    public void courierShouldNotBeAuthorizedWithLoginIsNotCorrectTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setLogin("umka");
        courierClient.login(loginRequest).assertThat().statusCode(SC_NOT_FOUND).and().body("message", equalTo("Учетная запись не найдена"));
    }

    @DisplayName("Авторизация курьера c неверным password")
    @Test
    public void courierShouldNotBeAuthorizedWithPasswordIsNotCorrectTest() {
        LoginRequest loginRequest = getLoginRequest(randomCourierRequest);
        loginRequest.setPassword("000");
        courierClient.login(loginRequest).assertThat().statusCode(SC_NOT_FOUND).and().body("message", equalTo("Учетная запись не найдена"));
    }
}