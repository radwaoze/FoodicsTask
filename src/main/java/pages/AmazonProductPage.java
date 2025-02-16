package pages;

import base.baseMethods;
import data_driven.ReadPropertiesFile;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;
import java.util.List;


public class AmazonProductPage {

    private WebDriver driver;

    public AmazonProductPage(WebDriver driver) {
        this.driver = driver;
    }

    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    public void applyFilters() throws IOException {
        baseMethods base = new baseMethods(driver);
        base.waitScreen();
        WebElement freeShipping = base.waitExpectedCondition(
                By.xpath(getPropValue.readPropertiesFile("freeShipping")), "visibilityOfElement");
        if (freeShipping == null) {
            throw new IllegalStateException("Free Shipping element not found");
        }
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", freeShipping);
        } catch (JavascriptException e) {
            System.out.println("Failed to scroll to free shipping: " + e.getMessage());
            throw e;
        }
        freeShipping.click();

        base.waitScreen();
        WebElement newFilter = base.waitExpectedCondition(
                By.xpath(getPropValue.readPropertiesFile("newFilter")), "visibilityOfElement");
        if (newFilter == null) {
            throw new IllegalStateException("New Filter element not found");
        }
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newFilter);
        } catch (JavascriptException e) {
            System.out.println("Failed to scroll to new filter: " + e.getMessage());
            throw e;
        }
        newFilter.click();
    }

    public void sortByHighToLow() throws IOException {
        baseMethods base = new baseMethods(driver);
        base.clickOnElement(By.id(getPropValue.readPropertiesFile("sortDropdown")));
        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("highToLow")));
    }

    public int addProductsBelowPrice() throws IOException {
        int addedProductsCount = 0;
        List<WebElement> productContainers = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(getPropValue.readPropertiesFile("productsList"))));

        if (productContainers.isEmpty()) {
            throw new AssertionError("No products found on the page. Check if XPath is correct and elements are loaded.");
        }

        System.out.println("Total products found: " + productContainers.size());

        for (WebElement product : productContainers) {
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);

                WebElement priceElement = product.findElement(By.xpath(getPropValue.readPropertiesFile("productPrice")));

                if (priceElement == null || priceElement.getText().isEmpty()) {
                    System.out.println("Skipping product: Price not found.");
                    continue;
                }

                String priceText = priceElement.getText().replaceAll("[^0-9]", "");
                if (priceText.isEmpty()) {
                    System.out.println("Skipping product: Invalid price format.");
                    continue;
                }

                double price = Double.parseDouble(priceText);
                System.out.println("Product Price: " + price);

                if (price < 15000) {
                    WebElement addToCartBtn = new WebDriverWait(driver, Duration.ofSeconds(5))
                            .until(ExpectedConditions.elementToBeClickable(
                                    product.findElement(By.xpath(".//button[@name='submit.addToCart' and contains(text(), 'Add to cart')]"))
                            ));

                    if (addToCartBtn == null) {
                        System.out.println("Skipping product: Add to cart button not found.");
                        continue;
                    }

                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", addToCartBtn);
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartBtn);

                    System.out.println("Added product with price: " + price);
                    addedProductsCount++;

                    Thread.sleep(1500);
                }
            } catch (NoSuchElementException | TimeoutException | InterruptedException e) {
                System.out.println("Skipping product: Missing price or add-to-cart button.");
            } catch (StaleElementReferenceException e) {
                System.out.println("Retrying: Element went stale, trying again...");
            }
        }

        System.out.println("Total products added to cart: " + addedProductsCount);

        if (addedProductsCount == 0 && isNextPageAvailable()) {
            goToNextPage();
            return addProductsBelowPrice();
        }

        return addedProductsCount;
    }

    public boolean isNextPageAvailable() {
        try {
            WebElement nextCTA = driver.findElement(By.xpath(getPropValue.readPropertiesFile("nextCTA")));
            return nextCTA.isDisplayed();
        } catch (NoSuchElementException | IOException e) {
            System.out.println("No next page available.");
            return false;
        }
    }

    public void goToNextPage() {
        try {
            WebElement nextCTA = driver.findElement(By.xpath(getPropValue.readPropertiesFile("nextCTA")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", nextCTA);
            nextCTA.click();
            Thread.sleep(3000);
        } catch (NoSuchElementException | InterruptedException | IOException e) {
            System.out.println("Failed to navigate to next page.");
        }
    }

    public void goToBasket() {
        baseMethods base = new baseMethods(driver);
        base.waitScreen();
        base.navigateToSpecificURL("https://www.amazon.eg/-/en/cart?ref_=ewc_gtc");
    }


}
