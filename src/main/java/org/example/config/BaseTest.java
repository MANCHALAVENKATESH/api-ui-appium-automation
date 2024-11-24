package org.example.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static AndroidDriver<MobileElement> driver;
    protected static ExtentReports extent = ExtentReportManager.getUiReportInstance();
    protected static ExtentTest test;

    // Initialize driver and setup before all tests
    @BeforeClass
    public static void setUp() throws Exception {
        // Load base configurations
        ConfigReader.loadBaseConfig();

        // Set up DesiredCapabilities for Appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", ConfigReader.getPlatform());
        capabilities.setCapability("appPackage", ConfigReader.getAppPackage());
        capabilities.setCapability("appActivity", ConfigReader.getAppActivity());
        capabilities.setCapability("udid", ConfigReader.getUDID());
        capabilities.setCapability("automationName", ConfigReader.getAutomationName());
        capabilities.setCapability("noReset",true);
        // Initialize the Appium driver (AndroidDriver)
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        test = extent.createTest("Sample");
        test.pass("login");
        // Initialize ExtentTest for each test method
        // test will be initialized before each test method using initializeTest()
    }

    // Getter for driver
    public static AndroidDriver<MobileElement> getDriver() {
        return driver;
    }

    // Clean up after all tests
    @AfterClass
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            if (test != null) {
                test.log(Status.INFO, "Driver quit successfully!");
            }
        }
    }
}
