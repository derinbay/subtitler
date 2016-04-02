package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class StandardYesAction extends ConfirmationAction {

    private static final Logger logger = LogManager.getLogger(StandardYesAction.class);

    @Override
    public void exec(Browser browser) {
        browser.clickTo(browser.presenceWait(5, By.xpath("//*[contains(text(),'Yes') or contains(text(),'Evet') or contains(text(),'Tamam') or contains(text(), 'TAMAM')]")));
    }
}
