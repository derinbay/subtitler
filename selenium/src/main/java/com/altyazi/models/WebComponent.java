package com.altyazi.models;

import com.altyazi.models.webcomponents.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class WebComponent {

    private static final Logger logger = LogManager.getLogger(WebComponent.class);

    protected Browser browser;
    protected String id;
    protected String xpath;

    public Browser browser() {
        return this.browser;
    }

    public String getId() {
        return id;
    }

    public String getXpath() {
        String localXpath = xpath;

        if (localXpath == null && id != null) {
            localXpath = "//*[@id='" + id + "']";
        }

        return localXpath;
    }

    public void run(Action action) {
        action.execIn(this);
    }

    public void onPopupClose() {
//        throw new IllegalStateException("This web component {"+this.getClass()+"} is not a Popup Opener.");
    }
}
