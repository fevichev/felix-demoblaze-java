package com.test.rest;

import com.test.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ViewDeviceDescriptionApi extends BaseTest {

    private final String title;
    private final String price;
    private static final String PATH = "/view";

    public ViewDeviceDescriptionApi(int deviceId) {
        String jsonBody = "{\"id\":\"" + deviceId + "\"}";

        String baseServerUrl = prop.getProperty("base.api.url");
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(baseServerUrl + PATH)
                .then()
                .assertThat()
                .statusCode(200);

        this.title = response.extract().body().jsonPath().getString("title");
        this.price = response.extract().body().jsonPath().getString("price").split("\\.")[0];
    }

    public static ViewDeviceDescriptionApi withDeviceId(int deviceId) {
        return new ViewDeviceDescriptionApi(deviceId);
    }

    public String getTitle() {
        return this.title;
    }

    public int getPrice() {
        return Integer.parseInt(this.price);
    }
}
