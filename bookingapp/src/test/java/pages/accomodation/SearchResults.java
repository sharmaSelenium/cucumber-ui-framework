package pages.accomodation;

import static utils.TestUtil.getAppDataYml;
import static utils.Wrappers.waitUntilVisible;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResults extends AccommodationSearch {

    @FindBy(xpath = "//input[@id='ss']")
    private WebElement destination;

    public SearchResults(WebDriver driver) {
        super(driver);
    }

    public boolean verifyAccommodationSearchResultsLoaded() {
        return driver.getTitle().equals(getAppDataYml("ACCOMMODATION_SEARCH_RESULTS_TITLE"));
    }

    public String getDestinationName(){
        waitUntilVisible(destination,0);
        return destination.getAttribute("value");
    }

}
