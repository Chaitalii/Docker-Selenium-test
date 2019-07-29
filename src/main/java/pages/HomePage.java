package pages;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.collect.Ordering;
import utilities.PropertyLoader;
import utilities.Utils;

public class HomePage {

	private WebDriver driver;
	private WebDriverWait wait;

	String destCountry = PropertyLoader.loadProperty("dest.country");
	String destCity = PropertyLoader.loadProperty("dest.city");

	@FindBy(xpath = "//div[@class=\"search\"]/input[@type=\"text\"")
	private WebElement search_box;

	@FindBy(xpath = "//div[@class=\"close\"]")
	private WebElement search_button;

	@FindBy(xpath = "//h1")
	private WebElement searchResult;

	@FindBy(xpath = "//a[text()='Destinations']")
	private WebElement destinations;

	@FindBys(@FindBy(xpath = "//div[@class=\"list\"]//li"))
	private List<WebElement> displayedPackages;

	@FindBy(name = "sort")
	private WebElement sortingDropDown;

	By country = By.xpath("//li[a[text()='Destinations']]//a[contains(@href,'" + destCountry + "')]");
	By city = By.xpath("//ul[@class='top']/li[@data-type='" + destCountry + "']//li/a[text()='" + destCity + "']");

	@FindBy(xpath = "//div[@class='header']//h1")
	private WebElement countryHeader;

	@FindBys(@FindBy(xpath = "//div[contains(@class, 'pc saving')]/div"))
	private List<WebElement> savingsAmount;

	@FindBy(xpath = "//li[@class='dropdown js-profile profile']")
	private WebElement profile;

	@FindBy(xpath = "//a[contains(text(), 'Log In')]")
	private WebElement loginIcon;

	@FindBy(xpath = "//a[text()='Deals']")
	private WebElement deals;

	@FindBy(xpath = "//div[@class='c']/h1")
	private WebElement lastMinuteDeals;

	@FindBy(xpath = "//div[@class='c']/div[@class='text']")
	private WebElement lastMinuteDealMessage;

	@FindBy(xpath = "//a[contains(text(), 'View tour')]")
	private WebElement viewTour;

	@FindBy(xpath = "//a[contains(text(), 'Check availability')]")
	private WebElement availabilityButton;

	@FindBy(xpath = "//a[contains(text(), 'Ask a question')]")
	private WebElement askQuestion;

	@FindBy(xpath = "//div[h5[contains(text(), 'Departure date')]]//li[1]")
	private WebElement firstDepartureMonth;

	@FindBys(@FindBy(xpath = "//div[h5[contains(text(), 'Departure date')]]//li"))
	private List<WebElement> allMonths;

	@FindBy(xpath = "//div[h5[contains(text(), 'Departure date')]]//a[contains(text(), 'Show more')]")
	private WebElement showMoreDate;

	@FindBy(xpath = "//li[div[div[a[contains(text(), 'View tour')]]]][1]//h4")
	private WebElement tourName;

	@FindBy(xpath = "//h1[@data-block-type='Tour name']")
	private WebElement actualTourName;

	@FindBy(xpath = "//div[contains(text(), 'Secure your getaway')]")
	private WebElement secureGetaway;

	@FindBy(xpath = "//div[@class='collapse-header']")
	private WebElement closePopup;

	WebElement[] xpathElementsInViewTourPage;

