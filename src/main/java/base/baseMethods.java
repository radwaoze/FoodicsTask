package base;

import data_driven.ReadPropertiesFile;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

public class baseMethods extends browserSetup {

    private WebDriver driver;

    public baseMethods(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitExpectedCondition(By elementPath, String waitCon) {
        WebElement element = null;
        try {
            switch (waitCon) {
                case "presenceOfElement":
                    element = (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.presenceOfElementLocated(elementPath));
                    return element;

                case "elementToBeClickable":
                    element = (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.elementToBeClickable(elementPath));
                    return element;

                case "visibilityOfElement":
                    element = (new WebDriverWait(driver, Duration.ofSeconds(15))).until(ExpectedConditions.visibilityOfElementLocated(elementPath));
                    return element;
            }
        } catch (Exception e) {
            System.out.println("Failed :" + e.getMessage());
            return null;
        }
        return element;
    }

    public void clickOnElement(By locator) {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized!");
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));

        if (element != null) {
            element.click();
            System.out.println("Clicked on element: " + locator);
        } else {
            throw new AssertionError("Failed: Input must be set (Element not found)");
        }
    }


    public String getPresentedText(By path) {
        WebElement element = null;
        try {
            return driver.findElement(path).getText();
        } catch (Exception e) {
            System.out.println("Failed to get text ...");
            return null;
        }
    }

    public void enterText(WebElement textField, String text) {
        try {
            textField.sendKeys(text);
        } catch (Exception e) {
            System.out.println("Failed to enter text in field ...");
        }
    }

    public void navigateToSpecificURL(String websiteUrl)
    {
        try
        {
            driver.get(websiteUrl);
        }
        catch (Exception e)
        {
            driver.navigate().to(websiteUrl);
        }
    }

    public void scrollUpDownPage()
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,300)");
    }

    public String getCurrentURL()
    {
        return driver.getCurrentUrl();
    }
}