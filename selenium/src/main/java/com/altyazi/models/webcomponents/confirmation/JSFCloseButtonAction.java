package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class JSFCloseButtonAction extends ConfirmationAction {

    @Override
    public void exec(Browser browser) {
        browser.clickTo(browser.presenceWait(15, By.xpath("//*[@id='orderShippingHowToDialogId']//span[contains(text(),'Kapat') or contains(text(),'Close')]/..")));
    }
}
