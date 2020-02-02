package steps;

import static utils.BaseWebPage.getDriver;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pages.HomePage;

public class CarHireSteps {

    private HomePage homePage = new HomePage(getDriver());

    @When("I navigate to care hire rental page")
    public void iNavigateToCareHireRentalPage() {
        homePage.carHire().navigateToCarRentals();
        Assert.assertTrue("Car rental page should be displayed", homePage.carHire().verifyCarHirePageLoaded());
    }

    @And("I search car hire for {string} location")
    public void iSearchCarHireForLocation(String location) {
        homePage.carHire().returnToSameLocation(location);
    }

    @And("I select pick-up {string} HRS {string} MINS")
    public void iSelectPickUpHRSMINS(String pickUpHrs, String pickUpMins) {
        homePage.carHire().selectPickUpTime(pickUpHrs,pickUpMins);
    }

    @And("I select drop-off {string} HRS {string} MINS")
    public void iSelectDropOffHRSMINS(String dropOffHrs, String dropOffMins) {
        homePage.carHire().selectDropTime(dropOffHrs,dropOffMins);
    }

    @Then("I can see car hire result for {string} location")
    public void iCanSeeCarHireResultForLocation(String location) {
        homePage.carHire().search();
        Assert.assertTrue("Car Pick up Hire search results should be displayed",
            homePage.carHire().searchResults().getPickUpCity().contains(location));
        Assert.assertTrue("Car Drop off Hire search results should be displayed",
            homePage.carHire().searchResults().getDropOffCity().contains(location));
    }


}
