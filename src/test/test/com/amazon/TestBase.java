package com.amazon;

import base.browserSetup;
import data_driven.ReadPropertiesFile;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

public class TestBase {
    protected WebDriver driver;
    browserSetup setup = new browserSetup();

    @BeforeSuite
    @Parameters("browserName")
    public void setup(@Optional("Chrome") String browserName) throws IOException {
        driver = new browserSetup().BrowserName(browserName);
        setup.maximizeScreen();
        System.out.println("The value of driver in TestBase is " + driver);
        final ReadPropertiesFile getPropValue = new ReadPropertiesFile();
        driver.navigate().to(getPropValue.readPropertiesFile("AmazonUrl"));
    }

    @AfterMethod
    public void takeScreenshotOnFailure(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE)
        {
            System.out.println("Result Status is " + result.getStatus());
            TakesScreenshot ts = (TakesScreenshot) driver;
            File screen = ts.getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screen , new File("./screenshots/" + result.getTestName()+ ".png"));
        }
    }

    @AfterSuite
    public void afterSuite() {
        setup.resetCache();
        setup.closeBrowser();
    }
}
