package org.example.pages.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.setup.ApiTestBase;
import org.example.utils.ConfigReader;

public class ApiTestMethods extends ApiTestBase {

    public static Response sendGetRequest(String endpoint, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get(endpoint);
    }

    public static Response sendPostRequest(String endpoint, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", ConfigReader.getContentType())
                .when()
                .post(endpoint);
    }
}
