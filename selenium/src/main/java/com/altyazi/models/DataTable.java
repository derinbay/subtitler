package com.altyazi.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class DataTable<T extends DataTable> extends WebComponent implements Pageable {

    private static final Logger logger = LogManager.getLogger(DataTable.class);

    protected String tableId;
    protected WebComponent parent;
    protected String rowFilter = "";
    private SearchResult selectedResult;
    private int selectedIndex;
    private WebElement selectedRow;
    private SearchResult backupOfSearchResult;

    public DataTable() {}

    public DataTable(String rowFilter) {
        this.rowFilter = "[" + rowFilter + "]";
    }

    public Paging getPaging() {
        return new Paging(this);
    }

    @Override
    public void goNextPage() {
        getPaging().goNextPage();
    }

    @Override
    public void goPreviousPage() {
        getPaging().goPreviousPage();
    }

    @Override
    public void goPage(int pageNo) {
        getPaging().goPage(pageNo);
    }

    @Override
    public void refresh() {
        selectedIndex = 0;
        selectedResult = null;
        backupOfSearchResult = null;
    }

    public boolean isEmpty() {
        WebElement firstRowInTable = browser.presenceWaitInSafe(10, By.xpath(xpath));
        if (firstRowInTable == null) {
            return false;
        }

        return true;
    }

    public int size() {
        return getRows().size();
    }

    public List<WebElement> getRows() {
        List<WebElement> rows = browser.findElements(By.xpath(xpath + rowFilter));

        return rows;
    }
}