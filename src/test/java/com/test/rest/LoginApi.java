package com.test.rest;

import com.google.common.io.BaseEncoding;
import com.test.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class LoginApi extends BaseTest {

    private static final String PATH = "/login";
    private final String auth_token;

    public LoginApi(String username, String password) {
        String password1 = getEncodedPasswordToBase64(password);

        String baseServerUrl = prop.getProperty("base.api.url");
        String jsonBody = "{\"username\":\"" + username + "\",\"password\":\"" + password1 + "\"}";

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(baseServerUrl + PATH)
                .then()
                .assertThat()
                .statusCode(200);

        this.auth_token = response.extract().body().asPrettyString().split(": ")[1].replace("\"", "");
    }

    public static LoginApi withCredentials(String username, String password) {
        return new LoginApi(username, password);
    }

    public String getAuth_token() {
        return this.auth_token;
    }

    private String getEncodedPasswordToBase64(String password) {
        return BaseEncoding.base64()
                .encode(password.getBytes());
    }
}