package com.altyazi.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataList extends WebComponent implements Pageable {

    private static final Logger logger = LogManager.getLogger(DataTable.class);

    protected WebComponent container;
    protected By by;
    protected String xpath;
    protected List<WebElement> elements;
    private int selectedIndex;
    private SearchResult selectedResult;

    public DataList() {}

    public DataList(WebComponent container, By by) {
        this.browser = container.browser();
        this.container = container;
        this.by = by;

        elements = container.browser.findElements(by);
    }

    @Override
    public void refresh() {
        elements = browser.findElements(by);
    }

    @Override
    public Paging getPaging() {
        return new Paging(this);
    }

    public int size() {
        return elements.size();
    }

    public DataList select(SelectionType type) {
        switch (type) {
            case FIRST:
                selectedIndex = 1;
                logger.trace("INFO: Clicked on first checkbox in table");
                break;

            case RANDOMLY:
                selectedIndex = new Random().nextInt(size());
                logger.trace("INFO: Clicked on randomly selected checkbox in table");
                break;

            case LAST:
                selectedIndex = size();
                logger.trace("INFO: Clicked on last checkbox in table");
                break;

            case PREVIOUS:
                selectedIndex = selectedIndex - 1;
                logger.trace("INFO: Clicked on previous selected checkbox in table");
                break;

            case NEXT:
                selectedIndex = selectedIndex + 1;
                logger.trace("INFO: Clicked on next selected checkbox in table");
                break;
        }
        return this;
    }

    public WebElement getSelected() {
        return elements.get(selectedIndex);
    }

    public List<WebElement> getElements() {
        return elements;
    }

    public void click() {
        WebElement link = elements.get(selectedIndex).findElement(By.tagName("a"));
        browser.clickTo(link);
        browser.wait(1);
        browser.waitForDialog();
    }

    public boolean hasNext() {
        return selectedIndex < elements.size();
    }

    public WebElement next() {
        return elements.get(selectedIndex++);
    }

    public int getIndex() {
        return selectedIndex - 1;
    }

    public void goNextPage() {
        getPaging().goNextPage();
    }

    public void goPreviousPage() {
        getPaging().goPreviousPage();
    }

    public void goPage(int pageNo) {
        getPaging().goPage(pageNo);
    }

    public void goNextBlock() {
        getPaging().goNextBlock();
    }

    public void goPrevBlock() {
        getPaging().goPrevBlock();
    }

    public void goPageWithTextBox(int pageNo) {
        getPaging().goPageWithTextBox(pageNo);
    }

    public WebElement findRowContains(String keyword) {
        for (WebElement element : elements) {
            if (element.getText().contains(keyword)) {
                return element;
            }
        }
        return null;
    }

    public boolean isEmpty() {
        WebElement list = browser.presenceWaitInSafe(5, by);
        if (list != null) {
            String text = list.getText();
            return text.contains("No record found") || text.contains("bulunmamaktadır");
        } else {
            return false;
        }
    }


    public SearchResult findRowClassicWay(String content) {
        List<WebElement> rows = getElements();
        int index = 1;
        for (WebElement row : rows) {
            if (row.getText().contains(content)) {
                return SearchResult.loadListRow(by, index, browser);
            }
            index++;
        }

        return null;
    }

    public SearchResult findTableRowClassicWay(String content) {
        List<WebElement> tables = getElements().get(0).findElements(By.xpath(".//table"));
        int index = 1;
        for (WebElement table : tables) {
            if (table.getText().contains(content)) {
                return SearchResult.loadTableRow(by, index, browser);
            }
            index++;
        }

        return null;
    }

    public List<SearchResult> findTableRowsClassicWay(String content) {
        List<SearchResult> results = new ArrayList<>();
        List<WebElement> rows = getElements();
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> tables = getElements().get(i).findElements(By.xpath(".//table"));
            int index = 1;
            for (WebElement table : tables) {
                if (table.getText().contains(content)) {
                    results.add(SearchResult.loadTableRow(by, index, browser));
                }
                index++;
            }
        }

        return results;
    }

    public SearchResult getRow(int rowId) {
        selectedResult = SearchResult.loadListRow(by, rowId, browser);

        return selectedResult;
    }
}
