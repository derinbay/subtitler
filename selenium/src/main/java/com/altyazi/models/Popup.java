package com.altyazi.models;

import com.altyazi.models.webcomponents.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class Popup extends WebComponent {

    private static final Logger logger = LogManager.getLogger(Popup.class);

    protected WebComponent opener;

    public Popup fill(FormFiller formFiller) {
        formFiller.fill(this);
        return this;
    }

    public WebComponent answer() {
        run(Action.answer());
        opener.onPopupClose();
        return opener;
    }

    public WebComponent send() {
        run(Action.send());
        opener.onPopupClose();
        return opener;
    }

    public WebComponent save() {
        run(Action.save());
        opener.onPopupClose();
        return opener;
    }

    public WebComponent add() {
        run(Action.add());
        opener.onPopupClose();
        return opener;
    }

    public WebComponent cancel() {
        run(Action.cancel());
        opener.onPopupClose();
        return opener;
    }

    public WebComponent yes() {
        run(Action.yes());
        opener.onPopupClose();
        return opener;
    }

    public boolean isDisplayed() {
        WebElement popup = browser.presenceWaitInSafe(5, By.xpath("//*[@id='" + id + "']"));
        if (popup == null) {
            return false;
        } else {
            return popup.isDisplayed();
        }
    }

    public boolean isVisible() {
        WebElement popup = browser.visibilityWaitInSafe(0, By.xpath("//*[@id='" + id + "']"));
        return popup != null;
    }

    public String text() {
        return browser.findElement(By.xpath("//*[@id='" + id + "']")).getText();
    }

    public <R extends DataTable> R search() {
        throw new IllegalStateException("Page doesn't support search activity");
    }
}
