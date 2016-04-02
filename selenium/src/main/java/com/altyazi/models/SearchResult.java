package com.altyazi.models;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchResult extends WebComponent {

    private final int index;
    private final String tableId;
    private final String rowFilter;
    private WebElement row;

    private SearchResult previousState = null;
    private List<String> columnValues = new ArrayList<>();

    private SearchResult(int index, String tableId, WebElement row, Browser browser, String rowFilter) {
        this.index = index;
        this.tableId = tableId;
        this.row = row;
        this.browser = browser;
        this.rowFilter = rowFilter;

        List<WebElement> elements = row.findElements(By.xpath("./td"));

        for (WebElement element : elements) {
            columnValues.add(element.getText());
        }
    }

    public static SearchResult load(String tableId, int index, Browser browser, String rowFilter) {
        WebElement selectedRow = browser.presenceWait(10, By.xpath("//*[@id='" + tableId + "']//tbody/tr" + rowFilter + "[" + index + "]"));
        return new SearchResult(index, tableId, selectedRow, browser, rowFilter);
    }

    public static SearchResult loadInside(String tableId, int index, Browser browser, String rowFilter) {
        WebElement selectedRow = browser.presenceWait(10, By.xpath("//*[@id='" + tableId + "']//tbody/tr" + rowFilter + "/td[2]//tbody/tr" + rowFilter + "[contains(@class, 'innerDatatableRows')][" + index + "]"));
        return new SearchResult(index, tableId, selectedRow, browser, rowFilter);
    }

    public static SearchResult loadListRow(By by, int index, Browser browser) {
        WebElement selectedRow = browser.presenceWait(10, By.xpath(getByText(by) + "[" + index + "]"));
        return new SearchResult(index, null, selectedRow, browser, null);
    }

    public static SearchResult loadTableRow(By by, int index, Browser browser) {
        WebElement selectedRow = browser.presenceWait(10, By.xpath(getByText(by) + "//table[" + index + "]"));
        return new SearchResult(index, null, selectedRow, browser, null);
    }

    public static String getByText(By by) {
        return by.toString().substring(by.toString().indexOf("/"), by.toString().length());
    }

    public WebElement getElement() {
        return row;
    }

    public SearchResult reload() {
        SearchResult updated = load(tableId, index, browser, rowFilter);
        updated.previousState = this;

        return updated;
    }

    public String columnValue(int indexOfColumn) {
        return columnValues.get(indexOfColumn);
    }

    public WebElement column(int indexOfColumn) {
        return row.findElement(By.xpath(".//td[" + indexOfColumn + "]"));
    }

    public SearchResult activateSelection() {
        WebElement checkbox = row.findElement(By.xpath("./td[1]/div/div/div[2]"));
        browser.scrollTo(checkbox);
        browser.clickTo(checkbox);

        return this;
    }
}
