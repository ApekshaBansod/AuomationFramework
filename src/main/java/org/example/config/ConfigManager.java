package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "src/main/resources/config.properties";
    private static final String ENV = System.getProperty("env", "dev");
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            
            // Load environment-specific properties if they exist
            String envFile = String.format("src/main/resources/%s.properties", ENV);
            try (FileInputStream envFis = new FileInputStream(envFile)) {
                properties.load(envFis);
            } catch (IOException e) {
                System.out.println("No environment-specific properties file found, using default.");
            }
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getBaseUrl() {
        return getProperty("base.url");
    }
    
    public static String getUsername() {
        return getProperty("username");
    }
    
    public static String getPassword() {
        return getProperty("password");
    }
    
    public static String getBrowser() {
        return System.getProperty("browser", "chrome");
    }
    
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }
}
