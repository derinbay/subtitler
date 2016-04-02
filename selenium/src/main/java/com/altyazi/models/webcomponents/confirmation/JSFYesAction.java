package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class JSFYesAction extends ConfirmationAction {
    @Override
    public void exec(Browser browser) {
        browser.clickTo(browser.presenceWait(30, By.xpath("//div[contains(@class,'ui-overlay-visible') and contains(@class,'ui-dialog') ]" +
                "//button/span[contains(text(),'Yes') or contains(text(),'Evet')  or contains(text(),'Tamam')]/..")));
    }
}
