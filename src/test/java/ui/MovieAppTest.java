package ui;

import com.aventstack.extentreports.Status;
import org.example.config.BaseTest;
import org.example.pages.WeMoviesPage;
import org.example.utils.AppiumHelpers;
import org.junit.Test;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class MovieAppTest extends BaseTest {
    private final AppiumHelpers helpers = new AppiumHelpers(driver);
    private final WeMoviesPage weMoviesPage = new WeMoviesPage();
    private final MovieMethods movieMethods = new MovieMethods(driver);
    @Test
    public void testProperWithProperName() {
        // Create a test instance for ExtentReports
        test = extent.createTest("Running test: Search with proper characters");

        try {
            test.log(Status.INFO, "Starting the test: Searching for the movie 'The Substance'");

            movieMethods.searchMovie("The Substance");
            test.log(Status.INFO, "Searched for the movie: 'The Substance'");

            boolean nowPlayingFound = helpers.isElementVisible(weMoviesPage.nowPlayingElement);
            boolean topRatedFound = helpers.isElementVisible(weMoviesPage.topRatedElement);

            if (nowPlayingFound) {
                test.log(Status.INFO, "Movie not present in 'Now Playing' section");
            } else {
                test.log(Status.PASS, "Movie present in 'Now Playing' section");
            }

            if (topRatedFound) {
                test.log(Status.INFO, "Movie not present in 'Top Rated' section");
            } else {
                test.log(Status.PASS, "Movie present in 'Top Rated' section");
            }

            test.log(Status.PASS, "Test completed successfully");
        } catch (Exception e) {
            test.log(Status.FAIL, "Test failed due to an exception: " + e.getMessage());
        } finally {
            extent.flush();
        }
    }
    @Test
    public void testSpecialCharacterSearch() {
        // Create and log the test in the extent report
        test = extent.createTest("Running test: Search with special characters");
        test.log(Status.INFO, "Starting test for searching with special characters: &^%$#@");

        // Perform the movie search
        movieMethods.searchMovie("&^%$#@");
        test.log(Status.INFO, "Performed search with special characters.");

        // Verify the results
        boolean resultsMatched = movieMethods.verifyResults( "No results found.", "No results found.");
        if (resultsMatched) {
            test.log(Status.PASS, "Results matched 'No results found.' for special characters.");
        } else {
            test.log(Status.FAIL, "Results did not match 'No results found.' for special characters.");
        }

        // Assert the results
        Assert.assertTrue(resultsMatched,
                "Results did not match 'No results found.' for special characters.");
    }

    @Test
    public void testSingleCharacterSearch() {
        // Create the test in Extent Reports
        test = extent.createTest("Running test: Single character search");
        test.log(Status.INFO, "Starting test for searching with a single character: 'A'");

        // Perform the movie search
        movieMethods.searchMovie("A");
        test.log(Status.INFO, "Performed search with a single character: 'A'");

        // Verify the results for "Now Playing"
        boolean nowPlayingFound = movieMethods.isElementPresent(weMoviesPage.nowPlayingElement);
        if (nowPlayingFound) {
            test.log(Status.INFO, "Movie not present in Now Playing section for single character search.");
        } else {
            test.log(Status.PASS, "Movie present for single character search in Now Playing section.");
        }

        // Assert the results
        Assert.assertFalse(nowPlayingFound, "Unexpected movie found in Now Playing section for single character search.");
    }

    @Test
    public void testRandomIntegerSearch() {
        // Create the test in Extent Reports
        test = extent.createTest("Running test: Random integer search");
        test.log(Status.INFO, "Starting test for searching with random integer: '2023'");

        // Perform the movie search
        movieMethods.searchMovie("2023");
        test.log(Status.INFO, "Performed search with random integer: '2023'");

        // Verify the results
        boolean resultsMatched = movieMethods.verifyResults("No results found.", "No results found.");
        if (resultsMatched) {
            test.log(Status.PASS, "Results matched 'No results found.' for random integer search.");
        } else {
            test.log(Status.FAIL, "Results did not match 'No results found.' for random integer search.");
        }

        // Assert the results
        Assert.assertTrue(resultsMatched, "Results did not match 'No results found.' for integer search.");
    }

    @Test
    public void testTopRatedMovieSearch() {
        // Create the test in Extent Reports
        test = extent.createTest("Running test: Search for a movie in Top Rated only");
        test.log(Status.INFO, "Starting test for searching Top Rated movie: 'The GodFather'");

        // Perform the movie search
        movieMethods.searchMovie("The GodFather");
        test.log(Status.INFO, "Performed search for Top Rated movie: 'The GodFather'");

        // Verify the results for "Now Playing" and "Top Rated"
        boolean nowPlayingFound = movieMethods.isElementPresent(weMoviesPage.nowPlayingElement);
        boolean topRatedFound = movieMethods.isElementPresent(weMoviesPage.topRatedElement);

        if (nowPlayingFound) {
            test.log(Status.INFO, "Movie not present in Now Playing section.");
        } else {
            test.log(Status.PASS, "Movie present in Now Playing section.");
        }

        if (topRatedFound) {
            test.log(Status.PASS, "Movie present in Top Rated section.");
        } else {
            test.log(Status.FAIL, "Movie not present in Top Rated section.");
        }

        // Assert the results
        Assert.assertFalse(nowPlayingFound, "Unexpected movie found in Now Playing section.");
        Assert.assertTrue(topRatedFound, "Expected movie not found in Top Rated section.");
    }


}
