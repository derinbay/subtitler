package com.altyazi.models;

public abstract class Tab<T extends Tab> extends WebComponent {

    public abstract T activate();
}
