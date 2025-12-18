package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage {
    
    @FindBy(className = "title")
    private WebElement pageTitle;
    
    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;
    
    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;
    
    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;
    
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;
    
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;
    
    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;
    
    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public boolean isProductPageDisplayed() {
        return isElementDisplayed(pageTitle) && pageTitle.getText().equals("Products");
    }
    
    public int getProductCount() {
        return productItems.size();
    }
    
    public double getFirstProductPrice() {
        if (productItems.isEmpty()) {
            return 0.0;
        }
        WebElement firstProduct = productItems.get(0);
        String priceText = firstProduct.findElement(By.className("inventory_item_price")).getText();
        return Double.parseDouble(priceText.replace("$", ""));
    }
    
    public double getLastProductPrice() {
        if (productItems.isEmpty()) {
            return 0.0;
        }
        WebElement lastProduct = productItems.get(productItems.size() - 1);
        String priceText = lastProduct.findElement(By.className("inventory_item_price")).getText();
        return Double.parseDouble(priceText.replace("$", ""));
    }
    
    public void addProductToCart(String productName) {
        WebElement product = findProductByName(productName);
        if (product != null) {
            WebElement addToCartButton = product.findElement(By.xpath(".//button[contains(@id,'add-to-cart')]"));
            click(addToCartButton);
        }
    }
    
    public void removeProductFromCart(String productName) {
        WebElement product = findProductByName(productName);
        if (product != null) {
            WebElement removeButton = product.findElement(By.xpath(".//button[contains(@id,'remove')]"));
            click(removeButton);
        }
    }
    
    public void sortProducts(String sortOption) {
        Select sortSelect = new Select(sortDropdown);
        sortSelect.selectByVisibleText(sortOption);
    }
    
    public String getFirstProductName() {
        if (!productItems.isEmpty()) {
            return productItems.get(0).findElement(By.className("inventory_item_name")).getText();
        }
        return "";
    }
    
    public String getLastProductName() {
        if (!productItems.isEmpty()) {
            return productItems.get(productItems.size() - 1).findElement(By.className("inventory_item_name")).getText();
        }
        return "";
    }
    
    public int getCartItemCount() {
        try {
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }
    
    public CartPage goToCart() {
        click(cartIcon);
        return new CartPage(driver);
    }
    
    private WebElement findProductByName(String productName) {
        for (WebElement product : productItems) {
            WebElement nameElement = product.findElement(By.className("inventory_item_name"));
            if (nameElement.getText().equals(productName)) {
                return product;
            }
        }
        return null;
    }
    
    public ProductPage openMenu() {
        try {
            // Wait for menu button to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(menuButton));
            
            // Click the menu button
            click(menuButton);
            
            // Add a small delay to allow menu animation to complete
            Thread.sleep(500);
            
            // Wait for the logout link to be visible and clickable
            wait.until(ExpectedConditions.and(
                ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")),
                ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))
            ));
            
            return this;
        } catch (Exception e) {
            throw new RuntimeException("Failed to open menu: " + e.getMessage(), e);
        }
    }
    
    public LoginPage clickLogout() {
        try {
            // First ensure menu is open
            openMenu();
            
            // Wait for logout link to be clickable and click it
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
            
            // Use JavaScript click for better reliability
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);
            
            // Wait for the login page to load
            wait.until(ExpectedConditions.urlContains("saucedemo.com"));
            
            return new LoginPage(driver);
        } catch (Exception e) {
            throw new RuntimeException("Logout failed: " + e.getMessage(), e);
        }
    }
}
