package pages.carrentals;

import static utils.Wrappers.waitUntilVisible;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResults extends CarHireSearch {

    @FindBy(xpath = "//div[@class='ab-SearchSummary_PickUp_City']")
    private WebElement pickUpCity;

    @FindBy(xpath = "//div[@class='ab-SearchSummary_DropOff_City']")
    private WebElement dropOffCity;

    public SearchResults(WebDriver driver) {
        super(driver);
    }

    public String getPickUpCity(){
        waitUntilVisible(pickUpCity,0);
        System.out.println("text is" + pickUpCity.getText());
        return pickUpCity.getText();
    }

    public String getDropOffCity(){
        waitUntilVisible(dropOffCity,0);
        System.out.println("text is" + dropOffCity.getText());
        return dropOffCity.getText();
    }
}
