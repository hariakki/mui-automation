package infrastructure;

import helpers.ConfigHelper;
import helpers.TestConstantData;
import helpers.WebElementExtension;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.html5.Location;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Kris Ma on 21/05/2017.
 */
public class KAppiumDriver {

    private final AppiumDriver<MobileElement> appiumDriver;
    private final JavascriptExecutor javaScriptExecutorImplementation;
    private final HasInputDevices hasInputDevicesImplementation;
    private final WebElementExtension webElementExtension;
    private Logger logger;

    public KAppiumDriver(AppiumDriver appiumDriver) {

        this.appiumDriver = appiumDriver;
        this.javaScriptExecutorImplementation = (JavascriptExecutor) appiumDriver;
        this.hasInputDevicesImplementation = (HasInputDevices) appiumDriver;
        this.webElementExtension = new WebElementExtension(appiumDriver);

    }

    //boolean hasQuit = false;
// new methods
    public AppiumDriver<MobileElement> getAppiumDriver() {
        return this.appiumDriver;
    }

    public boolean hasQuit() {
        return ((RemoteWebDriver) appiumDriver).getSessionId() == null;
    }

    public Logger setLogger(Logger logger) {
        return this.logger = logger;
    }

    public Logger getLogger() {
        if (logger == null) {
            return LogManager.getLogger();
        }
        return this.logger;
    }

