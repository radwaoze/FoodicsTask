package pages;

import base.baseMethods;
import com.github.javafaker.Faker;
import data_driven.ReadPropertiesFile;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;

public class AmazonCheckoutPage {
    private WebDriver driver;
    public AmazonCheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    public void addNewAddress() throws IOException {
        Faker faker = new Faker();

        String name = faker.name().fullName();
        String number = "01123546784";
        String street = faker.address().streetName();
        String building = String.valueOf(faker.number().numberBetween(1, 100));
        String city = "6th of October City";
        String district = "12th District";

        baseMethods base = new baseMethods(driver);

        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("changeAddress")));
        base.waitScreen();
        base.clickOnElement(By.id(getPropValue.readPropertiesFile("addNewAddress")));

        WebElement fullName = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("fullName")), "visibilityOfElement");
        fullName.click();
        base.enterText(fullName, name);

        WebElement phoneNumber = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("phoneNumber")), "visibilityOfElement");
        phoneNumber.click();
        base.enterText(phoneNumber, number);

        WebElement streetName = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("streetName")), "visibilityOfElement");
        streetName.click();
        base.enterText(streetName, street);

        WebElement buildingNo = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("buildingNo")), "visibilityOfElement");
        buildingNo.click();
        base.enterText(buildingNo, building);

        WebElement cityName = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("districtName")), "visibilityOfElement");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cityName);
        base.enterText(cityName, city);

        WebElement districtName = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("districtName")), "visibilityOfElement");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", districtName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", districtName, district);

        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("addressType")));

        WebElement useThisAddressBtn = base.waitExpectedCondition(
                By.xpath("useThisAddressCTA"),
                "elementToBeClickable"
        );
        useThisAddressBtn.click();

        System.out.println("Generated Address:");
        System.out.println("Name: " + name);
        System.out.println("Phone: " + number);
        System.out.println("Street: " + street);
        System.out.println("Building No: " + building);
        System.out.println("City: " + city);
        System.out.println("District: " + district);
    }

    public void addNewCreditCard() throws IOException {
        baseMethods base = new baseMethods(driver);
        Faker faker = new Faker();

        String name = faker.name().fullName();
        String card = faker.finance().creditCard();

        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("changePayment")));
        base.waitScreen();
        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("addPaymentCard")));
        WebElement cardNumber = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("cardNumber")), "visibilityOfElement");
        base.enterText(cardNumber, card);

        WebElement cardName = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("cardName")), "visibilityOfElement");
        base.enterText(cardName, name);

        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("expireYearDropdown")));
        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("expireYear")));
        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("addCardCTA")));
        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("saveCTA")));
    }

    public String getTotalOrder() throws IOException {
        baseMethods base = new baseMethods(driver);
        base.waitScreen();

        return new baseMethods(driver).getPresentedText(By.xpath(getPropValue.readPropertiesFile("orderTotal")));
    }

    public String getFreeDeliveryText() throws IOException {
        baseMethods base = new baseMethods(driver);
        base.waitScreen();

        return new baseMethods(driver).getPresentedText(By.xpath(getPropValue.readPropertiesFile("freeDelivery")));
    }
}
