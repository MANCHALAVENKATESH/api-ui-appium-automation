package org.example.api;

import io.restassured.response.Response;
import org.example.pages.api.ApiTestMethods;
import org.example.utils.ConfigReader;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NowPlayingApiTest extends ApiTestMethods {

    @Test
    public void testAllMoviesShouldReturnStatus200() {
        test = extent.createTest("testAllMoviesShouldReturnStatus200");

        String endpoint = ConfigReader.getEndpoint("top_rated");
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("results"));
        test.pass("Status code 200 and results found in the response body.");
    }

    @Test
    public void testUnauthorizedTokenShouldReturnStatus401() {
        test = extent.createTest("testUnauthorizedTokenShouldReturnStatus401");

        String endpoint = ConfigReader.getEndpoint("now_playing");
        String invalidToken = "yJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2";
        Response response = sendGetRequest(endpoint, invalidToken);

        assertEquals(401, response.getStatusCode());
        test.pass("Status code 401 and Unauthorized message found.");
    }

    @Test
    public void testPostRequestToInvalidEndpointShouldReturnStatus404() {
        test = extent.createTest("testPostRequestToInvalidEndpointShouldReturnStatus404");

        String endpoint = ConfigReader.getEndpoint("now_playing");
        Response response = sendPostRequest(endpoint, ConfigReader.getAuthToken());

        assertEquals(404, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("The resource you requested could not be found."));
        test.pass("Status code 404 and resource not found message.");
    }

    @Test
    public void testInvalidPageNumberNegativeShouldReturn400() {
        test = extent.createTest("testInvalidPageNumberNegativeShouldReturn400");

        String endpoint = ConfigReader.getEndpoint("now_playing") + "&page=-1";
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        assertEquals(400, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("Invalid page: Pages start at 1 and max at 500. They are expected to be an integer."));
        test.pass("Status code 400 and invalid page message.");
    }

    @Test
    public void testNoDuplicateMoviesInResults() {
        test = extent.createTest("testNoDuplicateMoviesInResults");

        String endpoint = ConfigReader.getEndpoint("now_playing");
        Response response = sendGetRequest(endpoint, ConfigReader.getAuthToken());

        assertEquals(200, response.getStatusCode());
        assertTrue(response.getBody().asString().contains("results"));

        // Extract movie IDs and check for duplicates
        List<Integer> movieIds = response.jsonPath().getList("results.id");
        Set<Integer> uniqueMovieIds = new HashSet<>(movieIds);

        assertEquals(movieIds.size(), uniqueMovieIds.size());
        test.pass("No duplicate movies in the results.");
    }
}
