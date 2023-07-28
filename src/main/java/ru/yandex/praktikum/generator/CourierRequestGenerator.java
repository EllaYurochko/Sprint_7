package ru.yandex.praktikum.generator;

import org.apache.commons.lang3.RandomStringUtils;
import ru.yandex.praktikum.pojo.CourierRequest;

public class CourierRequestGenerator {
    public static CourierRequest getRandomCourierRequest() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword("7654");
        courierRequest.setFirstName("Buratino");
        return courierRequest;
    }

    public static CourierRequest getCourierRequestWithoutLogin() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin("");
        courierRequest.setPassword("7654");
        courierRequest.setFirstName("Buratino");
        return courierRequest;
    }

    public static CourierRequest getCourierRequestWithoutPassword() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword("");
        courierRequest.setFirstName("Buratino");
        return courierRequest;
    }

    public static CourierRequest getCourierRequestWithoutFirstName() {
        CourierRequest courierRequest = new CourierRequest();
        courierRequest.setLogin(RandomStringUtils.randomAlphabetic(10));
        courierRequest.setPassword("7654");
        return courierRequest;
    }
}