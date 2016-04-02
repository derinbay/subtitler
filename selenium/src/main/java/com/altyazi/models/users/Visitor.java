package com.altyazi.models.users;

import com.altyazi.URLUtil;
import com.altyazi.models.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import java.util.Set;

public class Visitor<V extends Visitor> implements Account<VisitorPool> {

    protected Browser browser;
    protected V origin;
    private VisitorPool<V> pool;
    private String id;
    private boolean online;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private boolean plus18;
    private String reqId;
    private boolean dontSendPromo;
    private String tcNo;
    private String appKey;
    private String appSecret;

    public Visitor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Visitor aVisitor() {
        return new Visitor("", "");
    }

    public static Visitor aVisitor(String username, String password) {
        return new Visitor(username, password);
    }

    public static Visitor aVisitor(String email) {
        return new Visitor("", "").setEmail(email);
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public void setPool(VisitorPool pool) {
        this.pool = pool;
    }

    public V invalidUsername() {
        this.username = "invalid";
        return (V) this;
    }

    public V invalidPassword() {
        this.password = "invalid";
        return (V) this;
    }

    public <P extends Page> V open(P page) {
        return open(page, null);
    }

    public <P extends Page> V open(P page, Set<Cookie> cookies) {
        this.browser = Browser.openThe(page, cookies).byMe(this);
        this.origin().browser = this.browser;
        return (V) this;
    }

    public <P extends Page> V openAsIPhone6(P page) {
        return openAsIPhone6(page, null);
    }

    public <P extends Page> V openAsIPhone6(P page, Set<Cookie> cookies) {
        this.browser = Browser.openThe(page, cookies).byMe(this);
        this.origin().browser = this.browser;
        this.browser.resizeWindow(375, 667);
        this.browser.changePage(page);
        return (V) this;
    }

    public <P extends Page<V>> V goTo(P page) {
        this.browser.goTo(page);
        return (V) this;
    }

    public V changePage(Page page) {
        this.browser().changePage(page);
        return (V) this;
    }

    public V goBack() {
        this.browser().goBack();
        return (V) this;
    }

    public V goForward() {
        this.browser().goForward();
        return (V) this;
    }

    public V refresh() {
        this.browser.refresh();
        return (V) this;
    }

    public V refreshCurrentUrl() {
        this.browser.refreshCurrentUrl();
        return (V) this;
    }

    public Visitor login() {
        online = browser.page().login();
        return this;
    }

    public Visitor logout() {
        online = !browser.page().logout();
        return this;
    }

    public boolean isOnline() {
        return online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public V setUsername(String username) {
        this.username = username;
        return (V) this;
    }

    public String getPassword() {
        return this.password;
    }

    public V setPassword(String password) {
        this.password = password;
        return (V) this;
    }

    public String getEmail() {
        return this.email;
    }

    public V setEmail(String email) {
        this.email = email;
        return (V) this;
    }

    public String getFullname() {
        return this.fullname;
    }

    public V setFullname(String fullname) {
        this.fullname = fullname;
        return (V) this;
    }

    public Boolean getDontSendPromo() {
        return this.dontSendPromo;
    }

    public V setDontSendPromo(Boolean promo) {
        this.dontSendPromo = promo;
        return (V) this;
    }

    public Browser browser() {
        return browser;
    }

    public Page nowLookingAt() {
        return browser.page();
    }

    public Set<Cookie> closeSoftly() {
        return browser.closeSoftly();
    }

    public void closeBrowser() {
        browser.close();
    }

    public V click(WebElement webElement) {
        browser.clickTo(webElement);
        return (V) this;
    }

    public Visitor reset() {
        browser.page().reset();
        browser.waitForDialog();
        return this;
    }

    public DataTable search(FormFiller formFiller) {
        fill(formFiller);
        DataTable results = browser.page().search();
        return results;
    }

    public Visitor fill(FormFiller formFiller) {
        formFiller.fill(browser.page());
        return this;
    }

    public Popup openAddPopup() {
        Popup popup = browser.page().openAddPopup();
        return popup;
    }

    public Visitor forgot(String what) {
        browser.page().forgot(what);
        return this;
    }

    public V with() {
        throw new IllegalStateException("You can not call Visitor.with() method.");
    }

    public V origin() {
        if (origin == null) {
            return (V) this;
        }
        V orrigin = (V) origin.origin();
        return orrigin;
    }

    @Override
    public void free() {
        V theOrigin = this.origin();
        if (this.pool == null) {
            throw new IllegalStateException("VisitorPool must be set to Visitor on adding pool.");
        }
        this.pool.free(theOrigin);
    }

    public void setPlus18(boolean plus18) {
        this.plus18 = plus18;
    }

    public void loadElements() {
        browser.loadElements();
    }

    public void deleteCookie(String name) {
        browser.deleteCookie(name);
    }

    public boolean isRedirectedTo(Page page) {
        return URLUtil.contains(browser.currentURL(), page.url());
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getTcNo() {
        return tcNo;
    }

    public void setTcNo(String tcNo) {
        this.tcNo = tcNo;
    }
}