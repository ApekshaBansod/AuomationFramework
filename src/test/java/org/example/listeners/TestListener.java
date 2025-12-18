package org.example.listeners;

import io.qameta.allure.Attachment;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.example.utils.ScreenshotUtils;
import org.example.core.DriverManager;

public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getName());
        attachScreenshotToReport("Screenshot on success");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getName());
        attachScreenshotToReport("Screenshot on failure");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Implementation can be added if needed
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("Starting test execution for: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Finished test execution for: " + context.getName());
    }

    @Attachment(value = "{screenshotName}", type = "image/png")
    private byte[] attachScreenshotToReport(String screenshotName) {
        return ScreenshotUtils.takeScreenshot(DriverManager.getDriver());
    }
}
