package com.altyazi.models.browser;

import com.altyazi.models.Browser;
import com.altyazi.Config;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class InternetExplorer extends Browser {

    @Override
    protected void initInGrid() {
        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
        capability.setBrowserName("internet explorer");
        capability.setPlatform(Config.platform);
        capability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        webDriver = RemoteWebDrive.create(capability);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Override
    protected void initInLocal() {
        webDriver = new InternetExplorerDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
