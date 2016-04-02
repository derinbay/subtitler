package com.altyazi.models.webcomponents;

import com.altyazi.models.WebComponent;
import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class SendAction extends Action {

    @Override
    public void execIn(WebComponent component) {
        Browser browser = component.browser();
        browser.clickTo(By.xpath(component.getXpath() + "//button/span[contains(text(),'Gönder') or contains(text(), 'gönder')]/.."));
    }
}
