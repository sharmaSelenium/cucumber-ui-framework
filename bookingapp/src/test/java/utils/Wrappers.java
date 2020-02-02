package utils;

import java.time.Duration;
import java.util.List;
import org.junit.Assert;
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


    public static void waitForSomeTime(int timeOutInSec) {
        try {
            Thread.sleep(timeOutInSec * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    public static void jsClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static void selectByValue(WebElement eleDropdown, final String value) {
        Select select = new Select(eleDropdown);
        select.selectByValue(value);
    }


    public static void scrollIntoView(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);

    }

    public static void doubleClick(WebElement element) {
        Actions actions = new Actions(driver);
        actions.doubleClick(element).perform();
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

    private static int getImplicitWaitTimeInSec(int timeOutInSec) {
        int waitTime;
        if (timeOutInSec == 0) {
            waitTime = Integer.parseInt(System.getProperty("driverTimeout"));
        } else {
            waitTime = timeOutInSec;
        }
        return waitTime;
    }

    public static List<WebElement> elements(String value) {
        List<WebElement> elements = null;
        elements = driver.findElements(By.xpath(value));
        return elements;
    }

}
