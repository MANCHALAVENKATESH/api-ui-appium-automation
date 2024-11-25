package org.example.api;

import io.restassured.response.Response;
import org.example.pages.api.ApiTestMethods;
import org.example.utils.ConfigReader;
import com.aventstack.extentreports.ExtentTest;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TopRatedApiTest extends ApiTestMethods {

    private ExtentTest test;

    private void validateResponseStatus(Response response, int expectedStatusCode, ExtentTest test) {
        assertEquals("Expected status code does not match!", expectedStatusCode, response.getStatusCode());
        if (response.getStatusCode() == expectedStatusCode) {
            test.pass("Response status code is " + expectedStatusCode);
        } else {
            test.fail("Expected " + expectedStatusCode + ", but got: " + response.getStatusCode());
        }
    }

    private void validateResponseMessage(Response response, String expectedMessage, ExtentTest test) {
        String actualMessage = response.jsonPath().getString("status_message");
        if (actualMessage.contains(expectedMessage)) {
            test.pass("Response message matches: " + expectedMessage);
        } else {
            test.fail("Expected message: " + expectedMessage + ", but got: " + actualMessage);
        }
    }

    @Test
    public void allMoviesShouldReturnStatus200() {
        test = extent.createTest("All Movies Should Return Status 200");

        String endpoint = ConfigReader.getEndpoint("top_rated_endpoint") + "?language=en-US&page=1";
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        validateResponseStatus(response, 200, test);
        if (response.getStatusCode() == 200 && response.getBody().asString().contains("results")) {
            test.pass("Response contains a non-empty 'results' array.");
        } else {
            test.fail("Response does not contain 'results' or it's empty.");
        }
    }

    @Test
    public void shouldReturn401ForUnauthorizedToken() {
        test = extent.createTest("Should Return 401 for Unauthorized Token");

        String endpoint = ConfigReader.getEndpoint("top_rated_endpoint") + "?language=en-US&page=1";
        Response response = sendGetRequest(endpoint, "InvalidToken");

        validateResponseStatus(response, 401, test);
    }

    @Test
    public void shouldReturn404ForPostRequestToInvalidEndpoint() {
        test = extent.createTest("Should Return 404 for POST Request to Invalid Endpoint");

        String endpoint = ConfigReader.getEndpoint("invalid_post_endpoint");
        Response response = sendPostRequest(endpoint, ConfigReader.getAuthToken());

        validateResponseStatus(response, 404, test);
    }

    @Test
    public void invalidPageNumberNegative() {
        test = extent.createTest("Invalid Page Number (Negative)");

        String endpoint = ConfigReader.getEndpoint("now_playing_endpoint") + "?language=en-US&page=-1";
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        validateResponseStatus(response, 400, test);
        validateResponseMessage(response, "Invalid page", test);
    }

    @Test
    public void invalidPageNumberZeroOrGreaterThan500() {
        test = extent.createTest("Invalid Page Number (Zero or Greater than 500)");

        String endpoint = ConfigReader.getEndpoint("now_playing_endpoint") + "?language=en-US&page=0";
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        validateResponseStatus(response, 400, test);
        validateResponseMessage(response, "Invalid page", test);
    }

    @Test
    public void passingWrongAPI() {
        test = extent.createTest("Passing Wrong API");

        String endpoint = ConfigReader.getEndpoint("wrong_api_endpoint");
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        validateResponseStatus(response, 404, test);
        validateResponseMessage(response, "Invalid id", test);
    }

    @Test
    public void shouldEnsureNoDuplicateMoviesInTheResults() {
        test = extent.createTest("Should Ensure No Duplicate Movies in the Results");

        String endpoint = ConfigReader.getEndpoint("now_playing_endpoint") + "?language=en-US&page=1";
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        validateResponseStatus(response, 200, test);

        if (response.getStatusCode() == 200) {
            List<Integer> movieIds = response.jsonPath().getList("results.id");
            HashSet<Integer> uniqueMovieIds = new HashSet<>(movieIds);

            if (movieIds.size() == uniqueMovieIds.size()) {
                test.pass("No duplicate movies found in the response.");
            } else {
                test.fail("Duplicate movies found in the response.");
            }
        }
    }
}
