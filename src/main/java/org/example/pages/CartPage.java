package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage extends BasePage {
    
    @FindBy(className = "title")
    private WebElement pageTitle;
    
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;
    
    @FindBy(id = "checkout")
    private WebElement checkoutButton;
    
    @FindBy(className = "cart_quantity")
    private List<WebElement> itemQuantities;
    
    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;
    
    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;
    
    @FindBy(className = "cart_button")
    private List<WebElement> removeButtons;
    
    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;
    
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public boolean isCartPageDisplayed() {
        return isElementDisplayed(pageTitle) && pageTitle.getText().equals("Your Cart");
    }
    
    public int getCartItemCount() {
        return cartItems.size();
    }
    
    public void removeItem(int index) {
        if (index >= 0 && index < removeButtons.size()) {
            click(removeButtons.get(index));
        }
    }
    
    public void removeItem(String itemName) {
        for (int i = 0; i < itemNames.size(); i++) {
            if (itemNames.get(i).getText().equals(itemName)) {
                click(removeButtons.get(i));
                break;
            }
        }
    }
    
    public String getItemName(int index) {
        if (index >= 0 && index < itemNames.size()) {
            return itemNames.get(index).getText();
        }
        return "";
    }
    
    public String getItemPrice(int index) {
        if (index >= 0 && index < itemPrices.size()) {
            return itemPrices.get(index).getText();
        }
        return "";
    }
    
    public int getItemQuantity(int index) {
        if (index >= 0 && index < itemQuantities.size()) {
            return Integer.parseInt(itemQuantities.get(index).getText());
        }
        return 0;
    }
    
    public CheckoutPage proceedToCheckout() {
        click(checkoutButton);
        return new CheckoutPage(driver);
    }
    
    public boolean isItemInCart(String itemName) {
        return itemNames.stream().anyMatch(item -> item.getText().equals(itemName));
    }
    
    public ProductPage clickContinueShopping() {
        click(continueShoppingButton);
        return new ProductPage(driver);
    }
}
