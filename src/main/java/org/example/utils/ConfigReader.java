package org.example.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties = new Properties();
    private static Properties apiProperties = new Properties();

    private static String baseURI;

    // Load the base config file
    public static void loadBaseConfig(){
        try {
            String configPath = System.getProperty("user.dir") + "/src/main/resources/config/config.properties";
            FileInputStream fileInputStream = new FileInputStream(configPath);
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("Error loading Base configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load the API config file
    public static void loadApiConfig(){
        try {
            String configPath = System.getProperty("user.dir") + "/src/main/resources/config/api-config.properties";
            FileInputStream fileInputStream = new FileInputStream(configPath);
            apiProperties.load(fileInputStream);
        } catch (IOException e) {
            System.err.println("Error loading API configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Get base URI from the base config file
    public static String getBaseURI() {
        if (baseURI == null) {
            baseURI = apiProperties.getProperty("baseURI");
        }
        return baseURI;
    }

    // Get authentication token from the properties file
    public static String getAuthToken() {
        return apiProperties.getProperty("authToken");
    }

    // Get endpoint from the API config file by key
    public static String getEndpoint(String key) {
        return apiProperties.getProperty(key);
    }

    // Get platform from the base config file
    public static String getPlatform() {
        return properties.getProperty("platform");
    }

    // Get UDID from the base config file
    public static String getUDID(){
        return properties.getProperty("udid");
    }

    // Get app activity from the base config file
    public static String getAppActivity(){
        return properties.getProperty("appActivity");
    }

    // Get app package from the base config file
    public static String getAppPackage(){
        return properties.getProperty("appPackage");
    }
    public static String getAutomationName(){
        return properties.getProperty("automationName");
    }
    public static String getContentType(){
        return apiProperties.getProperty("contentType");
    }
}
