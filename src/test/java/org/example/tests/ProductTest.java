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

public class ProductTest extends BaseTest {
    
    @BeforeMethod
    public void login() {
        // Login before each test
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.login(ConfigManager.getUsername(), ConfigManager.getPassword());
    }
    
    @Test(description = "Verify product sorting by name A to Z")
    public void testSortProductsByNameAToZ() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        
        // Sort by Name (A to Z)
        productPage.sortProducts("Name (A to Z)");
        
        // Verify sorting
        String firstProduct = productPage.getFirstProductName();
        String lastProduct = productPage.getLastProductName();
        
        Assert.assertTrue(firstProduct.compareTo(lastProduct) <= 0, 
            "Products should be sorted by name A to Z");
    }
    
    @Test(description = "Verify product sorting by price low to high")
    public void testSortProductsByPriceLowToHigh() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        
        // Sort by Price (low to high)
        productPage.sortProducts("Price (low to high)");
        
        // Get first and last product prices
        double firstPrice = productPage.getFirstProductPrice();
        double lastPrice = productPage.getLastProductPrice();
        
        Assert.assertTrue(firstPrice <= lastPrice, 
            "Products should be sorted by price low to high");
    }
    
    @Test(description = "Verify adding multiple products to cart")
    public void testAddMultipleProductsToCart() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        
        // Add three different products to cart
        productPage.addProductToCart("Sauce Labs Backpack");
        productPage.addProductToCart("Sauce Labs Bike Light");
        productPage.addProductToCart("Sauce Labs Bolt T-Shirt");
        
        // Verify cart count
        int cartCount = productPage.getCartItemCount();
        Assert.assertEquals(cartCount, 3, "Cart should contain 3 items");
        
        // Go to cart and verify items
        CartPage cartPage = productPage.goToCart();
        Assert.assertEquals(cartPage.getCartItemCount(), 3, "Cart should display 3 items");
        
        // Verify product names in cart
        Assert.assertTrue(cartPage.isItemInCart("Sauce Labs Backpack"), 
            "Sauce Labs Backpack should be in cart");
        Assert.assertTrue(cartPage.isItemInCart("Sauce Labs Bike Light"), 
            "Sauce Labs Bike Light should be in cart");
        Assert.assertTrue(cartPage.isItemInCart("Sauce Labs Bolt T-Shirt"), 
            "Sauce Labs Bolt T-Shirt should be in cart");
    }
    
    @Test(description = "Verify removing product from cart")
    public void testRemoveProductFromCart() {
        ProductPage productPage = new ProductPage(DriverManager.getDriver());
        
        // Add products to cart
        productPage.addProductToCart("Sauce Labs Backpack");
        productPage.addProductToCart("Sauce Labs Bike Light");
        
        // Remove one product
        productPage.removeProductFromCart("Sauce Labs Backpack");
        
        // Verify cart count
        int cartCount = productPage.getCartItemCount();
        Assert.assertEquals(cartCount, 1, "Cart should contain 1 item after removal");
        
        // Go to cart and verify remaining item
        CartPage cartPage = productPage.goToCart();
        Assert.assertFalse(cartPage.isItemInCart("Sauce Labs Backpack"), 
            "Sauce Labs Backpack should not be in cart");
        Assert.assertTrue(cartPage.isItemInCart("Sauce Labs Bike Light"), 
            "Sauce Labs Bike Light should still be in cart");
    }
}
