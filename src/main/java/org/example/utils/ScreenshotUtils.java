package org.example.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {
    
    private static final String SCREENSHOT_DIR = "screenshots";
    
    static {
        // Create screenshots directory if it doesn't exist
        File directory = new File(SCREENSHOT_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    
    public static byte[] takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    
    public static String saveScreenshot(WebDriver driver, String testName) {
        try {
            // Create screenshot directory if it doesn't exist
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }
            
            // Generate timestamp and filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = String.format("%s_%s.png", testName, timestamp);
            Path filePath = screenshotDir.resolve(fileName);
            
            // Take and save screenshot
            byte[] screenshot = takeScreenshot(driver);
            Files.write(filePath, screenshot);
            
            return filePath.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to save screenshot: " + e.getMessage();
        }
    }
    
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            byte[] screenshot = takeScreenshot(driver);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = String.format("%s/%s_%s.png", SCREENSHOT_DIR, testName, timestamp);
            
            Path path = Paths.get(fileName);
            Files.write(path, screenshot);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to capture screenshot: " + e.getMessage();
        }
    }
}
