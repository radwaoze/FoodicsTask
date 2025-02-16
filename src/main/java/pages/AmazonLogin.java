package pages;

import base.baseMethods;
import data_driven.ReadPropertiesFile;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;


public class AmazonLogin {

    private WebDriver driver;

    public AmazonLogin(WebDriver driver) {
        this.driver = driver;
    }

    ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    public void navigateToSignInPage() throws IOException {
        new baseMethods(driver).clickOnElement(By.xpath(getPropValue.readPropertiesFile("signIn")));
    }

    public void loginByUsernameAndPassword(String Username, String Password) throws IOException, InterruptedException {
        baseMethods base = new baseMethods(driver);
        try {
            WebElement emailField = base.waitExpectedCondition(By.xpath(getPropValue.
                    readPropertiesFile("emailField")), "visibilityOfElement");
            emailField.click();
            base.enterText(emailField, Username);
        } catch (ElementClickInterceptedException e) {
            Thread.sleep(1000);
            WebElement emailField = base.waitExpectedCondition(By.xpath(getPropValue.
                    readPropertiesFile("emailField")), "visibilityOfElement");
            emailField.click();
            base.enterText(emailField, Username);
        }

        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("continueCTA")));

        WebElement pwField = base.waitExpectedCondition(By.xpath(getPropValue.
                        readPropertiesFile("pwField")), "visibilityOfElement");
        pwField.click();
        base.enterText(pwField, Password);
    }

    public void clickOnSignInCTA() throws IOException {
        WebElement LoginCTA = driver.findElement(By.id(getPropValue.readPropertiesFile("signInCTA")));
        LoginCTA.click();
    }

}