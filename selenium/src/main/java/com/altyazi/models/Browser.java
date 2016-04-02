package com.altyazi.models;

import com.altyazi.Config;
import com.altyazi.models.browser.Browsers;
import com.altyazi.models.users.Visitor;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public abstract class Browser {

    private static final Logger logger = LogManager.getLogger(Browser.class);

    protected WebDriver webDriver;
    protected HtmlUnitDriver unitDriver;
    protected ArrayList<File> exceptionScreenShots = new ArrayList<>();
    private Page page;
    private Visitor visitor;

    private boolean alreadyInitialized;

    public static Browser openThe(Page page, Set<Cookie> cookies) {
        return Browsers.runDefault().open(page, cookies);
    }

    private static ExpectedCondition<List<WebElement>> visibilityOfAllElementsLocatedByIn(
            final By locator, final WebElement parent) {
        return new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements;
                if (parent != null) {
                    elements = parent.findElements(locator);
                } else {
                    elements = driver.findElements(locator);
                }
                for (WebElement element : elements) {
                    if (!element.isDisplayed()) {
                        return null;
                    }
                }
                return elements.size() > 0 ? elements : null;
            }

            @Override
            public String toString() {
                return "visibility of all elements located by " + locator;
            }
        };
    }

    public void maximizeWindow() {
        webDriver.manage().window().maximize();
    }

    public void resizeWindow(int x, int y) {
        webDriver.manage().window().setSize(new Dimension(x, y));
    }

    protected abstract void initInGrid();

    protected abstract void initInLocal();

    public final Browser byMe(Visitor visitor) {
        this.visitor = visitor;
        return this;
    }

    private Browser open(Page page, Set<Cookie> cookies) {
        boolean isRemoteBrowser = Config.remote;
        if (!alreadyInitialized) {
            if (isRemoteBrowser) {
                logger.info("Open remote browser {" + Config.defaultBrowserName + "} in {" + Config.platform + "} ");
                initInGrid();
            } else {
                logger.info("Open local browser {" + Config.defaultBrowserName + "}");
                initInLocal();
            }
        }

        this.page = page;
        this.page.setBrowser(this);
        webDriver.get(page.url());
        if (webDriver != unitDriver) {
            waitForAjax();
        }

        if (cookies != null) {
            cookies.forEach((cookie) -> webDriver.manage().addCookie(cookie));
        }

        return this;
    }

    public void takeScreenshot(String scrFilename) {
        File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(
                OutputType.FILE);
        File directory = new File(Config.screenshotPath);

        if (!directory.exists())
            directory.mkdir();

        File outputFile = new File(Config.screenshotPath, scrFilename + ".png");

        try {
            FileUtils.copyFile(scrFile, outputFile);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public final Browser changePage(Page page) {
        this.page = page;
        this.page.setBrowser(this);
        return this;
    }

    public <P extends Page> Browser goTo(P page) {
        this.page = page;
        this.page.setBrowser(this);
        webDriver.get(page.url());
        waitForDialog();
        return this;
    }

    public void refresh() {
        webDriver.get(page.url());
        waitForDialog();
    }

    public void refreshCurrentUrl() {
        webDriver.get(currentURL());
        waitForDialog();
    }

    public void rest(String url) {
        webDriver.get(url);
    }

    public void goBack() {
        webDriver.navigate().back();
        waitForDialog();
    }

    public void goForward() {
        webDriver.navigate().forward();
    }

    public String getBrowserTitle() {
        return webDriver.getTitle();
    }

    public final Page page() {
        return this.page;
    }

    public final Visitor visitor() {
        return this.visitor;
    }

    public void loadElements(Object webComponent) {
        PageFactory.initElements(webDriver, webComponent);
    }

    public void loadElements() {
        PageFactory.initElements(webDriver, page);
    }

    public String currentURL() {
        return webDriver.getCurrentUrl();
    }

    public Set<Cookie> closeSoftly() {
        Set<Cookie> cookies = null;
        try {
            cookies = webDriver.manage().getCookies();
            webDriver.close();
        } catch (Exception ex) {
            logger.error("Exception on closing webdriver", ex);
        }
        return cookies;
    }

    public void close() {
        try {
            webDriver.quit();
        } catch (Exception ex) {
            logger.error("Exception on closing webdriver", ex);
        }
    }

    public WebElement findElementSafely(By by) {
        return findElementSafely(by, null);
    }

    public WebElement findElementSafely(By by, WebElement element) {
        try {
            webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
            return findElement(by, element);
        } catch (Exception ex) {
            logger.error("Element not found by {" + by + "} in safely");
        }
        return null;
    }

    public WebElement findElement(By by) {
        return findElement(by, null);
    }

    private WebElement findElement(By by, WebElement element) {
        if (element != null) {
            return element.findElement(by);
        } else {
            return webDriver.findElement(by);
        }
    }

    public List<WebElement> findElements(By by) {
        return webDriver.findElements(by);
    }

    public List<WebElement> findElements(By by, WebElement element) {
        return element.findElements(by);
    }

    public void select(WebElement label, String textToSelect) {
        clickTo(label);
        clickTo(presenceWait(2, By.xpath("//*[contains(@style,'display: block')]//ul/li[contains(text(),\"" + textToSelect.trim() + "\")]")));
    }

    public void type(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
        waitForDialog();
    }

    public void waitForAjax() {
        try {
            WebDriverWait myWait = new WebDriverWait(webDriver, 5);
            ExpectedCondition<Boolean> conditionToCheck = new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver input) {
                    JavascriptExecutor jsDriver = (JavascriptExecutor) webDriver;
                    boolean stillRunningAjax = (Boolean) jsDriver
                            .executeScript("return window.jQuery != undefined && jQuery.active != 0");
                    return !stillRunningAjax;
                }
            };

            myWait.until(conditionToCheck);
        } catch (Throwable ex) {
            logger.warn("Ajax Waiting Time Expired");
        }

//        ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
//        WebDriverWait wait = new WebDriverWait(webDriver, 30);
//        wait.until(pageLoadCondition);
    }

    public void waitForDialog() {
        waitForAjax();
        if (currentURL().contains("qaso.n11.com") || currentURL().contains("qa.bo.n11.com") || currentURL().contains("qa.cc.n11.com")) {
            if (findElements(By.xpath("//div[contains(@id,'dialogLoading')]")).size() > 0) {
                try {
                    int count = 0;
                    while (findElement(By.xpath("//div[contains(@id,'dialogLoading')]")).getAttribute("class").contains("ui-overlay-visible") && count < 50) {
                        waitForAjax();
                        if (count == 49) {
                            logger.warn("DIALOG TIMEOUT");
                        }
                        count++;
                    }
                } catch (Throwable ex) {
                    logger.error(ex);
                }
            }
        }
        if (currentURL().contains("qa.n11.com")) {
            if (isElementPresent(By.id("loadingBar")) && findElement(By.id("loadingBar")).getAttribute("style").contains("width")) {
                try {
                    int count = 0;
                    while (!findElement(By.id("loadingBar")).getAttribute("style").contains("0px") && !findElement(By.id("loadingBar")).getAttribute("style").equals("") && count < 50) {
                        waitForAjax();
                        if (count == 49) {
                            logger.warn("DIALOG TIMEOUT");
                        }
                        count++;
                    }
                } catch (Throwable ex) {
                    logger.error(ex);
                }
            }
        }
        if (currentURL().contains("qamobile.n11.com")) {
            try {
                int count = 0;
                while (isElementPresent(By.className("spinner")) && count < 50) {
                    waitForAjax();
                    if (count == 49) {
                        logger.warn("DIALOG TIMEOUT");
                    }
                    count++;
                }
            } catch (Throwable ex) {
                logger.error(ex);
            }

        }
    }

    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception ex) {
            logger.error("Exception on selenium waiting", ex);
        }
    }

    public void waitMs(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (Exception ex) {
            logger.error("Exception on selenium waiting", ex);
        }
    }

    public void acceptAlert() {
        try {
            Alert alert = webDriver.switchTo().alert();
            alert.accept();
            waitForDialog();
        } catch (NoAlertPresentException ex) {
            logger.error("No alert present! - " + ex);
        }
    }

    public Browser switchToFrame(String frameId) {
        webDriver.switchTo().frame(frameId);
        waitForDialog();

        return this;
    }

    public Browser switchToFrameByClass(String frameClassName) {
        webDriver.switchTo().frame(findElement(By.className(frameClassName)));

        return this;
    }

    public Browser switchToPage() {
        webDriver.switchTo().defaultContent();

        return this;
    }

    public void scrollTo(WebElement element) {
        scrollTo(element.getLocation().x, element.getLocation().y - 100);
    }

    public void scrollTo(int x, int y) {
        js("scrollTo(" + x + "," + y + ");");

        wait(1);
    }

    public void scrollDownInsideOf(WebElement element, int scrollDown) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;

        js.executeScript("arguments[0].scrollTop = arguments[1];", element, scrollDown);
    }

    public Object js(String script) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        Object response = js.executeScript(script);
        waitForDialog();

        return response;
    }

    public String pageSource() {
        return webDriver.getPageSource();
    }

    public void clickTo(By by) {
        clickTo(presenceWaitInSafe(1, by));
    }

    public void clickTo(WebElement element) {
        try {
            element.click();
            waitForDialog();
        } catch (WebDriverException ex) {
            if (ex.getMessage().contains("Other element would receive the click")) {
                WebElement stickyHeader = presenceWaitInSafe(1, By.xpath("//header[@id='header' and @class='sticky animate']"));
                if (stickyHeader != null) {
                    scrollTo(element.getLocation().x, element.getLocation().y - 100);
                }

                WebElement quickCoupon = presenceWaitInSafe(1, By.xpath("//div[@id='quickieLightBox']"));
                if (quickCoupon != null) {
                    quickCoupon.findElement(By.xpath(".//span[@class='closeBtn']")).click();
                    wait(1);
                }
                WebElement todaysDeal = presenceWaitInSafe(1, By.xpath("//div[contains(@class, 'fancybox-segmentify')]"));
                if (todaysDeal != null && todaysDeal.isDisplayed()) {
                    todaysDeal.findElement(By.xpath(".//a[@title='Close']")).click();
                    wait(1);
                }
                element.click();
                waitForDialog();
            } else {
                throw ex;
            }
        }
    }

    public void moveTo(WebElement element) {
        Actions builder = new Actions(webDriver);
        builder.moveToElement(element).perform();
    }

    public Actions actionBuilder() {
        return new Actions(webDriver);
    }

    public WebElement presenceWait(int timeoutInSeconds, By by) {
        return new FluentWait<>(webDriver).
                withTimeout(timeoutInSeconds, TimeUnit.SECONDS).
                pollingEvery(500, TimeUnit.MILLISECONDS).
                ignoring(NotFoundException.class).ignoring(NoSuchElementException.class).
                until(presenceOfElementLocated(by));
    }

    public WebElement visibilityWait(int timeoutInSeconds, By by) {
        return new FluentWait<>(webDriver).
                withTimeout(timeoutInSeconds, TimeUnit.SECONDS).
                pollingEvery(500, TimeUnit.MILLISECONDS).
                ignoring(NotFoundException.class).ignoring(NoSuchElementException.class).
                until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private List<WebElement> visibilitiesWait(int timeoutInSeconds, By by, WebElement element) {
        return new FluentWait<>(webDriver).
                withTimeout(timeoutInSeconds, TimeUnit.SECONDS).
                pollingEvery(500, TimeUnit.MILLISECONDS).
                ignoring(NotFoundException.class).ignoring(NoSuchElementException.class).
                until(visibilityOfAllElementsLocatedByIn(by, element));
    }

    public WebElement presenceWaitInSafe(int timeoutInSeconds, By by) {
        WebElement element = null;
        try {
            element = presenceWait(timeoutInSeconds, by);
        } catch (TimeoutException ex) {
            logger.warn("Ignore TimeoutException: " + ex.getMessage());
        }
        return element;
    }

    public WebElement visibilityWaitInSafe(int timeInSeconds, By by) {
        WebElement element = null;
        try {
            element = visibilityWait(timeInSeconds, by);
        } catch (TimeoutException ex) {
            logger.warn("Ignore TimeoutException: " + ex.getMessage());
        }
        return element;
    }

    public List<WebElement> visibilitiesWaitInSafe(int timeoutInSeconds, By by) {
        return visibilitiesWaitInSafe(timeoutInSeconds, by, null);
    }

    private List<WebElement> visibilitiesWaitInSafe(int timeoutInSeconds, By by, WebElement element) {
        List<WebElement> elements = null;
        try {
            elements = visibilitiesWait(timeoutInSeconds, by, element);
        } catch (TimeoutException ex) {
            logger.warn("Ignore TimeoutException: " + ex.getMessage());
        }
        return elements;
    }

    public boolean isElementPresent(By by) {
        try {
            webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
            webDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(long timeout, By by) {
        try {
            webDriver.manage().timeouts().implicitlyWait(timeout * 1000, TimeUnit.MILLISECONDS);
            webDriver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresentInside(WebElement element, By by) {
        try {
            webDriver.manage().timeouts().implicitlyWait(100, TimeUnit.MILLISECONDS);
            element.findElement(by);
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    public void waitForElementToHide(int seconds, By elementLocator) {
        WebDriverWait wait = new WebDriverWait(webDriver, seconds);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
    }

    public void waitForTextToChange(int seconds, By by, String textBefore) {
        WebDriverWait wait = new WebDriverWait(webDriver, seconds);
        wait.until(ExpectedConditions.invisibilityOfElementWithText(by, textBefore));
    }

    public void waitForWebElementToDisappear(int seconds, WebElement element) {
        WebDriverWait wait = new WebDriverWait(webDriver, seconds);
        wait.until(ExpectedConditions.stalenessOf(element));
    }

    public void waitForPresenceOf(int seconds, By elementLocator) {
        WebDriverWait wait = new WebDriverWait(webDriver, seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
    }

    public void waitForEnableOf(int seconds, By elementLocator) {
        WebDriverWait wait = new WebDriverWait(webDriver, seconds);
        wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
    }

    public void switchWindow() {
        Set<String> handlers = webDriver.getWindowHandles();
        String mainWindow = "";

        if (handlers.size() > 1) {
            mainWindow = webDriver.getWindowHandle();
        }

        for (String handler : handlers) {
            if (!handler.equals(mainWindow)) {
                webDriver.switchTo().window(handler);
                break;
            }
        }
        waitForDialog();
    }

    public void closeAllTabsExceptMainTab() {
        String originalHandle = webDriver.getWindowHandle();
        //TODO: something to open new tabs

        webDriver.getWindowHandles().stream().filter(handle -> !handle.equals(originalHandle)).forEach(handle -> {
            webDriver.switchTo().window(handle);
            webDriver.close();
        });

        webDriver.switchTo().window(originalHandle);
    }

    public int sizeOfWindows() {
        return webDriver.getWindowHandles().size();
    }

    public void clearCookies() {
        webDriver.manage().deleteAllCookies();
    }

    public void deleteCookie(String name) {
        webDriver.manage().deleteCookieNamed(name);
    }

    public Set<Cookie> getCookies() {
        return webDriver.manage().getCookies();
    }

    public String getBrowserName() {
        Capabilities cap = ((RemoteWebDriver) webDriver).getCapabilities();

        return cap.getBrowserName();
    }

    public void triggerField(String field) {
        js("$(\"[id$='" + field + "']\").trigger('blur')");
    }

    public String getElementTextById(String id) {
        return (String) js("return document.getElementById('" + id + "').value");
    }
}