	Utils util = new Utils(driver);
	Calendar now = Calendar.getInstance();

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 30);
		PageFactory.initElements(driver, this);
		xpathElementsInViewTourPage = new WebElement[] { availabilityButton, askQuestion };

	}

	public void goTo(String url) {
		this.driver.get(url);
		System.out.println("The page title is :" + this.driver.getCurrentUrl());
	}

	public void searchToursAndTrips(String city) throws InterruptedException {
		this.util.wait_explicit_till_element_Displayed(search_box);
		this.util.enterText(search_box, city);
		util.click(search_button);
		util.validateText(searchResult, city);
	}

	public void searchHolidayPackages() throws InterruptedException {
		util.click(destinations);
		util.verify_Element_displayed(country);

		util.click(city);
	}

	public void closePopup() {
		try {
			if (closePopup.isDisplayed())
				closePopup.click();
		} catch (Exception e) {
			System.out.println("May be the popup is closed already or got hidden");

		}
	}

	public void checkDefaultSorting() {
		String selectedvalue = util.fetchSelectedOption(sortingDropDown);
		System.out.println("selected value is:" + selectedvalue);
		assertEquals(selectedvalue, "Popularity: Most popular first", "Packages are not sorted  based on popularity");

	}

	public void fetchHolidayPackages() {
		List<WebElement> list = displayedPackages;
		if (list.size() < 1) {
			System.out.println("At least One Package should be avaialble for available countries");
			Assert.fail();
		} else {
			System.out.println("Multiple packages are found for selected country!!!Enjoy your vacation");
		}
	}

	public void verifydisplayedTrips() {
		String heading = util.get_Element_Text(countryHeader);
		if (heading.contains(destCity)) {
			System.out.println("Tours and Trips are correctly displayed for selected city");

		} else {
			System.out.println("Tours and Trips are not displayed as per selected city");
			Assert.fail();
		}
	}

	public void sortToursBySelection(String sortBy) throws InterruptedException {
		Select sel = new Select(util.getElement(sortingDropDown));
		sel.selectByValue(sortBy);
		util.waitForPageLoad();
		Thread.sleep(3000);
	}

	@SuppressWarnings("unchecked")
	public void verifypackagesBySortedSavings() {
		List<WebElement> list = savingsAmount;
		boolean sorted = util.verifySortedList(list);
		org.testng.Assert.assertEquals(sorted, true, "Savings are not displayed in Order");

	}

	public void clickOnLogin() {
		util.click(profile);
		util.click(loginIcon);
	}

	public void clickOnDeals() {
		util.hoverOverAndClick(deals);
	}

	public void verifyLastMinutedeals() {
		String actualHeading = util.get_Element_Text(lastMinuteDeals);
		org.testng.Assert.assertEquals(actualHeading, "Last Minute Tour Deals", "Text is not as expected");
		String actualMessage = util.get_Element_Text(lastMinuteDealMessage);
		org.testng.Assert.assertEquals(actualMessage.contains("check out these last minute travel deals"), true);
	}

	public void verifyViewTour() throws InterruptedException {
		util.verify_Element_displayed(viewTour);
		String nameOfTourInPage1 = util.get_Element_Text(tourName);
		util.click(viewTour);
		util.switchWindow();
		String tourNameInPage2 = util.get_Element_Text(actualTourName);
		org.testng.Assert.assertEquals(nameOfTourInPage1, tourNameInPage2, "Tour names are not same in both pages");
		util.verifyAllUIElements(xpathElementsInViewTourPage);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void verifyFirstDepartureMonth() {

		util.scroll(350, showMoreDate);
		util.click(showMoreDate);
		String monthStart = util.get_Element_Text(firstDepartureMonth).substring(0, 3);
		String currentMonth = new SimpleDateFormat("MMM").format(now.getTime());
		int month = now.get(Calendar.MONTH) + 1;
		org.testng.Assert.assertEquals(monthStart, currentMonth, "Current month is not displayed as first month");
	}

	public void verifyPastDatesNotDisplayed() {
		LocalDate date = LocalDate.now();
		@SuppressWarnings("rawtypes")
		List<WebElement> displayedMonths = util.findElements(allMonths);
		List<String> months = new ArrayList();

		for (WebElement wel : displayedMonths) {
			String str = wel.getText();
			months.add(str);
		}
		// months.add("Mar 2019");

		System.out.println("Months displayed in website are  :" + months);

		List<String> prevMonths = new ArrayList();
		int month = now.get(Calendar.MONTH) + 1;
		for (int i = 1; i < month; i++) {
			LocalDate earlier = date.minusMonths(i);
			String monthPrev = earlier.getMonth().toString().substring(0, 3).toLowerCase();
			String firstCap = monthPrev.substring(0, 1).toUpperCase() + monthPrev.substring(1);
			prevMonths.add(firstCap + " " + now.get(Calendar.YEAR));
		}
		System.out.println("Previous months are " + prevMonths);
		prevMonths.retainAll(months);
		int count = prevMonths.size();
		System.out.println("Common Months are:" + prevMonths + "and the count is ::" + count);
		if (count != 0) {
			Assert.fail("Past months are also displayed in the website");
		}
		//
	}
}
