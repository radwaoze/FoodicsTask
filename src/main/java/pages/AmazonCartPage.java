package pages;

import base.baseMethods;
import data_driven.ReadPropertiesFile;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AmazonCartPage {

    private WebDriver driver;

    public AmazonCartPage(WebDriver driver) {
        this.driver = driver;
    }

    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    public int fetchCartItems() throws IOException {
        baseMethods base = new baseMethods(driver);

        String shoppingCartTitle = base.getPresentedText(By.xpath(getPropValue.
                readPropertiesFile("shoppingCart")));
        System.out.println("Shopping Cart Title: " + shoppingCartTitle);

        List<WebElement> cartItems = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(getPropValue.readPropertiesFile("cartItems"))));

        if (cartItems.isEmpty()) {
            throw new AssertionError("No products are added to cart. Check if XPath is correct and elements are loaded.");
        }
        System.out.println("Total added products to cart: " + cartItems.size());

        return cartItems.size();
    }

    public void proceedToBuy() throws IOException {
        baseMethods base = new baseMethods(driver);
        base.clickOnElement(By.name(getPropValue.readPropertiesFile("proceedToBuy")));
    }

    public void cancelPrimeOffer() throws IOException {
        baseMethods base = new baseMethods(driver);

        WebElement amazonPrimeTitle = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("amazonPrimeTitle")), "visibilityOfElement");
        WebElement amazonAnotherPrimeTitle = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("amazonAnotherPrimeTitle")), "visibilityOfElement");
        if ((amazonPrimeTitle != null && amazonPrimeTitle.isDisplayed()) ||
                (amazonAnotherPrimeTitle != null && amazonAnotherPrimeTitle.isDisplayed())) {

            base.waitScreen();
            System.out.println("Amazon Prime offer is displayed. Declining...");

            WebElement noPrimeThanks = base.waitExpectedCondition(By.xpath(getPropValue.
                    readPropertiesFile("noPrimeThanks")), "visibilityOfElement");

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", noPrimeThanks);
            noPrimeThanks.click();
        } else {
            System.out.println("Amazon Prime offer is not displayed.");


        }
    }

        public String getSubtotal () throws IOException {
            baseMethods base = new baseMethods(driver);
            base.waitScreen();

            return new baseMethods(driver).getPresentedText(By.xpath(getPropValue.readPropertiesFile("subTotal")));
        }
    }
