package com.altyazi.models;

import com.altyazi.models.users.Visitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Cookie;

import java.util.Map;
import java.util.Set;

public abstract class Page<V extends Visitor> extends WebComponent {

    private static final Logger logger = LogManager.getLogger(Page.class);

    protected String url;

    protected Popup popup;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public boolean login() {
        throw new IllegalStateException("Page doesn't support login activity");
    }

    public boolean logout() {
        throw new IllegalStateException("Page doesn't support logout activity");
    }

    public String url() {
        return this.url;
    }

    public String currentUrl() {
        return this.browser.currentURL();
    }

    public Popup currentPopup() {
        return this.popup;
    }

    public <R extends DataTable> R search() {
        throw new IllegalStateException("Page doesn't support search activity");
    }

    public <R extends DataTable> R results() {
        throw new IllegalStateException("Page doesn't support data table");
    }

    public void reset() {
        throw new IllegalStateException("Page doesn't have any form to reset");
    }

    public Popup openAddPopup() {
        throw new IllegalStateException("Page doesn't support new record popup");
    }

    public void forgot(String what) {
        throw new IllegalStateException("Page doesn't support forgot " + what);
    }

    public void refresh() {
        browser.refresh();
    }

    @Override
    public void onPopupClose() {
        this.popup = null;
    }

    public Set<Cookie> getCookies() {
        return browser.getCookies();
    }

    public Map getDataLayer() {
        return null;
    }
}
