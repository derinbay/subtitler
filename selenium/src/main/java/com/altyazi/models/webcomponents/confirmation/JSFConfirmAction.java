package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class JSFConfirmAction extends ConfirmationAction {
    @Override
    public void exec(Browser browser) {
        browser.clickTo(browser.presenceWait(5, By.xpath("//*[contains(@class,'btn') and text()='Onayla']")));
    }
}
