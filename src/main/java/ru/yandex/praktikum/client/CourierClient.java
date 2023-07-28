package ru.yandex.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.yandex.praktikum.pojo.CourierRequest;
import ru.yandex.praktikum.pojo.LoginRequest;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {
    private static final String COURIER = "courier";
    private static final String COURIER_LOGIN = "courier/login";
    private static final String COURIER_DELETE = "courier/{id}";

    @Step("Создаем курьера")
    public ValidatableResponse create(CourierRequest courierRequest) {
        return given().spec(getDefaultRequestSpec()).body(courierRequest).post(COURIER).then();
    }

    @Step("Авторизуем курьера")
    public ValidatableResponse login(LoginRequest loginRequest) {
        return given().spec(getDefaultRequestSpec()).body(loginRequest).post(COURIER_LOGIN).then();
    }

    @Step("Удаляем курьера")
    public ValidatableResponse delete(int id) {
        return given().spec(getDefaultRequestSpec()).delete(COURIER_DELETE, id).then();
    }
}