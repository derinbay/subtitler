package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class JSFCloseAction extends ConfirmationAction {
    @Override
    public void exec(Browser browser) {
        browser.clickTo(browser.presenceWait(60, By.xpath("//div[@class='lightBox']//span[@class='closeBtn']")));
        browser.waitForElementToHide(30, By.xpath("//div[@class='lightBox']//span[@class='closeBtn']"));
        browser.waitForDialog();
    }
}
