package pages.headersection;

import static utils.Wrappers.element;
import static utils.Wrappers.jsClick;
import static utils.Wrappers.waitUntilClickable;
import static utils.Wrappers.waitUntilVisible;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.HomePage;

public class ChooseCurrency extends HomePage {
    @FindBy(xpath = "//a[@data-title='Choose your currency']")
    private WebElement lnkChooseCurrency;

    public ChooseCurrency(WebDriver driver) {
        super(driver);
    }

    public void chooseCurrency(String currency) {
        waitUntilClickable(lnkChooseCurrency, 0);
        lnkChooseCurrency.click();
        waitUntilVisible(element("//a[@data-currency='" + currency + "']/span"), 0);
        waitUntilClickable(element("//a[@data-currency='" + currency + "']/span"), 0);
        jsClick(element("//a[@data-currency='" + currency + "']/span"));
    }

    public String getCurrencySymbol(String selectedCurrency) {
        return element("//input[@value='" + selectedCurrency + "']/../a").getText();
    }
}
