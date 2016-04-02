package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class MobileYesAction extends ConfirmationAction {

    private static final Logger logger = LogManager.getLogger(MobileYesAction.class);

    @Override
    public void exec(Browser browser) {
        browser.clickTo(browser.presenceWait(5, By.xpath("//div[contains(@class,'popupWrapper')]//div[@class='buttons']/a")));
    }
}
