package rahulshettyacademy.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.*;

import java.io.IOException;
import java.util.List;

public class StepDefinitionImpl extends BaseTest {
    public LandingPage landingPage;
    public ProductCatalogue productCatalogue;
    public ConfirmationPage confirmationPage;
    @Given("I landed on Ecommerce page")
    public void I_landed_on_Ecommerce_page() throws IOException {
        landingPage=launchApplication();
        //code
    }
    @Given("^Logged in with username (.+) and password (.+)$")
    public void logged_in_username_and_password(String username,String password)
    {
        productCatalogue=landingPage.loginApplication(username,password);
    }
    @When("^I add product (.+) from Cart$")
    public void i_add_product_to_cart(String productName) throws InterruptedException {
        List<WebElement> products=productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
    }
   @And("^Checkout (.+) and submit the order$")
    public void checkout_submit_order(String productName)
   {
       CartPage cartPage=productCatalogue.goToCartPage();
       Boolean match=cartPage.verifyProductDisplay(productName);
       Assert.assertTrue(match);
       CheckoutPage checkoutPage=cartPage.goToCheckout();
       checkoutPage.selectCountry("India");
       confirmationPage= checkoutPage.submitOrder();
   }
   @Then("{string} message is displayed on ConfirmationPage")
    public void message_displayed_ConfirmationPage(String string)
   {
       String confirmMessage=confirmationPage.getConfirmationMessage();
       Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
       driver.close();
   }

    @Then("{string} message is displayed")
    public void iShouldSeeTheMessageDisplayed(String string) {
        // Add code to verify that the error message is displayed
        Assert.assertEquals(string,landingPage.getErrorMessage());
        driver.close();
    }


}

