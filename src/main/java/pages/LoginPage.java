package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilities.PropertyLoader;
import utilities.Utils;

public class LoginPage {

	
	private WebDriver driver;
	private WebDriverWait wait;
	
	@FindBy(xpath = "//input[contains(@placeholder, 'Enter your Email or Username')]")
	private WebElement loginUserName;

	@FindBy(xpath = "//input[contains(@placeholder, 'Enter password')]")
	private WebElement loginpwd;

	@FindBy(xpath = "//span[text()='Log in']")
	private WebElement loginButton;
	
	@FindBy(xpath = "//a[@class='profile-link']")
	private WebElement profileLink;

	@FindBy(xpath = "//p[@class='ao-profile-top__profile-details-email']")
	private WebElement userEmail;

	@FindBy(xpath = "//div[@id='message-sign-in']")
	private WebElement errorMessage;
	
	
	public LoginPage(WebDriver driver){
		this.driver=driver;
		this.wait= new WebDriverWait(driver, 30);
		PageFactory.initElements(driver, this);
	}
	Utils util = new Utils(driver);
	String wrongLoginMessage = PropertyLoader.loadProperty("errorMessage");

	public void loginToTourRader(String userName, String pwd) {
		util.sendText(loginUserName, userName);
		util.sendText(loginpwd, pwd);
		util.click(loginButton);
		util.waitForPageLoad();
	}

	public void verifyLoggedInUser() throws InterruptedException {

		util.verify_Element_displayed(profileLink);
		util.click(profileLink);
		String user = util.get_Element_Text(userEmail);
		org.testng.Assert.assertEquals(user, PropertyLoader.loadProperty("login.username"));

	}

	public boolean checkErrorMessage() throws InterruptedException {
		util.verify_Element_displayed(errorMessage);
		// util.verify_Element_Not_displayed(errorMessage);
		String actMessage = util.get_Element_Text( errorMessage);
		boolean errorDisplayed = actMessage.contentEquals(PropertyLoader.loadProperty("invalid.login"));
		return errorDisplayed;
	}

}
