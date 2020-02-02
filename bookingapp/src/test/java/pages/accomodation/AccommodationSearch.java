package pages.accomodation;

import static utils.TestUtil.getFutureDateInFormat;
import static utils.Wrappers.doubleClick;
import static utils.Wrappers.element;
import static utils.Wrappers.jsClick;
import static utils.Wrappers.waitUntilClickable;
import static utils.Wrappers.waitUntilVisible;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.HomePage;

public class AccommodationSearch extends HomePage {

    @FindBy(xpath = "//span[contains(text(), 'Search')]")
    private WebElement btnSearch;

    @FindBy(id = "ss")
    private WebElement destSearch;

    @FindBy(xpath = "//div[@data-mode='checkin']/div")
    private WebElement checkInDatePicker;

    @FindBy(xpath = "//label[@id='xp__guests__toggle']")
    private WebElement btnGuestCount;

    @FindBy(xpath = "//input[@id='group_adults']")
    private WebElement adults;

    @FindBy(xpath = "//input[@id='group_children']")
    private WebElement children;

    @FindBy(xpath = "//input[@id='no_rooms']")
    private WebElement rooms;

    @FindBy(xpath = "//label[text()='Adults']/../following-sibling::div/button[2]/span")
    private WebElement addAdult;

    @FindBy(xpath = "//label[text()='Adults']/../following-sibling::div/button[1]/span")
    private WebElement removeAdult;

    @FindBy(xpath = "//label[text()='Children']/../following-sibling::div/button[2]/span")
    private WebElement addChildren;

    @FindBy(xpath = "//label[text()='Rooms']/../following-sibling::div/button[2]/span")
    private WebElement addRooms;

    public AccommodationSearch(WebDriver driver) {
        super(driver);
    }

    public SearchResults searchResults(){return new SearchResults(driver);}

    public void selectDestination(String dest) {
        waitUntilClickable(destSearch, 0);
        destSearch.sendKeys(dest);
        waitUntilVisible(element("//span[text()='" + dest + "']") ,0);
        waitUntilClickable(element("//span[text()='" + dest + "']"), 0);
        element("//span[text()='" + dest + "']").click();
    }


    public void search() {
        waitUntilClickable(btnSearch, 0);
        btnSearch.click();
    }

    public void selectDatePicker(){
        waitUntilClickable(checkInDatePicker, 0);
        doubleClick(checkInDatePicker);
    }
    public void selectCheckInDate(int checkInDaysFromToday ){
        String checkInDate = getFutureDateInFormat("yyyy-MM-dd", checkInDaysFromToday);
        waitUntilClickable(element("//td[@data-date='" + checkInDate + "']") ,0);
        jsClick(element("//td[@data-date='" + checkInDate + "']"));

    }

    public void selectCheckoutDate(int checkoutDaysFromToday){
        String checkOutDate = getFutureDateInFormat("yyyy-MM-dd", checkoutDaysFromToday);
        waitUntilClickable(element("//td[@data-date='" + checkOutDate + "']") ,0);
        jsClick(element("//td[@data-date='" + checkOutDate + "']"));
    }

    public void selectRoomsOccupancy(){
        waitUntilClickable(btnGuestCount ,0);
        btnGuestCount.click();
    }

    public void selectAdultCount(int requestedAdultCount){
        int adultsCount = Integer.parseInt(adults.getAttribute("value"));
        if (requestedAdultCount > adultsCount){
            for (int i=0 ; i < requestedAdultCount-adultsCount ; i++){
                addAdult.click();
            }
        }
        else if (requestedAdultCount < adultsCount){
            for (int i= 0 ; i < adultsCount-requestedAdultCount ; i++){
                removeAdult.click();
            }
        }
    }

    public void selectChildrenCount(int requestedChildCount){
        int childCount = Integer.parseInt(children.getAttribute("value"));
        if (requestedChildCount > childCount){
            for (int i=0 ; i < requestedChildCount-childCount ; i++){
                addChildren.click();
            }
        }

    }

    public void selectRoomCount(int requestedRoomCount){
        int roomCount = Integer.parseInt(rooms.getAttribute("value"));
        if (requestedRoomCount > roomCount){
            for (int i=0 ; i < requestedRoomCount-roomCount ; i++){
                addRooms.click();
            }
        }
    }


}
