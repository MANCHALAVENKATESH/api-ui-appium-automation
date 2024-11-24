package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.config.BaseApi;
import org.example.config.ConfigReader;
import org.junit.Test;


import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NowPlayingApiTest extends BaseApi {

    @Test
    public void testAllMoviesShouldReturnStatus200() {
        test = extent.createTest("testAllMoviesShouldReturnStatus200");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get("top_rated?language=en-US&page=1");

        // Validate status and structure
        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("results"));
        test.pass("Status code 200 and results found in the response body.");
    }

    @Test
    public void testUnauthorizedTokenShouldReturnStatus401() {
        test = extent.createTest("testUnauthorizedTokenShouldReturnStatus401");

        String invalidToken = "Bearer yJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2";

        Response response = RestAssured.given()
                .header("Authorization", invalidToken)
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get("now_playing?language=en-US&page=1");

        assertEquals(401, response.getStatusCode());
        assertEquals("Unauthorized", response.getStatusLine().split(" ")[1]);
        test.pass("Status code 401 and Unauthorized message found.");
    }

    @Test
    public void testPostRequestToInvalidEndpointShouldReturnStatus404() {
        test = extent.createTest("testPostRequestToInvalidEndpointShouldReturnStatus404");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .header("Accept", ConfigReader.getContentType())
                .when()
                .post("now_playing?language=en-US&page=1");

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("The resource you requested could not be found."));
        test.pass("Status code 404 and resource not found message.");
    }

    @Test
    public void testInvalidPageNumberNegativeShouldReturn400() {
        test = extent.createTest("testInvalidPageNumberNegativeShouldReturn400");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get("now_playing?language=en-US&page=-1");

        assertEquals(400, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("Invalid page: Pages start at 1 and max at 500. They are expected to be an integer."));
        test.pass("Status code 400 and invalid page message.");
    }

    @Test
    public void testInvalidPageNumberZeroShouldReturn400() {
        test = extent.createTest("testInvalidPageNumberZeroShouldReturn400");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get("now_playing?language=en-US&page=0");

        assertEquals(400, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("Invalid page: Pages start at 1 and max at 500. They are expected to be an integer."));
        test.pass("Status code 400 and invalid page message.");
    }

    @Test
    public void testInvalidPageNumberGreaterThan500ShouldReturn400() {
        test = extent.createTest("testInvalidPageNumberGreaterThan500ShouldReturn400");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get("now_playing?language=en-US&page=501");

        assertEquals(400, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("Invalid page: Pages start at 1 and max at 500. They are expected to be an integer."));
        test.pass("Status code 400 and invalid page message.");
    }

    @Test
    public void testWrongApiEndpointShouldReturn404() {
        test = extent.createTest("testWrongApiEndpointShouldReturn404");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get("movie/now_playings?language=en-US&page=1");

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("Invalid id: The pre-requisite id is invalid or not found."));
        test.pass("Status code 404 and invalid id message.");
    }

    @Test
    public void testNoDuplicateMoviesInResults() {
        test = extent.createTest("testNoDuplicateMoviesInResults");

        Response response = RestAssured.given()
                .header("Authorization", "Bearer" + " " + ConfigReader.getAuthToken())
                .header("Accept", ConfigReader.getContentType())
                .when()
                .get("now_playing?language=en-US&page=1");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("results"));

        // Extract movie IDs and check for duplicates
        List<Integer> movieIds = response.jsonPath().getList("results.id");
        Set<Integer> uniqueMovieIds = new HashSet<>(movieIds);

        assertEquals(movieIds.size(), uniqueMovieIds.size());
        test.pass("No duplicate movies in the results.");
    }

}
