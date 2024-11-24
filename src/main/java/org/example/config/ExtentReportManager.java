package org.example.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    public static ExtentReports extent;
    private static ExtentSparkReporter htmlReporter;

    // This method will handle creating API reports
    public static ExtentReports getApiReportInstance() {
        if (extent == null) {
            // Generate a unique report name for API tests based on the current timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "reports/api/extent-report-" + timestamp + ".html"; // Store reports in reports/api

            // Initialize the ExtentSparkReporter with the unique report path
            htmlReporter = new ExtentSparkReporter(reportPath);
            // Optionally set the theme for the report
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setDocumentTitle("API Test Report");
            htmlReporter.config().setReportName("API Test Execution Report");

            // Initialize ExtentReports and attach the reporter
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            System.out.println("API report initialized at: " + reportPath);
        }
        return extent;
    }

    // This method will handle creating UI reports
    public static ExtentReports getUiReportInstance() {
        if (extent == null) {
            // Generate a unique report name for UI tests based on the current timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "reports/ui/extent-report-" + timestamp + ".html"; // Store reports in reports/ui

            // Initialize the ExtentSparkReporter with the unique report path
            htmlReporter = new ExtentSparkReporter(reportPath);
            // Optionally set the theme for the report
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setDocumentTitle("UI Test Report");
            htmlReporter.config().setReportName("UI Test Execution Report");

            // Initialize ExtentReports and attach the reporter
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            System.out.println("UI report initialized at: " + reportPath);
        }
        return extent;
    }
}
