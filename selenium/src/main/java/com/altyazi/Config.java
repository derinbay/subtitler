package com.altyazi;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;

import java.io.InputStream;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.logging.log4j.LogManager.getLogger;

public class Config {

    public static final int WAITTIME_TIMEOUT = 60;
    public static final int WAITTIME_ELEMENTOCCURENCE = 15;
    public static final int WAITTIME_SMALL = 5;
    public static final int WAITTIME_TOOSMALL = 2;
    public static final Platform platform;
    public static final boolean remote;
    public static final String defaultBrowserName;
    public static final String altyaziOrgUrl;
    public static final String screenshotPath;
    public static final Properties properties;
    public static final int failedTestRetryCount;
    private static final String environment;

    private static final Logger logger = getLogger(Config.class);

    static {
        environment = getEnv();

        remote = isRemote();

        platform = getPlatform();

        defaultBrowserName = getBrowser();

        properties = loadProperties(environment);

        screenshotPath = System.getProperty("user.dir") + "/screenshots/";

        failedTestRetryCount = getFailedTestRetryCount();

        altyaziOrgUrl = properties.getProperty("webdriver.altyaziOrgUrl");
    }

    private static Properties loadProperties(String env) {
        String configFileName = env + "_config.properties";
        InputStream in = ClassLoader.getSystemResourceAsStream(configFileName);
        Properties properties = new Properties();

        try {
            properties.load(in);
        } catch (Exception e) {
            throw new IllegalStateException("Exception on loading {" + configFileName + "} conf file from classpath", e);
        }
        return properties;
    }

    private static String getEnv() {
        String env = System.getProperties().getProperty("selenium_env");

        if (isBlank(env)) {
            throw new IllegalArgumentException("No ENV option is set, please set -Dselenium_env in java");
        }

        return env;
    }

    private static String getBrowser() {
        String browser = System.getProperties().getProperty("browser");

        if (isBlank(browser)) {
            throw new IllegalArgumentException("No browser option is set, please set -Dbrowser in java");
        }

        return browser;
    }

    private static Platform getPlatform() {
        String platformConf = System.getProperties().getProperty("platform");
        boolean isRemote = isRemote();

        if (!isRemote) {
            return null;
        }

        if (isRemote && isBlank(platformConf)) {
            {
                throw new IllegalArgumentException("No PLATFORM option is set. You must define as -Dplatform if remote testing is activated");
            }
        }

        return Platform.valueOf(platformConf.toUpperCase());
    }

    private static boolean isRemote() {
        String remoteValue = System.getProperties().getProperty("remote");

        boolean remote = false;
        if (!isBlank(remoteValue)) {
            remote = Boolean.parseBoolean(remoteValue);
        }
        return remote;
    }

    private static int getFailedTestRetryCount() {
        return Integer.parseInt(System.getProperties().getProperty("retry", "4"));
    }
}
