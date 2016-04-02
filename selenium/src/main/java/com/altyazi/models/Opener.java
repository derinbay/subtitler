package com.altyazi.models;

public abstract class Opener {

    private final WebComponent opener;

    public Opener(WebComponent opener) {
        this.opener = opener;
    }

    public abstract void open();

    public WebComponent getSource() {
        return opener;
    }
}
