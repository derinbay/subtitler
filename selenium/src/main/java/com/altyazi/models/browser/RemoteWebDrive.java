package com.altyazi.models.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class RemoteWebDrive {
    public static WebDriver create(DesiredCapabilities capability) {
        WebDriver webDriver;
        try {
            webDriver = new RemoteWebDriver(new URL("http://172.20.0.196:4444/wd/hub"), capability);
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return webDriver;
    }
}
