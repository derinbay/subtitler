package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class StandardNoAction extends ConfirmationAction {

    @Override
    public void exec(Browser browser) {
        browser.clickTo(By.xpath("//div[contains(text(),'No') or contains(text(),'Hayır')]"));
    }
}
