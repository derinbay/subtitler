package com.altyazi.models.browser;

import com.altyazi.models.Browser;
import com.altyazi.Config;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Firefox extends Browser {
    @Override
    protected void initInGrid() {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("firefox");
        capability.setPlatform(Config.platform);
        webDriver = RemoteWebDrive.create(capability);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Override
    protected void initInLocal() {
        FirefoxBinary binary = new FirefoxBinary();

        String currentPath = this.getClass().getClassLoader().getResource("").getPath();
        String firefoxProfilePath = currentPath + "/firefoxprofile";

        File firefoxProfileFolder = new File(firefoxProfilePath);
        FirefoxProfile profile = new FirefoxProfile(firefoxProfileFolder);
        profile.setAcceptUntrustedCertificates(true);

        webDriver = new FirefoxDriver(profile);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
    }
}