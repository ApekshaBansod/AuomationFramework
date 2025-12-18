package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends BasePage {
    
    @FindBy(id = "user-name")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login-button")
    private WebElement loginButton;
    
    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;
    
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
        
        // Handle Google Password Manager popup if it appears
        handleGooglePasswordManagerPopup();
    }
    
    /**
     * Handles Google Password Manager popup if it appears after login
     */
    private void handleGooglePasswordManagerPopup() {
        try {
            // Wait a bit longer for the popup to appear
            Thread.sleep(2000);
            
            // Try to find and click the "OK" button using JavaScript
            // This works even if the element is not directly clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            
            // Try to find the "OK" button using multiple possible selectors
            try {
                // First try: Click using JavaScript
                WebElement okButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(., 'OK') or contains(., 'Ok') or contains(., 'Got it') or contains(., 'Dismiss')]")
                ));
                
                if (okButton != null) {
                    // Use JavaScript click to avoid element not interactable issues
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", okButton);
                    System.out.println("Clicked the popup button using JavaScript");
                    Thread.sleep(1000); // Wait for the popup to close
                    return;
                }
            } catch (Exception e) {
                System.out.println("Could not find or click the popup button with first selector");
            }
            
            // If first attempt failed, try alternative approach
            try {
                // Try to press Escape key to dismiss the popup
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                System.out.println("Pressed Escape key to dismiss popup");
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("Could not dismiss popup with Escape key");
            }
            
        } catch (Exception e) {
            System.out.println("No popup appeared or could not be handled: " + e.getMessage());
        }
    }
    
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(errorMessage);
    }
    
    public String getErrorMessageText() {
        return getText(errorMessage);
    }
    
    public boolean isLoginPageDisplayed() {
        return isElementDisplayed(loginButton);
    }
}
