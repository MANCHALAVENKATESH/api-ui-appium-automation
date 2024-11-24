package ui;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.example.pages.WeMoviesPage;
import org.example.utils.AppiumHelpers;
import org.openqa.selenium.By;

import java.util.Map;

import static org.example.config.BaseTest.driver;
import static org.example.config.BaseTest.getDriver;

public class MovieMethods {
    private final AndroidDriver<MobileElement> driver;

    public MovieMethods(AndroidDriver<MobileElement> driver){
        this.driver = driver;
    }
    private final AppiumHelpers helpers = new AppiumHelpers(getDriver());
    private final WeMoviesPage weMoviesPage = new WeMoviesPage();
    public void searchMovie(String movieTitle) {
        try {
            Thread.sleep(3000);
            helpers.click(weMoviesPage.searchBar);
            Thread.sleep(2000);
            helpers.sendKeys(weMoviesPage.searchBar,movieTitle);
            Thread.sleep(2000);
            driver.hideKeyboard();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isElementPresent(By xpath) {
        try {
            driver.findElement((xpath));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public  boolean verifyResults(String expectedNowPlaying, String expectedTopRated) {
        try {

            String nowPlayingContentDesc = helpers.getAttribute(weMoviesPage.nowPlayingElement,"content-desc");
            String topRatedContentDesc = helpers.getAttribute(weMoviesPage.topRatedElement,"content-desc");

            return nowPlayingContentDesc.equals(expectedNowPlaying) && topRatedContentDesc.equals(expectedTopRated);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void pressEnterKey(AndroidDriver<MobileElement> driver) {
        try {
            driver.executeScript("mobile: shell", Map.of(
                    "command", "input",
                    "args", new String[]{"keyevent", "66"}
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
