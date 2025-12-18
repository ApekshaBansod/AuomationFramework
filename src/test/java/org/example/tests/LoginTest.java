package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.LoginPage;
import org.example.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.config.ConfigManager;
import org.example.core.DriverManager;

public class LoginTest extends BaseTest {
    
    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        
        // Test data
        String username = ConfigManager.getUsername();
        String password = ConfigManager.getPassword();
        
        // Perform login
        loginPage.login(username, password);
        
        // Verify successful login
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        Assert.assertTrue(productPage.isProductPageDisplayed(), 
            "Product page should be displayed after successful login");
    }
    
    @Test(description = "Verify login with invalid credentials shows error message")
    public void testLoginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        
        // Test data - invalid credentials
        String invalidUsername = "invalid_user";
        String invalidPassword = "invalid_pass";
        
        // Attempt login with invalid credentials
        loginPage.login(invalidUsername, invalidPassword);
        
        // Verify error message is displayed
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(), 
            "Error message should be displayed for invalid login");
        
        // Verify the error message text
        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";
        Assert.assertEquals(loginPage.getErrorMessageText(), expectedErrorMessage, 
            "Error message text is incorrect");
    }
    
    @Test(description = "Verify logout functionality")
    public void testLogout() {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        
        // Login first
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        
        // Navigate to menu and click logout
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        
        
        
        productPage.openMenu().clickLogout();
        
        // Verify we're back at the login page
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
            "Login page should be displayed after logout");
    }
}
