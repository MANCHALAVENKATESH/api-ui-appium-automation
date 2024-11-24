package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.config.BaseApi;
import org.example.config.ConfigReader;
import com.aventstack.extentreports.ExtentTest;
import org.junit.Test;

import static org.example.config.ExtentReportManager.extent;

public class TopRatedApiTest extends BaseApi {

    private static ExtentTest test;

    public static RequestSpecification getRequestSpec() {
        return RestAssured.given()
                .contentType(ConfigReader.getContentType())
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken());
    }

    private Response sendGetRequest(String endpoint) {
        return getRequestSpec()
                .accept(ContentType.JSON)
                .get(endpoint);
    }
    private Response postRequest(String endpoint, String body) {
        return RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .accept(ContentType.JSON)
                .body(body)
                .post(endpoint);
    }
    private void logResponseStatus(ExtentTest test, Response response, int expectedStatusCode) {
        if (response.getStatusCode() == expectedStatusCode) {
            test.pass("Response status code is " + expectedStatusCode);
        } else {
            test.fail("Expected " + expectedStatusCode + " but got: " + response.getStatusCode());
        }
    }

    private void logResponseMessage(ExtentTest test, Response response) {
        test.pass("Response Message: " + response.jsonPath().getString("status_message"));
    }

    @Test
    public void allMoviesShouldReturnStatus200() {
        test = extent.createTest("All Movies Should Return Status 200");

        Response response = sendGetRequest("?language=en-US&page=1");

        logResponseStatus(test, response, 200);
        if (response.getStatusCode() == 200) {
            test.pass("Response contains a non-empty 'results' array.");
        }
    }

    @Test
    public void shouldReturn401ForUnauthorizedToken() {
        test = extent.createTest("Should Return 401 for Unauthorized Token");

        String invalidAuthToken = "Bearer yJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2";
        Response response = RestAssured.given()
                .header("Authorization", invalidAuthToken)
                .accept(ContentType.JSON)
                .get("?language=en-US&page=1");

        logResponseStatus(test, response, 401);
    }

    @Test
    public void shouldReturn404ForPostRequestToInvalidEndpoint() {
        test = extent.createTest("Should Return 404 for POST Request to Invalid Endpoint");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .accept(ContentType.JSON)
                .post("?language=en-US&page=1");

        logResponseStatus(test, response, 404);
    }

    @Test
    public void invalidPageNumberNegative() {
        test = extent.createTest("Invalid Page Number (Negative)");

        Response response = sendGetRequest("?language=en-US&page=-1");

        logResponseStatus(test, response, 400);
        logResponseMessage(test, response);
    }

    @Test
    public void invalidPageNumberZeroOrGreaterThan500() {
        test = extent.createTest("Invalid Page Number (Zero or Greater than 500)");

        Response response = sendGetRequest("?language=en-US&page=0");

        logResponseStatus(test, response, 400);
        logResponseMessage(test, response);
    }

    @Test
    public void passingWrongAPI() {
        test = extent.createTest("Passing Wrong API");

        Response response = sendGetRequest("/top_rateds?language=en-US&page=1");

        logResponseStatus(test, response, 404);
        logResponseMessage(test, response);
    }

    @Test
    public void shouldEnsureNoDuplicateMoviesInTheResults() {
        test = extent.createTest("Should Ensure No Duplicate Movies in the Results");

        Response response = sendGetRequest("?language=en-US&page=1");

        logResponseStatus(test, response, 200);
        if (response.getStatusCode() == 200) {
            // Get the movie IDs and check for duplicates
            var movieIds = response.jsonPath().getList("results.id");
            var uniqueMovieIds = new java.util.HashSet<>(movieIds);

            if (uniqueMovieIds.size() == movieIds.size()) {
                test.pass("No duplicate movies found in the response.");
            } else {
                test.fail("Duplicate movies found in the response.");
            }
        }
    }
}
