package com.altyazi.models.browser;

import com.altyazi.models.Browser;
import com.altyazi.Config;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class Safari extends Browser {
    @Override
    protected void initInGrid() {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("safari");
        capability.setPlatform(Config.platform);
        webDriver = RemoteWebDrive.create(capability);
    }

    @Override
    protected void initInLocal() {
        DesiredCapabilities safariCapabilities = DesiredCapabilities.safari();
        safariCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        this.webDriver = new EventFiringWebDriver(new SafariDriver(safariCapabilities));
    }
}
