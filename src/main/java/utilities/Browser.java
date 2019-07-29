package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import webdriver.WebDriverFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/*
 * Bean representing a browser. It contains name, version and platform fields.
 */
public class Browser {

	public static String baseUrl = PropertyLoader.loadProperty("site.url");
	public static String BrowserName = PropertyLoader.loadProperty("browser.name");
	// private static String BrowserVersion =
	// PropertyLoader.loadProperty("browser.version");
	public static WebDriver driver;
	

	public static void Initialize() throws MalformedURLException {
		String host=null;
		if (System.getProperty("HOST") == null) {
			driver = WebDriverFactory.getInstance(BrowserName);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			if (System.getProperty("BROWSER") != null && System.getProperty("BROWSER").equals("firefox")) {
				driver = WebDriverFactory.getInstance("firefox");
			}
		}

		// http://35.246.142.197:4444/grid/console
		// goTo(baseUrl);

		// To work with Grid:
		else {
		
			if (System.getProperty("HUB_HOST") != null) {
				 
				host =System.getProperty("HUB_HOST");
				
			}else{
				host = "localhost";		
	
			    }
			DesiredCapabilities dc = DesiredCapabilities.chrome();

			if (System.getProperty("Browser") != null && System.getProperty("Browser").equals("firefox")) {
				dc = DesiredCapabilities.firefox();

			}
			driver = new RemoteWebDriver(new URL("http://"+host+ ":4445"+"/wd/hub/"), dc);

		}
	}
	  
	public static String getTitle() {
		return driver.getTitle();
	}

	public static WebDriver Driver() {
		return driver;
	}

	public static void close() {
		driver.close();
	}
}

