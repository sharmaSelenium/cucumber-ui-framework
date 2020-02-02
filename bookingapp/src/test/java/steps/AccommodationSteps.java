package steps;

import static utils.BaseWebPage.getDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import pages.HomePage;

public class AccommodationSteps {

    private HomePage homePage = new HomePage(getDriver());

    @Given("I am on booking.com home page")
    public void iAmOnBookingComHomePage() {
        Assert.assertTrue("Booking.com homepage should be displayed", homePage.verifyHomePageLoaded());
    }

    @When("I search hotel with following criteria")
    public void iSearchHotelWithFollowingCriteria(DataTable dataTable) {
        List<Map<String,String>> data = dataTable.asMaps(String.class,String.class);
        homePage.accommodation().selectDestination(data.get(0).get("city"));
        homePage.accommodation().selectDatePicker();
        homePage.accommodation().selectCheckInDate(Integer.parseInt(data.get(0).get("checkInDaysFromToday")));
        homePage.accommodation().selectCheckoutDate(Integer.parseInt(data.get(0).get("checkoutDaysFromToday")));
        homePage.accommodation().selectRoomsOccupancy();
        homePage.accommodation().selectAdultCount(Integer.parseInt(data.get(0).get("adults")));
        homePage.accommodation().selectChildrenCount((Integer.parseInt(data.get(0).get("children"))));
        homePage.accommodation().selectRoomCount((Integer.parseInt(data.get(0).get("rooms"))));
        homePage.accommodation().search();

    }

    @Then("I can see hotels for {string} destination")
    public void iCanSeeHotelsForDestination(String city) {
        Assert.assertTrue("Accommodation search results should be displayed",
            homePage.accommodation().searchResults().verifyAccommodationSearchResultsLoaded());
        Assert.assertEquals("Expected destination should be " + city + "", city,
            homePage.accommodation().searchResults().getDestinationName());

    }

    @Then("I can see hotel prices in selected {string} currency")
    public void iCanSeeHotelPricesInSelectedCurrency(String currency) {
        Assert.assertTrue("Accommodation search results should be displayed",
            homePage.accommodation().searchResults().verifyAccommodationSearchResultsLoaded());
        Assert.assertTrue("Hotel Prices should be displayed in currency " + currency + "",
            homePage.accommodation().searchResults().verifyHotelPricesInSelectedCurrency(currency));
    }

}
