package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage extends BasePage {
    
    @FindBy(id = "first-name")
    private WebElement firstNameField;
    
    @FindBy(id = "last-name")
    private WebElement lastNameField;
    
    @FindBy(id = "postal-code")
    private WebElement zipCodeField;
    
    @FindBy(id = "continue")
    private WebElement continueButton;
    
    @FindBy(className = "title")
    private WebElement pageTitle;
    
    @FindBy(className = "summary_subtotal_label")
    private WebElement itemTotal;
    
    @FindBy(className = "summary_tax_label")
    private WebElement tax;
    
    @FindBy(className = "summary_total_label")
    private WebElement total;
    
    @FindBy(id = "finish")
    private WebElement finishButton;
    
    @FindBy(id = "cancel")
    private WebElement cancelButton;
    
    @FindBy(className = "complete-header")
    private WebElement thankYouMessage;
    
    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;
    
    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public boolean isCheckoutPageDisplayed() {
        return isElementDisplayed(pageTitle) && pageTitle.getText().equals("Checkout: Your Information");
    }
    
    public void fillCheckoutInformation(String firstName, String lastName, String zipCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(zipCodeField, zipCode);
    }
    
    public void clickContinue() {
        click(continueButton);
    }
    
    public boolean isCheckoutOverviewDisplayed() {
        return isElementDisplayed(pageTitle) && pageTitle.getText().equals("Checkout: Overview");
    }
    
    public String getItemTotal() {
        return isElementDisplayed(itemTotal) ? itemTotal.getText() : "";
    }
    
    public String getTax() {
        return isElementDisplayed(tax) ? tax.getText() : "";
    }
    
    public String getTotal() {
        return isElementDisplayed(total) ? total.getText() : "";
    }
    
    public void finishCheckout() {
        click(finishButton);
    }
    
    public boolean isOrderComplete() {
        return isElementDisplayed(thankYouMessage) && 
               thankYouMessage.getText().equals("THANK YOU FOR YOUR ORDER");
    }
    
    public String getErrorMessage() {
        return isElementDisplayed(errorMessage) ? errorMessage.getText() : "";
    }
    
    public void completeCheckout(String firstName, String lastName, String zipCode) {
        fillCheckoutInformation(firstName, lastName, zipCode);
        clickContinue();
        finishCheckout();
    }
    
    public CartPage clickCancel() {
        click(cancelButton);
        return new CartPage(driver);
    }
}
