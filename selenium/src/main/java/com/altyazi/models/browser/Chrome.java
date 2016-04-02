package com.altyazi.models.browser;

import com.altyazi.models.Browser;
import com.altyazi.Config;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class Chrome extends Browser {
    @Override
    protected void initInGrid() {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setPlatform(Config.platform);
        webDriver = RemoteWebDrive.create(capability);
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenResolution = new Dimension((int)
//                toolkit.getScreenSize().getWidth(), (int)
//                toolkit.getScreenSize().getHeight());
//
//        webDriver.manage().window().setSize(screenResolution);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Override
    protected void initInLocal() {
        // Optional, if not specified, WebDriver will search your path for chromedriver.
        System.setProperty("webdriver.chrome.driver", "/opt/selenium/chromedriver");
        webDriver = new ChromeDriver();

//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Dimension screenResolution = new Dimension((int)
//                toolkit.getScreenSize().getWidth(), (int)
//                toolkit.getScreenSize().getHeight());
//
//        webDriver.manage().window().setSize(screenResolution);
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
}