    public void clickButton(KMobileElement webElement) throws InterruptedException {

        try {
            FluentWait fluentWait = new FluentWait(appiumDriver).withTimeout(ConfigHelper.getAttemptCount() * ConfigHelper.getElementWaitTime(), SECONDS).pollingEvery(50, MILLISECONDS);
            // fluentWait.until(ExpectedConditions.visibilityOf(webElement));
            // fluentWait.until(ExpectedConditions.elementToBeClickable(webElement));
            // scrollViewToWebElement(webElement);
            webElement.click();
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = appiumDriver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert data: " + alertText);
                alert.accept();
                clickButton(webElement);
            } catch (NoAlertPresentException e) {
                clickButton(webElement);
            }
        }catch (org.openqa.selenium.StaleElementReferenceException e) {

            getLogger().info(e.toString());
            getLogger().info(webElement.toString());
            //it means it's been clicked
            try {
                webElement.click();
            } catch (Exception e3) {
                logger.info(e3.toString());
                throw e3;
            }

        } catch (org.openqa.selenium.WebDriverException e1) {
            if (e1.toString().contains("Other element would receive the click")) {
                // it means some other ads blocking
                try {
                    logger.info(e1.toString());
                    // scrollViewToWebElement(webElement);
                    appiumDriver.executeScript("arguments[0].click();", webElement);
                } catch (Exception e2) {
                    logger.info(e2.toString());
                    //   clickButton(webElement);//return to the entry
                }
            }
        } catch (Exception e3) {
            getLogger().info(e3.toString());
            getLogger().info(webElement.toString());
            waitForElementVisible(webElement);
            clickButton(webElement);
        }
    }

    public void clickButton(WebElement webElement) throws InterruptedException {

        try {
            FluentWait fluentWait = new FluentWait(appiumDriver).withTimeout(ConfigHelper.getAttemptCount() * ConfigHelper.getElementWaitTime(), SECONDS).pollingEvery(50, MILLISECONDS);
           // fluentWait.until(ExpectedConditions.visibilityOf(webElement));
           // fluentWait.until(ExpectedConditions.elementToBeClickable(webElement));
           // scrollViewToWebElement(webElement);
            webElement.click();
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = appiumDriver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert data: " + alertText);
                alert.accept();
                clickButton(webElement);
            } catch (NoAlertPresentException e) {
                clickButton(webElement);
            }
        }catch (org.openqa.selenium.StaleElementReferenceException e) {

            getLogger().info(e.toString());
            getLogger().info(webElement.toString());
            //it means it's been clicked
            try {
                webElement.click();
            } catch (Exception e3) {
                logger.info(e3.toString());
                throw e3;
            }

        } catch (org.openqa.selenium.WebDriverException e1) {
            if (e1.toString().contains("Other element would receive the click")) {
                // it means some other ads blocking
                try {
                    logger.info(e1.toString());
                   // scrollViewToWebElement(webElement);
                   appiumDriver.executeScript("arguments[0].click();", webElement);
                } catch (Exception e2) {
                    logger.info(e2.toString());
                    //   clickButton(webElement);//return to the entry
                }
            }
        } catch (Exception e3) {
            getLogger().info(e3.toString());
            getLogger().info(webElement.toString());
            waitForElementVisible(webElement);
            clickButton(webElement);
        }
    }

    public boolean waitForElementVisible(WebElement element) {
        try {
            return webElementExtension.waitForElementVisible(element);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementVisible(element);
            }
            throw e;
        }

    }
    public boolean waitForElementVisible(KMobileElement element) {
        try {
            return webElementExtension.waitForElementVisible(element);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementVisible(element);
            }
            throw e;
        }

    }

    public void waitUntilNotMoving(KMobileElement kMobileElement) throws InterruptedException {
        boolean isEqual = false;
        int waitTime = 0;
        while(!isEqual) {
            if(waitTime> ConfigHelper.getPageLoadWaitTime())
            {
                throw new IllegalArgumentException("after "+ ConfigHelper.getPageLoadWaitTime()+ " s ,the target is still moving");
            }
            Point oldPoint = kMobileElement.getMobileElement().getLocation();
            Thread.sleep(1000);
            Point newPoint = kMobileElement.getMobileElement().getLocation();
            isEqual = newPoint.equals(oldPoint);
            waitTime+= 1;
        }
    }
    public void clickButton(String accessibilityId	 ) throws InterruptedException {

        try {
            // scrollViewToWebElement(locator);
            KMobileElement kMobileElement = findElementByAccessibilityId(accessibilityId);
             waitUntilNotMoving(kMobileElement);
            int waitTime = 0;
            while(!kMobileElement.getMobileElement().isEnabled()&& waitTime<ConfigHelper.getElementWaitTime())
            {
                waitTime+= 1000;
            }
            findElementByAccessibilityId(accessibilityId).click();

        } catch (UnhandledAlertException f) {
            try {
                Alert alert = this.switchTo().alert();
                String alertText = alert.getText();
                logger.info("Alert data: " + alertText);
                alert.accept();
                clickButton(accessibilityId);
            } catch (NoAlertPresentException e) {
                clickButton(accessibilityId);
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                getLogger().info(e.toString());
                WebDriverWait wait = new WebDriverWait(appiumDriver, TestConstantData.elementWaitTime);
                //  wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
                clickButton(accessibilityId);
            } catch (Exception e1) {
                getLogger().info(e1.toString());
                clickButton(accessibilityId);//return to the entry
            }
        } catch (Exception e) {
            if (e.toString().contains("Other element would receive the click")) {// it means some other ads blocking
                try {
                    getLogger().info(e.toString());
                    Thread.sleep(2000);
                    // scrollViewToWebElement(locator);
                    appiumDriver.executeScript("arguments[0].click();", appiumDriver.findElementByAccessibilityId(accessibilityId));

                } catch (Exception e1) {
                    getLogger().info(e1.toString());
                    clickButton(accessibilityId);//return to the entry
                }
            }
        }

    }
    public void clickButton(By locator) throws InterruptedException {

        try {
            // scrollViewToWebElement(locator);
            findElement(locator).click();

        } catch (UnhandledAlertException f) {
            try {
                Alert alert = this.switchTo().alert();
                String alertText = alert.getText();
                logger.info("Alert data: " + alertText);
                alert.accept();
                clickButton(locator);
            } catch (NoAlertPresentException e) {
                clickButton(locator);
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                getLogger().info(e.toString());
                WebDriverWait wait = new WebDriverWait(appiumDriver, TestConstantData.elementWaitTime);
              //  wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
                clickButton(locator);
            } catch (Exception e1) {
                getLogger().info(e1.toString());
                clickButton(locator);//return to the entry
            }
        } catch (Exception e) {
            if (e.toString().contains("Other element would receive the click")) {// it means some other ads blocking
                try {
                    getLogger().info(e.toString());
                    Thread.sleep(2000);
                    // scrollViewToWebElement(locator);
                    appiumDriver.executeScript("arguments[0].click();", appiumDriver.findElement(locator));

                } catch (Exception e1) {
                    getLogger().info(e1.toString());
                    clickButton(locator);//return to the entry
                }
            }
        }

    }

    //override methodes
    public List<KMobileElement> findElements(By by) {

        List<MobileElement> mobileElements = this.appiumDriver.findElements(by);
        List<KMobileElement> kMobileElements = new ArrayList<>();
        for (MobileElement mobileElement : mobileElements) {
            kMobileElements.add(new KMobileElement(mobileElement));
        }

        return kMobileElements;

    }

    public KMobileElement findElement(By by) {
        return new KMobileElement(this.appiumDriver.findElement(by));

    }


