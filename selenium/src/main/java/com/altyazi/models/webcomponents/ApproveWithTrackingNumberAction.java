package com.altyazi.models.webcomponents;

import com.altyazi.models.WebComponent;
import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class ApproveWithTrackingNumberAction extends Action {

    @Override
    public void execIn(WebComponent component) {
        Browser browser = component.browser();
        browser.clickTo(browser.findElement(By.xpath(component.getXpath() + "//button/span[text()='Approve' or contains(text(), 'Onayla')]/..")));
        browser.clickTo(browser.findElement(By.xpath(".//span[text()='Takip Numarası İle']")));
    }
}
