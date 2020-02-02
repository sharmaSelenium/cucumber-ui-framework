package pages;

import static utils.TestUtil.getAppDataYml;
import static utils.Wrappers.waitUntilClickable;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.accomodation.AccommodationSearch;
import pages.carrentals.CarHireSearch;
import pages.headersection.ChooseCurrency;
import utils.BaseWebPage;

public class HomePage extends BaseWebPage {

    @FindBy(xpath = "//span[text()='Car rentals']")
    private WebElement lnkCarRental;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        BaseWebPage.driver = driver;
    }

    public AccommodationSearch accommodation(){return new AccommodationSearch(driver);}
    public CarHireSearch carHire(){return new CarHireSearch(driver);}
    public ChooseCurrency chooseCurrency(){return new ChooseCurrency(driver);}

    public boolean verifyHomePageLoaded() {
        return driver.getTitle().equals(getAppDataYml("HOME_PAGE_TITLE"));
    }

    public void navigateToCarRentals(){
        waitUntilClickable(lnkCarRental, 0);
        lnkCarRental.click();
    }


}