//
//    public List<KMobileElement> findElements(String by, String using) {
//        return super.findElements(by, using);
//    }
//
//    public List<KMobileElement> findElementsById(String id) {
//        return super.findElementsById(id);
//    }
//
//    public List<KMobileElement> findElementsByLinkText(String using) {
//        return super.findElementsByLinkText(using);
//    }
//
//    public List<KMobileElement> findElementsByPartialLinkText(String using) {
//        return super.findElementsByPartialLinkText(using);
//    }
//
//    public List<KMobileElement> findElementsByTagName(String using) {
//        return super.findElementsByTagName(using);
//    }
//
//    public List<KMobileElement> findElementsByName(String using) {
//        return super.findElementsByName(using);
//    }
//
//    public List<KMobileElement> findElementsByClassName(String using) {
//        return super.findElementsByClassName(using);
//    }
//
//    public List<KMobileElement> findElementsByCssSelector(String using) {
//        return super.findElementsByCssSelector(using);
//    }
//
//    public List<KMobileElement> findElementsByXPath(String using) {
//        return super.findElementsByXPath(using);
//    }

    public WebDriver context(String name) {
        return this.appiumDriver.context(name);
    }

    public Set<String> getContextHandles() {
        return this.appiumDriver.getContextHandles();
    }

    public String getContext() {
        return this.appiumDriver.getContext();
    }

    public DeviceRotation rotation() {
        return this.appiumDriver.rotation();
    }

    public void rotate(DeviceRotation rotation) {
        this.appiumDriver.rotate(rotation);
    }

    public void rotate(ScreenOrientation orientation) {
        this.appiumDriver.rotate(orientation);
    }

    public ScreenOrientation getOrientation() {
        return this.appiumDriver.getOrientation();
    }

    public Location location() {
        return this.appiumDriver.location();
    }

    public void setLocation(Location location) {
        this.appiumDriver.setLocation(location);
    }

    public URL getRemoteAddress() {
        return this.appiumDriver.getRemoteAddress();
    }

    public String getPlatformName() {
        return this.appiumDriver.getPlatformName();
    }

    public String getAutomationName() {
        return this.appiumDriver.getAutomationName();
    }

    public boolean isBrowser() {
        return this.appiumDriver.isBrowser();
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) {
        try {
            return (this.appiumDriver).getScreenshotAs(outputType);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return getScreenshotAs(outputType);
            }
            throw e;
        }
    }

    public WebDriver.TargetLocator switchTo() {
        return this.appiumDriver.switchTo();
    }

    public WebDriver.Options manage() {
        return this.appiumDriver.manage();
    }

    public WebDriver.Navigation navigate() {
        return this.appiumDriver.navigate();
    }

    public boolean checkPresenceOf(By pop) {
        return true;
    }

    public String getTitle() {

        if(this.appiumDriver.getTitle()==null)
        {
            //this is a mobile app rather than a web view
            return "";
        }
        return appiumDriver.getTitle();
    }

    public void quit() {
        this.appiumDriver.quit();
    }

    public void waitForPageLoad() {
        try {
            webElementExtension.waitForPageLoad();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                waitForPageLoad();
            } else {
                throw e;
            }
        }

    }

    public List<KMobileElement>  findElementsByAccessibilityId(String using) {


        List<MobileElement> mobileElements = this.appiumDriver.findElementsByAccessibilityId(using);
        List<KMobileElement> kMobileElements = new ArrayList<>();
        for (MobileElement mobileElement : mobileElements) {
            kMobileElements.add(new KMobileElement(mobileElement));
        }

        return kMobileElements;

    }

    public KMobileElement findElementByAccessibilityId(String using) {


        MobileElement mobileElement = this.appiumDriver.findElementByAccessibilityId(using);
        KMobileElement kMobileElement = new KMobileElement(mobileElement);

        return kMobileElement;

    }

    public boolean waitForElementInvisible(By interstitial) {
        try {
            return webElementExtension.waitForElementInvisible(interstitial);

        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementInvisible(interstitial);
            }
            throw e;
        }
    }

    public boolean waitForElementInvisible(WebElement webElement) {
        try {
            return webElementExtension.waitForElementInvisible(webElement);

        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementInvisible(webElement);
            }
            throw e;
        }
    }
}
