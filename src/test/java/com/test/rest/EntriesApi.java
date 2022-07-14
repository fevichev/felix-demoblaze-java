package com.test.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.base.BaseTest;
import io.restassured.response.ValidatableResponse;
import com.fasterxml.jackson.databind.*;

import static io.restassured.RestAssured.given;

public class EntriesApi extends BaseTest {

    private String baseServerUrl = prop.getProperty("base.api.url");
    private static final String PATH = "/entries";
    private final String jsonBody;

    public EntriesApi() {

        ValidatableResponse response =  given()
                .when()
                .get(baseServerUrl + PATH)
                .then()
                .log()
                .body()
                .statusCode(200);

//        this.jsonBody = response.then().extract().body().asPrettyString();
        this.jsonBody = response.extract().body().jsonPath().getJsonObject("Items").toString();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
//            String x = response.extract().body().jsonPath().get("Items").toString();
            String x = response.extract().body().asPrettyString();
            System.out.println(objectMapper.readTree(x).findPath("Items").findValues("title"));
            JsonNode jsonTree = objectMapper.readTree(x).findPath("Items");

//            List<EntriesApi> myObjects = Arrays.asList(objectMapper.readValue(jsonTree.toString(), EntriesApi[].class));
//            for (EntriesApi el : myObjects) {
//                System.out.println(el.toString());
////                System.out.printf("%s %s %s%n", el.getName(), el.getStartDate(), el.getId());
//            }

            System.out.println(jsonTree.findValue("Nexus 6").get("price"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//
//        try {
//            JsonNode jsonTree = objectMapper.readTree(this.jsonBody);
//            System.out.println(jsonTree.findValue("Nexus 6").get("price"));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static EntriesApi and() {
        return new EntriesApi();
    }

    public String getBody() {
        return this.jsonBody;
    }
}
