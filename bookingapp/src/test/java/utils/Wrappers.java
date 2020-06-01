package utils;

import com.github.javafaker.Faker;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

public class Wrappers extends BaseWebPage {

    public static WebElement element(String value) {
        WebElement element = null;
        element = driver.findElement(By.xpath(value));
        return element;
    }

    public static List<WebElement> elements(String value) {
        return driver.findElements(By.xpath(value));
    }

    public static WebElement element(String type, String value) {
        WebElement element = null;
        String locatorType = type.toLowerCase();
        switch (locatorType) {
            case "id":
                element = driver.findElement(By.id(value));
                break;
            case "xpath":
                element = driver.findElement(By.xpath(value));
                break;
            case "class":
                element = driver.findElement(By.className(value));
                break;
            case "css":
                element = driver.findElement(By.cssSelector(value));
                break;
            default:
                break;
        }
        return element;
    }

    public static List<WebElement> elements(String type, String value) {
        List<WebElement> elements = null;
        String locatorType = type.toLowerCase();
        switch (locatorType) {
            case "id":
                elements = driver.findElements(By.id(value));
                break;
            case "xpath":
                elements = driver.findElements(By.xpath(value));
                break;
            case "class":
                elements = driver.findElements(By.className(value));
                break;
            case "css":
                elements = driver.findElements(By.cssSelector(value));
                break;
            default:
                break;
        }
        return elements;
    }

    public static void waitUntilVisible(WebElement element, int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
            fWait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            Assert.fail("Expected element not displayed in " + waitTime + " seconds");
        }
    }

    public static void doubleClick(WebElement element) {
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
    }

    public static void waitUntilClickable(WebElement element, int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
            fWait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            Assert.fail("Expected element not clickable in " + waitTime + " seconds");
        }
    }

    public static void waitForNotVisible(WebElement element, int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50));
            fWait.until(ExpectedConditions.invisibilityOf(element));
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
        } catch (org.openqa.selenium.TimeoutException e) {
            Assert.fail("Expected element not invisible in " + waitTime + " seconds");
        }
    }

    public static void waitForSomeTime(int timeOutInSec) {
        try {
            Thread.sleep(timeOutInSec * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    public static void waitForTitle(String title, int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50));
            fWait.until(ExpectedConditions.titleIs(title));
        } catch (Exception e) {
            Assert.fail("Expected title not present in " + waitTime + " seconds");
        }
    }

    public static void waitForTextInElement(WebElement element, String text, int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
            fWait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            Assert.fail("Expected text not present in element in" + waitTime + " seconds");
        }
    }

    public static void waitUntilElementSelected(WebElement element, int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
            fWait.until(ExpectedConditions.elementToBeSelected(element));
        } catch (Exception e) {
            Assert.fail("Expected element not selected in" + waitTime + " seconds");
        }
    }

    public static void softWaitUntilVisible(WebElement element, int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
            fWait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception ignored) {
        }
    }

    public static void waitForAlert(int timeOutInSec) {
        int waitTime = getImplicitWaitTimeInSec(timeOutInSec);
        try {
            FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(waitTime))
                .pollingEvery(Duration.ofMillis(50));
            fWait.until(ExpectedConditions.alertIsPresent());
        } catch (Exception ignored) {
        }
    }

    public static void jsClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static void clearAndType(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    public static void clickAndType(WebElement element, String text) {
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    public static void contextClick(WebElement element) {
        Actions action = new Actions(driver);
        action.contextClick(element).build().perform();
    }

    public static void selectByValue(WebElement eleDropdown, final String value) {
        Select select = new Select(eleDropdown);
        select.selectByValue(value);
    }

    public static void selectByVisibleText(WebElement eleDropdown, final String value) {
        Select select = new Select(eleDropdown);
        select.selectByVisibleText(value);
    }

    public static void selectByIndex(WebElement eleDropdown, final String value) {
        Select select = new Select(eleDropdown);
        select.selectByIndex(Integer.parseInt(value));
    }

    public static String getSelectedValue(WebElement eleDropdown) {
        Select select = new Select(eleDropdown);
        return select.getFirstSelectedOption().getAttribute("value");
    }

    public static void scrollIntoView(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);

    }

    public static void moveToElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public static void moveToElementAndClick(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).click().build().perform();
    }

    public static void dragAnddrop(WebElement sourceLocator, WebElement targetLocator) {
        Actions action = new Actions(driver);
        action.dragAndDrop(sourceLocator, targetLocator).build().perform();
    }

    public static void acceptAlertPopUp() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public static void dismissAlertPopUp() {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
    }

    public static void moveSlider(WebElement eleSlider, int value) {
        ((JavascriptExecutor) driver).executeScript("$(arguments[0]).val(" + value + ").change()", eleSlider);
    }

    public static boolean waitForNewWindow(int timeOutInSec) {
        boolean flag = false;
        try {
            Thread.sleep(timeOutInSec);
            Set<String> winId = driver.getWindowHandles();
            if (winId.size() > 1) {
                flag = true;
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static void switchToWindowHandle(String pageTitle) {
        Set<String> windowIterator = driver.getWindowHandles();
        for (String windowHandle : windowIterator) {
            WebDriver windowPopup = driver.switchTo().window(windowHandle);
            if (windowPopup.getTitle().equals(pageTitle)) {
                break;
            }
        }
    }

    public static boolean ifElementPresent(WebElement element) {
        try {
            element.isEnabled();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean ifElementPresent(String xPath) {
        try {
            driver.findElement(By.xpath(xPath));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean ifElementDisplayed(WebElement element) {
        try {
            element.isEnabled();
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static Faker faker() {
        return new Faker();
    }

    private static boolean containsText(String textValue) {
        return driver.findElement(By.tagName("body")).getText().contains(textValue);
    }

    private static int getImplicitWaitTimeInSec(int timeOutInSec) {
        int waitTime;
        if (timeOutInSec == 0) {
            waitTime = Integer.parseInt(System.getProperty("driverTimeout"));
        } else {
            waitTime = timeOutInSec;
        }
        return waitTime;
    }

}
