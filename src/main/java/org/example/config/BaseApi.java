package org.example.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.example.config.BaseTest.extent;

public class BaseApi {
    protected static ExtentTest test;
    protected static ExtentReports extent = ExtentReportManager.getApiReportInstance();

    @BeforeClass
    public static void setUp() {
        // Set base URI for all API requests
        ConfigReader.loadApiConfig();
        RestAssured.baseURI = ConfigReader.getBaseURI();
    }
    @AfterClass
    public static void tearDown() {
        if (extent != null) {
            extent.flush(); // Write test logs to the report
        }
    }
}
