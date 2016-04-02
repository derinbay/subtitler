package com.altyazi.models;

import javax.annotation.concurrent.Immutable;
import java.util.function.Consumer;

@Immutable
public class FormAction {
    public final FormActionType type;
    public final String propertyName;
    public final String value;
    private Consumer<Browser>[] consumers;

    public FormAction(String propertyName, String value, FormActionType type, Consumer<Browser>... consumers) {
        this.propertyName = propertyName;
        this.value = value;
        this.type = type;
        this.consumers = consumers;
    }

    public void exec(WebComponent webComponent) {
        type.exec(webComponent, propertyName, value, consumers);
    }
}
