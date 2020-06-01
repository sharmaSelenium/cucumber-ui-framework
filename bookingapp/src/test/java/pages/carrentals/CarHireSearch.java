package pages.carrentals;

import static utils.TestUtil.getAppDataYml;
import static utils.Wrappers.element;
import static utils.Wrappers.scrollIntoView;
import static utils.Wrappers.selectByValue;
import static utils.Wrappers.waitUntilClickable;
import static utils.Wrappers.waitUntilVisible;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.HomePage;


public class CarHireSearch extends HomePage {

    @FindBy(xpath = "//input[@id='ss_origin']")
    private WebElement pickUpLocation;

    @FindBy(xpath = "//input[@id='ss']")
    private WebElement dropOffLocation;

    @FindBy(xpath = "//div[@data-mode='checkin']/div/button")
    private WebElement calendarCheckIn;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement search;

    @FindBy(xpath = "//select[@name='checkinTime']")
    private WebElement pickUpHrs;

    @FindBy(xpath = "//select[@name='checkinTimeMinutes']")
    private WebElement pickUpMins;

    @FindBy(xpath = "//select[@name='checkoutTime']")
    private WebElement dropOffHrs;

    @FindBy(xpath = "//select[@name='checkoutTimeMinutes']")
    private WebElement dropOffMins;

    public CarHireSearch(WebDriver driver) {
        super(driver);
    }

    public boolean verifyCarHirePageLoaded() {
        return driver.getTitle().equals(getAppDataYml("CAR_HIRE_PAGE_TITLE"));
    }

    public SearchResults searchResults(){return new SearchResults(driver);}

    public void returnToSameLocation(String location){
        waitUntilClickable(pickUpLocation,0);
        pickUpLocation.sendKeys(location);
        waitUntilVisible(element("//span[text()='" + location + "']") ,0);
        waitUntilClickable(element("//span[text()='" + location + "']"), 0);
        element("//span[text()='" + location + "']").click();
    }


    public void selectCalendar(){
        waitUntilClickable(calendarCheckIn,0);
      calendarCheckIn.click();
    }


    public void search(){
        waitUntilClickable(search,0);
        search.click();
    }

    public void selectPickUpTime(String pickUpHours , String pickUpMinutes){
        selectCalendar();
        scrollIntoView(pickUpHrs);
        waitUntilClickable(pickUpHrs,0);
        selectByValue(pickUpHrs, pickUpHours);
        waitUntilClickable(pickUpMins,0);
        selectByValue(pickUpMins, pickUpMinutes);
    }

    public void selectDropTime(String dropOffHours , String dropOffMinutes){
        waitUntilClickable(dropOffHrs,0);
        selectByValue(dropOffHrs, dropOffHours);
        waitUntilClickable(dropOffMins,0);
        selectByValue(dropOffMins, dropOffMinutes);
        Map<String ,HashMap<String,String>> map = new HashMap<>();
        HashMap<String,String> map1 = new HashMap<>();
        map.put("Nitish",map1 );
        map.remove("Nitish");
    }

}
