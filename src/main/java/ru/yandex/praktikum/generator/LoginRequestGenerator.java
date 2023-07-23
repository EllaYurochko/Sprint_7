package ru.yandex.praktikum.generator;
import ru.yandex.praktikum.pojo.CourierRequest;
import ru.yandex.praktikum.pojo.LoginRequest;

public class LoginRequestGenerator {
    public static LoginRequest getLoginRequest(CourierRequest courierRequest) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(courierRequest.getLogin());
        loginRequest.setPassword(courierRequest.getPassword());
        return loginRequest;
    }
}