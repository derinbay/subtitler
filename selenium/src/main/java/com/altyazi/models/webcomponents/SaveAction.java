package com.altyazi.models.webcomponents;

import com.altyazi.models.Browser;
import com.altyazi.models.WebComponent;
import org.openqa.selenium.By;

public class SaveAction extends Action {

    @Override
    public void execIn(WebComponent component) {
        Browser browser = component.browser();
        browser.clickTo(browser.findElement(By.xpath(component.getXpath() + "//button/span[text()='Save' or text()='Kaydet']/..")));
    }
}
