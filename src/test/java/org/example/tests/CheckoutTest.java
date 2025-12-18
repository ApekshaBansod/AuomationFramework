package org.example.tests;

import org.example.pages.CartPage;
import org.example.pages.CheckoutPage;
import org.example.pages.LoginPage;
import org.example.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.base.BaseTest;
import org.example.core.DriverManager;
import org.example.config.ConfigManager;





public class CheckoutTest extends BaseTest {
    
    @BeforeMethod
    public void loginAndAddItemsToCart() {
        // Login and add items to cart before each test
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Add a product to cart
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        productPage.addProductToCart("Sauce Labs Backpack");
        
        // Go to cart and proceed to checkout
        CartPage cartPage = productPage.goToCart();
        cartPage.proceedToCheckout();
    }
    
    @Test(description = "Verify successful checkout with valid information")
    public void testSuccessfulCheckout() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        
        // Fill checkout information
        checkoutPage.fillCheckoutInformation("John", "Doe", "12345");
        checkoutPage.clickContinue();
        
        // Verify checkout overview
        Assert.assertTrue(checkoutPage.isCheckoutOverviewDisplayed(), 
            "Should be on checkout overview page");
            
        // Verify order summary
        String itemTotal = checkoutPage.getItemTotal();
        String tax = checkoutPage.getTax();
        String total = checkoutPage.getTotal();
        
        Assert.assertFalse(itemTotal.isEmpty(), "Item total should be displayed");
        Assert.assertFalse(tax.isEmpty(), "Tax should be displayed");
        Assert.assertFalse(total.isEmpty(), "Total should be displayed");
        
        // Complete checkout
        checkoutPage.finishCheckout();
        
        // Verify order completion
        Assert.assertTrue(checkoutPage.isOrderComplete(), 
            "Order completion message should be displayed");
    }
    
    @DataProvider(name = "invalidCheckoutData")
    public Object[][] getInvalidCheckoutData() {
        return new Object[][] {
            {"", "Doe", "12345", "Error: First Name is required"},
            {"John", "", "12345", "Error: Last Name is required"},
            {"John", "Doe", "", "Error: Postal Code is required"}
        };
    }
    
    @Test(dataProvider = "invalidCheckoutData", 
          description = "Verify form validation with missing required fields")
    public void testCheckoutFormValidation(String firstName, String lastName, 
                                         String zipCode, String expectedError) {
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        
        // Fill checkout information with missing fields
        checkoutPage.fillCheckoutInformation(firstName, lastName, zipCode);
        checkoutPage.clickContinue();
        
        // Verify error message
        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertEquals(errorMessage, expectedError, 
            "Incorrect error message for missing required field");
    }
    
    @Test(description = "Verify cancel button on checkout information page")
    public void testCancelCheckoutFromInformationPage() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        
        // Click cancel
        CartPage cartPage = checkoutPage.clickCancel();
        
        // Verify we're back to cart page
        Assert.assertTrue(cartPage.isCartPageDisplayed(), 
            "Should be back to cart page after canceling checkout");
    }
    
    @Test(description = "Verify cancel button on checkout overview page")
    public void testCancelCheckoutFromOverviewPage() {
        CheckoutPage checkoutPage = new CheckoutPage(DriverManager.getDriver());
        
        // Fill valid information and continue
        checkoutPage.fillCheckoutInformation("John", "Doe", "12345");
        checkoutPage.clickContinue();
        
        // Click cancel and verify we're back to cart page
        CartPage cartPage = checkoutPage.clickCancel();
        
        // Verify we're back to cart page
        Assert.assertTrue(cartPage.isCartPageDisplayed(), 
            "Should be back to cart page after canceling checkout from overview");
    }
}
