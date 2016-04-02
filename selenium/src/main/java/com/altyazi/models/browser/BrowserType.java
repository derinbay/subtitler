package com.altyazi.models.browser;

public enum BrowserType {
    INTERNETEXPLORER("iexplorer"),
    CHROME("chrome"),
    FIREFOX("firefox"),
    HTMLUNIT("htmlunit"),
    SAFARI("safari"),
    IOSSAFARI("iossafari"),
    ANDROIDBROWSER("androidbrowser"),
    ANDROIDCHROME("androidchrome"),
    SELENDROID("selendroid");

    private String name;

    BrowserType(String name) {
        this.name = name;
    }

    public static BrowserType byName(String defaultBrowserName) {
        BrowserType found = null;

        for (BrowserType type : values()) {
            if (type.is(defaultBrowserName)) {
                found = type;
                break;
            }
        }

        if (found == null) {
            throw new IllegalArgumentException("Illegal Browser : " + defaultBrowserName);
        }

        return found;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean is(String defaultBrowserName) {
        String lowerCasedTypeName = name.toLowerCase();
        String lowerCasedDefaultName = defaultBrowserName.toLowerCase();
        return lowerCasedTypeName.equals(lowerCasedDefaultName);
    }


}
