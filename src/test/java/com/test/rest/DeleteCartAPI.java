package com.test.rest;

import com.test.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class DeleteCartAPI extends BaseTest {

    private static final String PATH = "/deletecart";

    public DeleteCartAPI(String username) {

        String baseServerUrl = prop.getProperty("base.api.url");
        String jsonBody = "{\"cookie\": \"" + username + "\"}";

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(baseServerUrl + PATH)
                .then()
                .assertThat()
                .statusCode(200);
    }

    public static DeleteCartAPI withCredentials(String username) {
        return new DeleteCartAPI(username);
    }
}
