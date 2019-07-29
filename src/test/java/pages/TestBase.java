package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import utilities.Browser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public abstract class TestBase {

    private static final String SCREENSHOT_FOLDER = "target/screenshots/";
    private static final String SCREENSHOT_FORMAT = ".png";

    @BeforeClass
    public void init() throws MalformedURLException {
    	
        Browser.Initialize();
//    	Browser browser= new Browser();
    }
    
    @BeforeMethod
    public void refreshPage(){
    	Browser.driver.get(Browser.baseUrl);
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (Browser.Driver() != null) {
            Browser.Driver().quit();
        }
    }
//    @AfterSuite(alwaysRun = true)
    public void finalTearDown() {
        if (Browser.Driver() != null) {
            Browser.Driver().quit();
        }
    }
    @AfterMethod
    public void setScreenshot(ITestResult result) {
    	
        if (!result.isSuccess()) {
            try {
                WebDriver returned = new Augmenter().augment(Browser.Driver());
                if (returned != null) {
                    File f = ((TakesScreenshot) returned).getScreenshotAs(OutputType.FILE);
                    try {
                        FileUtils.copyFile(f,
                                new File(SCREENSHOT_FOLDER + result.getName() + SCREENSHOT_FORMAT));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (ScreenshotException se) {
                se.printStackTrace();
            }
        }
    }
}
