package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;

public class JSFConfirmWithShippingInfoAction extends ConfirmationAction {
    @Override
    public void exec(Browser browser) {
        browser.clickTo(By.xpath("//div[@id='tabView:exchangeApproveConfirmationDialog']//label[@id='tabView:shipmentCompany_label']"));
        browser.clickTo(browser.presenceWait(5, By.xpath("//div[@id='tabView:shipmentCompany_panel']//li[3]")));
        browser.wait(1);
        browser.waitForDialog();
        browser.presenceWait(10, By.xpath("//div[@id='tabView:exchangeApproveConfirmationDialog']//input[@id='tabView:trackingNumber']")).sendKeys(RandomStringUtils.randomNumeric(6));
        browser.js("$(\"[id$='tabView:trackingNumber']\").trigger('blur')");
        while (browser.findElement(By.id("tabView:exchangeApproveConfirmationDialogId")).getAttribute("class").contains("ui-overlay-visible")) {
            if (browser.findElement(By.xpath("//div[@id='tabView:exchangeApproveConfirmationDialog']//input[@id='tabView:trackingNumber']")).getAttribute("value").equals("")) {
                browser.findElement(By.xpath("//div[@id='tabView:exchangeApproveConfirmationDialog']//input[@id='tabView:trackingNumber']")).sendKeys(RandomStringUtils.randomNumeric(6));
                browser.waitMs(500);
            }
            browser.clickTo(browser.presenceWait(5, By.xpath("//div[@id='tabView:exchangeApproveConfirmationDialog']//span[text()='Talebi Onayla']/..")));
        }
    }
}
