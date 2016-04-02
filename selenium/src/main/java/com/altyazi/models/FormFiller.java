package com.altyazi.models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.altyazi.models.FormActionType.*;

public class FormFiller {
    private List<FormAction> actions = new ArrayList<FormAction>();
    private Browser browser;

    public static FormFiller by() {
        return new FormFiller();
    }

    public FormFiller dec(String inputPropertyName, double amount) {
        actions.add(new FormAction(inputPropertyName, "" + amount, DEC));
        return this;
    }

    public FormFiller inc(String inputPropertyName, double amount) {
        actions.add(new FormAction(inputPropertyName, "" + amount, INC));
        return this;
    }

    public FormFiller click(String inputPropertyName) {
        actions.add(new FormAction(inputPropertyName, null, CLICK));
        return this;
    }

    public FormFiller hover(String inputPropertyName) {
        actions.add(new FormAction(inputPropertyName, null, CLICK));
        return this;
    }

    public FormFiller type(String inputPropertyName, String text) {
        actions.add(new FormAction(inputPropertyName, text, TYPE));
        return this;
    }

    public FormFiller typeAndSelect(String inputPropertyName, String text, Consumer<Browser> consumer) {
        actions.add(new FormAction(inputPropertyName, text, TYPEANDSELECT, consumer));
        return this;
    }

    public FormFiller value(String inputPropertyName, String text) {
        actions.add(new FormAction(inputPropertyName, text, VALUE));
        return this;
    }

    public FormFiller select(String selectPropertyName, String option) {
        actions.add(new FormAction(selectPropertyName, option, SELECT));
        return this;
    }

    public FormFiller standartSelect(String selectPropertyName, String option) {
        actions.add(new FormAction(selectPropertyName, option, STANDARTSELECT));
        return this;
    }

    public FormFiller pickDate(String datePropertyName) {
        actions.add(new FormAction(datePropertyName, null, PICK_DATE));
        return this;
    }

    public FormFiller pickDateAfter(String datePropertyName, String day) {
        actions.add(new FormAction(datePropertyName, day, PICK_DATE_AFTER));
        return this;
    }

    public void fill(WebComponent webComponent) {
        this.browser = webComponent.browser();

        browser.loadElements(webComponent);

        for (FormAction action : actions) {
            action.exec(webComponent);
        }
    }
}
