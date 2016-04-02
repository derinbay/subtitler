package com.altyazi.models.webcomponents.confirmation;

import com.altyazi.models.Browser;
import org.openqa.selenium.By;

public class JSFContinueAction extends ConfirmationAction {
    @Override
    public void exec(Browser browser) {
        browser.wait(2);
        browser.clickTo(browser.findElement(By.xpath("//div[@class='popupWrapper returnExchangePopup returnRequestedPopup']/div[@class='popupFooter']/a")));
    }
}
