package pages;

import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.Browser;
import utilities.PropertyLoader;

public class LoginPageTest extends TestBase {
	
	LoginPage obj_LoginPage;
	HomePage obj_HomePage;
	boolean error;
	String userName= PropertyLoader.loadProperty("login.username");
	String pwd=PropertyLoader.loadProperty("login.pwd");
	String invalidUser=PropertyLoader.loadProperty("login.inValidUsername");
	

//	This test is to make sure user is able to login successfully with correct userid and passwowrd. After logging in, same mail id that was used to login should be displayed
//	.This test also covers teh negetive scenario where user enters correct login data and fails to login.
	@Test
	public void verifyLoginToTourRader() throws InterruptedException{
		obj_HomePage = new HomePage(Browser.driver);
		obj_LoginPage= new LoginPage(Browser.driver);
		obj_HomePage.clickOnLogin();
		obj_LoginPage.loginToTourRader(userName, pwd);
		error=obj_LoginPage.checkErrorMessage();
		if(error){
			System.out.println("User is not able to login with valid credentials.");
			Assert.fail("User is not able to login with valid credentials.");
		}else{
		obj_LoginPage.verifyLoggedInUser();
		}
	}
	
	
	//This test is to amke sure user can't login with invalid credentials. Login details are supplied from application.properties file
	@Test
	public void verifyInvalidLogin() throws InterruptedException{
		obj_HomePage = new HomePage(Browser.driver);
		obj_LoginPage= new LoginPage(Browser.driver);
		obj_HomePage.clickOnLogin();
		obj_LoginPage.loginToTourRader(invalidUser, pwd);
		error=obj_LoginPage.checkErrorMessage();
		if(!error){
			System.out.println("User is  able to login with invalid credentials.");
			Assert.fail("User is able  login with invalid credentials.");
		}else{
		System.out.println("Uer is NOT able to login with invalid credentials.");
		}
	}
	
}
