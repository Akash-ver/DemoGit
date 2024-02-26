package rahulshettyacademy.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.TestComponents.Retry;
import rahulshettyacademy.pageobjects.*;

import java.io.IOException;
import java.util.List;

public class ErrorValidations extends BaseTest {
     @Test(groups = {"ErrorHandling"},retryAnalyzer = Retry.class)
     public void LoginErrorValidation() throws IOException, InterruptedException {
       String productName="ZARA COAT 3";
       LandingPage landingPage=launchApplication();

       ProductCatalogue productCatalogue=landingPage.loginApplication("test1234897@gmail.com","Test@123456789");
      Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
    }
    @Test
    public void productErrorValidation() throws IOException, InterruptedException {
        String productName="ZARA COAT 3";
        LandingPage landingPage=launchApplication();

        ProductCatalogue productCatalogue=landingPage.loginApplication("test1234897@gmail.com","Test@123456");

        List<WebElement> products=productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage=productCatalogue.goToCartPage();
        Boolean match=cartPage.verifyProductDisplay("ZARA COAT1 3");
        Assert.assertFalse(match);
    }
}
