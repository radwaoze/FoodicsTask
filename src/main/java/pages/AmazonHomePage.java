package pages;

import base.baseMethods;
import data_driven.ReadPropertiesFile;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.IOException;

public class AmazonHomePage {
    private WebDriver driver;

    public AmazonHomePage(WebDriver driver) {
        this.driver = driver;
    }

    static ReadPropertiesFile getPropValue = new ReadPropertiesFile();

    public void navigateToVideoGames() throws IOException {
        baseMethods base = new baseMethods(driver);

        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("allMenu")));
        base.clickOnElement(By.xpath(getPropValue.readPropertiesFile("seeAll")));


        WebElement videoGames = base.waitExpectedCondition(By.xpath(getPropValue.
                readPropertiesFile("videoGames")), "visibilityOfElement");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", videoGames);
        videoGames.click();

        base.waitScreen();
        WebElement allVideoGames = base.waitExpectedCondition(By.cssSelector(getPropValue.
                readPropertiesFile("allVideoGames")), "visibilityOfElement");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", allVideoGames);

    }
}
