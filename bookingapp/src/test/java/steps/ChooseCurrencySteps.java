package steps;

import static utils.BaseWebPage.getDriver;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import pages.HomePage;

public class ChooseCurrencySteps {
    private HomePage homePage = new HomePage(getDriver());

    @When("I choose my {string} currency")
    public void iChooseMyCurrency(String currency) {
    homePage.chooseCurrency().chooseCurrency(currency);
    }

    @Then("I can see {string} currency symbol {string} in header section")
    public void iCanSeeCurrencySymbolInHeaderSection(String currency, String symbol) {
        Assert.assertTrue("Currency Symbol should be displayed " + symbol + "",
            homePage.chooseCurrency().getCurrencySymbol(currency).contains(symbol));
    }
}
