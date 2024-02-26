package rahulshettyacademy.tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class SubmitOrderTest extends BaseTest {
    String productName="ZARA COAT 3";
     @Test(dataProvider ="getData",groups = {"Purchase"})
     public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {

       LandingPage landingPage=launchApplication();

       ProductCatalogue productCatalogue=landingPage.loginApplication(input.get("email"), input.get("password"));

       List<WebElement> products=productCatalogue.getProductList();
       productCatalogue.addProductToCart(input.get("product"));
       CartPage cartPage=productCatalogue.goToCartPage();

       Boolean match=cartPage.verifyProductDisplay(input.get("product"));
       Assert.assertTrue(match);
       CheckoutPage checkoutPage=cartPage.goToCheckout();
       checkoutPage.selectCountry("India");
       ConfirmationPage confirmationPage= checkoutPage.submitOrder();

       String confirmMessage=confirmationPage.getConfirmationMessage();
       Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods ={"submitOrder"})
    public void orderHistoryTest()
    {
        ProductCatalogue productCatalogue=landingPage.loginApplication("test1234897@gmail.com","Test@123456");
        OrderPage ordersPage=productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.verifyOrderDisplay(productName));
    }

    @DataProvider
    public Object[][] getData() throws IOException {
//        HashMap<String,String> map=new HashMap<String,String>();
//        map.put("email","test1234897@gmail.com");
//        map.put("password","Test@123456");
//        map.put("product","ZARA COAT 3");
//
//        HashMap<String,String> map1=new HashMap<String,String>();
//        map1.put("email","Test8974@gmail.com");
//        map1.put("password","Test8974@gmail.com");
//        map1.put("product","ADIDAS ORIGINAL");
        List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//rahulshettyacademy//data//PurchaseOrder.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};
    }
}
