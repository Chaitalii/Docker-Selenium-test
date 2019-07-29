package pages;

import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.Browser;

public class HomePageTest extends TestBase {

	HomePage obj_HomePage;
	// = PageFactory.initElements(Browser.driver, HomePage.class) ;

	// This test is to verify first departure date displayed in the list is same
	// as current month and no past date is displayed under departure date
	// selection.
	 @Test
	public void verifyDepartureDates() throws InterruptedException {
		obj_HomePage = new HomePage(Browser.driver);
		obj_HomePage.verifyFirstDepartureMonth();
		obj_HomePage.verifyPastDatesNotDisplayed();

	}

	// This test is to verify, on clicking View Tour button, user is navigated
	// to a page related to the clicked tour and all the supplied elements.
	// The elements that are intended to be verified can be added to the list-
	// xpathElementsInViewTourPage(mentioned in homepage). If any missing
	// elements would fail the testcase.
	 @Test
	public void verifyViewTour() throws InterruptedException {
		obj_HomePage = new HomePage(Browser.driver);
		obj_HomePage.verifyViewTour();

	}

	// This test is to make sure Last Minute deals are only selected (and not
	// any other deals)when Last Minute Deals category is selected
	 @Test
	public void verifyLastMinuteDeals() {
		obj_HomePage = new HomePage(Browser.driver);
		obj_HomePage.clickOnDeals();
		obj_HomePage.verifyLastMinutedeals();
	}

	// this test is to amke sure user can sort the tours by deals and after the
	// sorting, tours are displayed as per the correct sorting order of savings.
//	 @Test
	public void verifyPackagesAreDisplayedByDeals() throws InterruptedException {
		obj_HomePage = new HomePage(Browser.driver);
		obj_HomePage.sortToursBySelection("deals");
		obj_HomePage.verifypackagesBySortedSavings();
	}

	@Test
	// this test is to verify tours are sorted by popularity by default and at
	// least one tour is available for the countries listed in the website
	public void searchPackagesForAvailableCountries() throws InterruptedException {
		obj_HomePage = new HomePage(Browser.driver);
		obj_HomePage.searchHolidayPackages();
		obj_HomePage.verifydisplayedTrips();
		obj_HomePage.fetchHolidayPackages();

	}

	 @Test
	// this test is to make sure tours and trips are displayed as per the
	// country selected by the user
	public void verifyToursAndTripsAsPerSelection() throws InterruptedException {
		obj_HomePage = new HomePage(Browser.driver);
		obj_HomePage.closePopup();
		obj_HomePage.searchHolidayPackages();
		obj_HomePage.verifydisplayedTrips();
	}
}
