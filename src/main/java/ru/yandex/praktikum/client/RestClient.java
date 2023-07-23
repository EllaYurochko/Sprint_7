package ru.yandex.praktikum.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ru.yandex.praktikum.config.Config;

public class RestClient {
    public RequestSpecification getDefaultRequestSpec() {
        return new RequestSpecBuilder().setBaseUri(Config.getBaseUrl()).setContentType(ContentType.JSON).build();
    }
}