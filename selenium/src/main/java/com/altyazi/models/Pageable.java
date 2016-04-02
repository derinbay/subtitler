package com.altyazi.models;

public interface Pageable {
    void refresh();

    Paging getPaging();

    Browser browser();

    void goNextPage();

    void goPreviousPage();

    void goPage(int pageNo);
}
