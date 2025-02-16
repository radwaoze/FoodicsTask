package com.amazon;

import base.baseMethods;
import data_driven.ReadPropertiesFile;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;


public class AmazonShoppingTest extends TestBase {
    private final ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    @Test
    public void testAmazonShoppingWorkflow() throws IOException, InterruptedException {
        //Login to Amazon
        AmazonLogin loginPage = new AmazonLogin(driver);
        loginPage.navigateToSignInPage();
        loginPage.loginByUsernameAndPassword(getPropValue.readPropertiesFile("email/phone"),
                getPropValue.readPropertiesFile("password"));
        loginPage.clickOnSignInCTA();

        // Navigate to Video Games
        AmazonHomePage homePage = new AmazonHomePage(driver);
        homePage.navigateToVideoGames();
        Assert.assertEquals(new baseMethods(driver).getPresentedText(By.xpath(getPropValue.readPropertiesFile("videoGameTitle"))),
                "Video Games", "The Actual title does not match the expected title");

        // Apply filters and sorting then add product below 15000 to cart
        AmazonProductPage products = new AmazonProductPage(driver);
        products.applyFilters();
        Assert.assertEquals(new baseMethods(driver).getPresentedText(By.xpath(getPropValue.readPropertiesFile("results"))),
                "Results", "The Actual title does not match the expected title");
        products.sortByHighToLow();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("sr_st_price-desc-rank"), "The URL does not contain the expected text: sr_st_price-desc-rank");

        int totalAddedProducts = products.addProductsBelowPrice(); // Fetch the total of added products that are below price 15000
        products.goToBasket(); // Navigate to Basket

        AmazonCartPage cart = new AmazonCartPage(driver);
        int totalCartItems = cart.fetchCartItems(); // Fetch the total count of added products in cart
        Assert.assertEquals(totalCartItems, totalAddedProducts, "The count of cart items does not match the added products counts");

        String subTotal = cart.getSubtotal(); // Fetch the subtotal in cart

        cart.proceedToBuy(); // Click on Proceed to buy
        cart.cancelPrimeOffer(); // Cancel Prime offer if appeared

        AmazonCheckoutPage checkout = new AmazonCheckoutPage(driver);
        checkout.addNewAddress(); // Add new address
        String totalOrder = checkout.getTotalOrder(); // Fetch the total in checkout

        String cleanedTotal = totalOrder.replaceAll("[^0-9]", "");
        double totalOrderPrice = Double.parseDouble(cleanedTotal);
        System.out.println("Parsed Total Order Price: " + totalOrderPrice);
        if (totalOrderPrice > 25000) {
            System.out.println("Total exceeds 25,000. COD is not available. Proceeding with credit card.");
            checkout.addNewCreditCard(); //Add new credit card if total greater than 25000
        } else {
            System.out.println("Total is within limit. COD is available.");
        }
        Assert.assertEquals(totalOrder, subTotal, "The total order doesn't equal subtotal!");

        String freeDelivery = checkout.getFreeDeliveryText();
        Assert.assertTrue(freeDelivery.contains("Free Delivery"), "The text of Free delivery doesn't contain the expected text: Free Delivery");

    }
}
