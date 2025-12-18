package org.example.base;

import io.qameta.allure.Allure;
import org.example.core.DriverManager;
import org.example.utils.ScreenshotUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.ByteArrayInputStream;

@Listeners
public class BaseTest {
    
    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(@Optional("chrome") String browser, @Optional("false") boolean headless) {
        System.setProperty("browser", browser);
        System.setProperty("headless", String.valueOf(headless));
        DriverManager.getDriver().get(org.example.config.ConfigManager.getBaseUrl());
    }
    
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                // Take screenshot and save to file
                String testName = result.getMethod().getMethodName();
                String screenshotPath = ScreenshotUtils.saveScreenshot(DriverManager.getDriver(), testName);
                
                // Attach to Allure report
                byte[] screenshot = ScreenshotUtils.takeScreenshot(DriverManager.getDriver());
                Allure.addAttachment("Screenshot on failure", new ByteArrayInputStream(screenshot));
                
                System.out.println("Screenshot saved to: " + screenshotPath);
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
            
            // Log the failure
            Allure.addAttachment("Test Failure", "text/plain", 
                "Test failed: " + result.getName() + "\n" +
                "Error: " + result.getThrowable().getMessage()
            );
        }
        
        // Clear cookies and quit the driver
        DriverManager.clearCookies();
        DriverManager.quitDriver();
    }
}
