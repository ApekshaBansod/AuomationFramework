package org.example.tests;

import org.example.base.BaseTest;
import org.example.config.ConfigManager;
import org.example.core.DriverManager;
import org.example.pages.CartPage;
import org.example.pages.LoginPage;
import org.example.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.example.pages.CheckoutPage;


public class CartTest extends BaseTest {
    
    @BeforeMethod
    public void loginAndAddItemsToCart() {
        // Login and add items to cart before each test
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
        
        // Add some products to cart
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        productPage.addProductToCart("Sauce Labs Backpack");
        productPage.addProductToCart("Sauce Labs Bike Light");
    }
    
    @Test(description = "Verify items can be removed from cart")
    public void testRemoveItemFromCart() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        CartPage cartPage = productPage.goToCart();
        
        // Verify initial cart count
        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Initial cart should have 2 items");
        
        // Remove one item
        cartPage.removeItem(0);
        
        // Verify cart count after removal
        Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should have 1 item after removal");
    }
    
    @Test(description = "Verify cart displays correct item details")
    public void testCartItemDetails() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        CartPage cartPage = productPage.goToCart();
        
        // Verify cart has correct items with correct details
        Assert.assertTrue(cartPage.isItemInCart("Sauce Labs Backpack"), 
            "Sauce Labs Backpack should be in cart");
        Assert.assertTrue(cartPage.isItemInCart("Sauce Labs Bike Light"), 
            "Sauce Labs Bike Light should be in cart");
            
        // Verify quantities (should be 1 for each item)
        Assert.assertEquals(cartPage.getItemQuantity(0), 1, "Item quantity should be 1");
        Assert.assertEquals(cartPage.getItemQuantity(1), 1, "Item quantity should be 1");
    }
    
    @Test(description = "Verify continue shopping button returns to products page")
    public void testContinueShopping() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        CartPage cartPage = productPage.goToCart();
        
        // Click continue shopping
        cartPage.clickContinueShopping();
        
        // Verify we're back on products page
        Assert.assertTrue(productPage.isProductPageDisplayed(), 
            "Should be on products page after clicking continue shopping");
    }
    
    @Test(description = "Verify checkout button navigates to checkout page")
    public void testCheckoutNavigation() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        CartPage cartPage = productPage.goToCart();
        
        // Click checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        
        // Verify we're on checkout information page
        Assert.assertTrue(checkoutPage.isCheckoutPageDisplayed(), 
            "Should be on checkout information page");
    }
}
