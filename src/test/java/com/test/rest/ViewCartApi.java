package com.test.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ViewCartApi extends BaseTest {

    private static final String PATH = "/viewcart";
    private final String productId;
    private final int sizeOfItemsInCart;

    public ViewCartApi(String authenticationToken) {

        String baseServerUrl = prop.getProperty("base.api.url");
        String jsonBody = "{\"cookie\": \"" + authenticationToken + "\",\"flag\": true}";

        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody)
                .when()
                .post(baseServerUrl + PATH)
                .then()
                .assertThat()
                .statusCode(200);

        ObjectMapper objectMapper = new ObjectMapper();
        String x = response.extract().body().asPrettyString();
        try {
            this.productId = objectMapper.readTree(x).findPath("Items").findValues("prod_id").toString().split("")[1];
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        this.sizeOfItemsInCart = response.extract().body().jsonPath().getList("Items").size();
    }

    public static ViewCartApi withToken(String authenticationToken) {
        return new ViewCartApi(authenticationToken);
    }

    public int getProductId() {
        return Integer.parseInt(this.productId);
    }

    public int getSizeOfItemsInCart() {
        return this.sizeOfItemsInCart;
    }
}
