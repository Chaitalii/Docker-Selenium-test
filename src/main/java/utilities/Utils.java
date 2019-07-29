package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Utils {

	private WebDriver driver;

	public Utils(WebDriver driver) {
		this.driver = Browser.driver;
		PageFactory.initElements(driver, this);
	}
	// Browser obj= new Browser();

	public void click(By by) {
		driver.findElement(by).click();
	}

	public WebElement getElement(WebElement deals) {
		return deals;
	}

	public WebElement getElement(By deals) {
		return driver.findElement(deals);
	}

	public String getPageTitle() {

		return driver.getTitle();
	}

	public void verify_Element_displayed(WebElement viewTour) throws InterruptedException {

		wait_explicit_till_element_Displayed(viewTour);
		boolean result = viewTour.isDisplayed();
		Assert.assertEquals(result, true, "Element not displayed");

	}

	public void verify_Element_displayed(By viewTour) throws InterruptedException {

		wait_explicit_till_element_Displayed(viewTour);
		boolean result = driver.findElement(viewTour).isDisplayed();
		Assert.assertEquals(result, true, "Element not displayed");

	}

	public void verify_Element_Not_displayed(WebElement by) throws InterruptedException {

		wait_explicit_till_element_Displayed(by);
		boolean result = driver.findElement((By) by).isDisplayed();
		Assert.assertFalse(result, "Element not displayed as expected");

	}

	public void verify_Element_displayed(WebElement by, String msg) {

		wait_explicit_till_element_Displayed(by);
		boolean result = driver.findElement((By) by).isDisplayed();

		Assert.assertEquals(result, true, "Element not displayed. " + msg);

	}

	public String get_Element_Text(WebElement firstDepartureMonth) {

		return firstDepartureMonth.getText();

	}

	public void verify_Element_Text(By by, String text) {

		String strExpected = driver.findElement(by).getText().toLowerCase().trim();
		Assert.assertEquals(strExpected, text.toLowerCase().trim(), "Element text not displayed as expected .Expected: "
				+ text.toLowerCase() + " and Actual is : " + strExpected);

	}

	public String get_Attribute_Value(By by, String strAttribute) {

		String result = driver.findElement(by).getAttribute(strAttribute);
		return result;
	}

	public Date get_Current_Date() {

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// getTime() returns the current date in default time zone
		Date date = calendar.getTime();
		return date;
	}

	public void wait_explicit_till_element_Displayed(WebElement viewTour) {

		WebDriverWait waitnew = new WebDriverWait(driver, 20);
		WebElement element = waitnew.until(ExpectedConditions.visibilityOf(viewTour));

	}

	public void wait_explicit_till_element_Displayed(By viewTour) {

		WebDriverWait waitnew = new WebDriverWait(driver, 20);
		WebElement element = waitnew.until(ExpectedConditions.visibilityOfElementLocated(viewTour));

	}

	public void waitForPageLoad() {
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		if (js.executeScript("return document.readyState").toString().equals("complete")) {
			System.out.println("Page Is loaded.");
			return;
		}
	}

	// *******************************************************//
	public void sendText(WebElement loginUserName, String text) {

		WebElement objInput =  loginUserName;

		objInput.clear();
		objInput.sendKeys(text);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		objInput.sendKeys(Keys.TAB);
	}

	public List get_Element_List(By by) {

		List<WebElement> lisElement = driver.findElements(by);

		return lisElement;
	}

	public void click(WebElement objElement) {

		wait_explicit_till_element_Clickable(objElement);
		objElement.click();
	}

	public void wait_explicit_till_element_Clickable(WebElement objElement) {

		WebDriverWait waitnew = new WebDriverWait(driver, 20);
		waitnew.until(ExpectedConditions.elementToBeClickable(objElement));

		// .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='WebDriver']")));

	}

	public void wait_Specific_Seconds(long sec) {

		try {
			Thread.sleep(sec);
		} catch (Exception e) {

		}
		driver.manage().timeouts().implicitlyWait(sec, TimeUnit.SECONDS);
	}

	public void enterText(WebElement search_box, String text) throws InterruptedException {
		// TODO Auto-generated method stub
		WebElement objInput = driver.findElement((By) search_box);
		objInput.clear();
		objInput.sendKeys(text);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		objInput.sendKeys(Keys.ENTER);

	}

	public void validateText(WebElement searchResult, String validatestring) {

		String actualString = driver.findElement((By) searchResult).getText();
		boolean b = actualString.contains(validatestring);
		Assert.assertEquals(b, true, "Actual is " + b);

	}

	public String fetchSelectedOption(WebElement sortingDropDown) {
		WebElement objInput = sortingDropDown;
		Select sel = new Select(objInput);
		String selectedValue = sel.getFirstSelectedOption().getText();
		return selectedValue;
	}

	public List<WebElement> findElements(List<WebElement> allMonths) {
		// List<WebElement> list= driver.findElements(allMonths);
		List<WebElement> list = allMonths;
		int count = list.size();
		// System.out.println("Number of matching elements found is"+ count);
		return list;
	}

	public void refreshPage() {
		driver.navigate().refresh();
		waitForPageLoad();
	}

	public boolean verifySortedList(List<WebElement> list) {
		List<String> original = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("Discount is :" + list.get(i).getText());
			temp.add(list.get(i).getText());
			original.add(list.get(i).getText());
		}

		Collections.sort(temp, Collections.reverseOrder());
		boolean sorted = temp.equals(original);
		System.out.println("list sorted :" + sorted);
		return sorted;
	}

	public void hoverOverAndClick(WebElement deals) {
		Actions builder = new Actions(driver);
		builder.moveToElement(deals).perform();
		By locator = By.xpath("//a[text()='Last Minute Deals']");
		click(locator);
	}

	public void switchWindow() {
		Set<String> handles = driver.getWindowHandles();
		Iterator<String> it = handles.iterator();
		// iterate through opened windows
		while (it.hasNext()) {
			String parentWindow = it.next();
			String childWindow = it.next();
			driver.switchTo().window(childWindow);
			// driver.switchTo().window(parentWindow);
		}
	}

	public void scroll(int scroll, WebElement showMoreDate) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,450)");
		// js.executeScript("arguments[0].scrollIntoView();", by);
	}

	public void verifyAllUIElements(WebElement[] xpathElementList) {
		int elementCount = 0;
		for (WebElement xpathStr : xpathElementList) {
			try {
				xpathStr.isDisplayed();
				elementCount++;
				System.out.println(xpathStr + "is displayed");
			} catch (Exception e) {
				System.out.println(xpathStr + " is not displayed");
			}
		}
		if (elementCount == xpathElementList.length) {
			Assert.assertTrue(true, "All the elements in the page are verified");
			System.out.println("All the elements in the page are verified");
		} else {
			Assert.fail("One or more elements are missing from the page");
		}

	}
}
