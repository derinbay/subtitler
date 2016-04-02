package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class JSFNoAction extends ConfirmationAction {

    @Override
    public void exec(Browser browser) {
        browser.clickTo(By.xpath("//button/span[contains(text(),'No') or contains(text(),'Hayır')]/.."));
    }
}
