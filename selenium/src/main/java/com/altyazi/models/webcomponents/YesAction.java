package com.altyazi.models.webcomponents;

import com.altyazi.models.Browser;
import com.altyazi.models.WebComponent;
import org.openqa.selenium.By;


public class YesAction extends Action {

    @Override
    public void execIn(WebComponent component) {
        Browser browser = component.browser();
        browser.clickTo(By.xpath(component.getXpath() + "//button/span[contains(text(),'Evet') or contains(text(), 'Yes')]/.."));
    }
}