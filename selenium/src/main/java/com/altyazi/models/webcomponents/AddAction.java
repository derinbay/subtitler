package com.altyazi.models.webcomponents;

import com.altyazi.models.Browser;
import com.altyazi.models.WebComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AddAction extends Action {

    public void execIn(WebComponent component) {
        Browser browser = component.browser();
        WebElement addButton = browser.findElement(By.xpath(component.getXpath() + "//button/span[text()='Add' or contains(text(), 'Ekle')]/.."));
        browser.clickTo(addButton);
    }
}
