package com.altyazi.models;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class Paging extends WebComponent {

    private final Pageable pageable;

    public Paging(Pageable pageable) {
        this.pageable = pageable;
        this.browser = this.pageable.browser();
    }

    public void goLastPage() {
        WebElement lastButton = browser.findElement(By.xpath("//a[contains(@class,'pageLink') and contains(@class,'last')]"));
        if (lastButton != null) {
            browser.scrollTo(lastButton);
            browser.clickTo(lastButton);
            this.pageable.refresh();
        }
    }

    public void goNextPage() {
        WebElement nextButton = browser.findElementSafely(By.xpath("//div[@class='pagination']//a[contains(@class,'next') and contains(@class,'navigation')]"));
        if (nextButton != null) {
            browser.scrollTo(nextButton);
            browser.clickTo(nextButton);
            this.pageable.refresh();
        }
    }

    public boolean isOnLastPage() {
        WebElement nextButton = browser.findElementSafely(By.xpath("//div[@class='pagination']//a[contains(@class,'next') and contains(@class,'navigation')]"));
        return nextButton == null;
    }

    public boolean isExist() {
        return browser.findElementSafely(By.xpath("//div[@class='pagination']")) != null;
    }

    public void goPreviousPage() {
        WebElement prevButton = browser.findElementSafely(By.xpath("//div[@class='pagination']//a[contains(@class,'prev') and contains(@class,'navigation')]"));
        if (prevButton != null) {
            browser.scrollTo(prevButton);
            browser.clickTo(prevButton);
            this.pageable.refresh();
        }
    }

    public void goPage(int pageNo) {
        WebElement goPageButton = browser.findElement(By.xpath("//div[@class='pagination']//a[contains(text(),'" + pageNo + "')]"));
        if (goPageButton != null) {
            browser.scrollTo(goPageButton);
            browser.clickTo(goPageButton);
            this.pageable.refresh();
        }
    }

    public void goNextBlock() {
        WebElement nextBlockButton = browser.findElementSafely(By.xpath("//div[@class='pagination']//a[contains(@class,'nextBlock') and contains(@class,'navigation')]"));
        if (nextBlockButton != null) {
            browser.scrollTo(nextBlockButton);
            browser.clickTo(nextBlockButton);
            this.pageable.refresh();
        }
    }

    public void goPrevBlock() {
        WebElement prevBlockButton = browser.findElementSafely(By.xpath("//div[@class='pagination']//a[contains(@class,'prevBlock') and contains(@class,'navigation')]"));
        if (prevBlockButton != null) {
            browser.scrollTo(prevBlockButton);
            browser.clickTo(prevBlockButton);
            this.pageable.refresh();
        }
    }

    public void goPageWithTextBox(int pageNo) {
        WebElement pagingTextBox = browser.findElementSafely(By.xpath("(//input[@class='currentPage'])[1]"));
        browser.scrollTo(pagingTextBox);
        pagingTextBox.clear();
        pagingTextBox.sendKeys("" + pageNo);
        pagingTextBox.sendKeys(Keys.RETURN);
        browser.waitForDialog();
    }
}
