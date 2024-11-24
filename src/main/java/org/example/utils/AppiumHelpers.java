package org.example.utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;

import java.time.Duration;

public class AppiumHelpers {
    private AndroidDriver<MobileElement> driver;

    // Constructor to initialize the driver
    public AppiumHelpers(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public void swipeUp() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point(width / 2, (int) (height * 0.8)))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(width / 2, (int) (height * 0.2)))
                .release()
                .perform();
    }

    public String getAttribute(By element, String property) {
        return driver.findElement(element).getAttribute(property);
    }

    public void swipeDown() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point(width / 2, (int) (height * 0.2)))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(width / 2, (int) (height * 0.8)))
                .release()
                .perform();
    }

    public void swipeLeft() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point((int) (width * 0.8), height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point((int) (width * 0.2), height / 2))
                .release()
                .perform();
    }

    public void swipeRight() {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;

        TouchAction<?> action = new TouchAction<>(driver);
        action.press(PointOption.point((int) (width * 0.2), height / 2))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point((int) (width * 0.8), height / 2))
                .release()
                .perform();
    }

    public void scrollToText(String text) {
        driver.findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + text + "\"))"
        );
    }

    public void sendKeys(By element, String text) {
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }

    public void click(By element) {
        driver.findElement(element).click();
    }

    public void tapElement(By element) {
        TouchAction<?> action = new TouchAction<>(driver);
        action.tap(PointOption.point(driver.findElement(element).getCenter())).perform();
    }

    public boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
