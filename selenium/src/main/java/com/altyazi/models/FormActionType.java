package com.altyazi.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public enum FormActionType {
    DEC {
        @Override
        public void exec(WebComponent component, String fieldName, String amount, Consumer<Browser>... consumers) {
            WebElement element = get(component, fieldName);
            Double currentValue = Double.parseDouble(element.getAttribute("value"));
            Double newValue = Double.parseDouble(element.getAttribute("value")) - Double.parseDouble(amount);
            component.browser.type(element, "" + newValue);
        }
    },
    INC {
        @Override
        public void exec(WebComponent component, String fieldName, String amount, Consumer<Browser>... consumers) {
            WebElement element = get(component, fieldName);
            Double newValue = Double.parseDouble(element.getAttribute("value")) + Double.parseDouble(amount);
            component.browser.type(element, "" + newValue);
        }
    },
    TYPEANDSELECT {
        @Override
        public void exec(WebComponent component, String fieldName, String text, Consumer<Browser>... selectors) {
            TYPE.exec(component, fieldName, text);
            selectors[0].accept(component.browser);
            component.browser().waitForDialog();
        }
    },
    SELECT {
        @Override
        public void exec(WebComponent component, String fieldName, String option, Consumer<Browser>... consumers) {
            WebElement element = get(component, fieldName);
            component.browser().select(element, option);
        }
    },
    STANDARTSELECT {
        @Override
        public void exec(WebComponent component, String fieldName, String option, Consumer<Browser>... consumers) {
            WebElement element = get(component, fieldName);
            Select dropDown = new Select(element);

            List<WebElement> options = dropDown.getOptions();
            for (WebElement optionElement : options) {
                if (optionElement.getText().equals(option) || optionElement.getAttribute("value").equals(option)) {
                    component.browser.clickTo(optionElement);
                    break;
                }
            }
        }
    },
    TYPE {
        @Override
        public void exec(WebComponent component, String fieldName, String text, Consumer<Browser>... consumers) {
            WebElement element = get(component, fieldName);
            component.browser.type(element, text);
        }
    },
    VALUE {
        @Override
        public void exec(WebComponent component, String fieldName, String text, Consumer<Browser>... consumers) {
            WebElement element = get(component, fieldName);
            String id = id(element, fieldName);
            if (id.isEmpty()) {
                String className = className(element, fieldName);
                component.browser().js("javascript:document.getElementsByClassName('" + className + "')[0].value='" + text + "';");
            } else {
                component.browser().js("javascript:document.getElementById('" + id + "').value='" + text + "';");
            }
        }
    },
    CLICK {
        @Override
        public void exec(WebComponent component, String fieldName, String text, Consumer<Browser>... consumers) {
            WebElement element = get(component, fieldName);
            component.browser.clickTo(element);
            component.browser.acceptAlert();
        }
    },
    PICK_DATE {
        @Override
        public void exec(WebComponent component, String fieldName, String nullSTR, Consumer<Browser>... consumers) {
            Browser browser = component.browser();
            WebElement opener = calendarOpener(component, fieldName);
            //click on the calender button
            browser.clickTo(opener);

            //find calender table body
            WebElement monthCalender = browser.presenceWait(5, By.xpath("//*[@id='ui-datepicker-div']/table/tbody"));
            List<WebElement> allRows = monthCalender.findElements(By.tagName("tr"));

            for (WebElement row : allRows) {
                List<WebElement> allDaysInTheRow = row.findElements(By.tagName("td"));

                for (WebElement day : allDaysInTheRow) {
                    if (!day.getAttribute("class").contains("ui-datepicker-unselectable")) {
                        browser.clickTo(day);
                        logger.trace("INFO: Selected ending date");
                        return;
                    }
                }
            }
            logger.trace("INFO: Could NOT select ending date!");
        }
    },
    PICK_DATE_AFTER {
        @Override
        public void exec(WebComponent component, String fieldName, String dayAfter, Consumer<Browser>... consumers) {
            Browser browser = component.browser();
            WebElement opener = calendarOpener(component, fieldName);
            //click on the calender button
            browser.clickTo(opener);

            //find calender table body
            WebElement monthCalender = browser.presenceWait(5, By.xpath("//*[@id='ui-datepicker-div']/table/tbody"));
            List<WebElement> allRows = monthCalender.findElements(By.tagName("tr"));
            List<WebElement> days = new ArrayList<>();
            List<WebElement> today = new ArrayList<>();

            for (WebElement row : allRows) {
                List<WebElement> allDaysInTheRow = row.findElements(By.tagName("td"));

                for (WebElement day : allDaysInTheRow) {
                    if (!day.getAttribute("class").contains("ui-datepicker-unselectable")) {
                        days.add(day);
                    }

                    if (day.getAttribute("class").contains("ui-datepicker-today")) {
                        today.add(day);
                    }
                }
            }

            if (dayAfter == "today") {
                browser.clickTo(today.get(0));
            } else {
                if (Integer.parseInt(dayAfter) > (days.size() - 1)) {
                    goNextMonth(component);
                    component.browser().clickTo(By.xpath("//a[text()='" + (Integer.parseInt(dayAfter) - days.size() + 1) + "']/.."));
                    logger.trace("INFO: Selected ending date");
                    return;
                } else {
                    browser.clickTo(days.get(Integer.parseInt(dayAfter)));
                    logger.trace("INFO: Selected ending date");
                    return;
                }
            }
            logger.trace("INFO: Could NOT select ending date!");
        }
    };

    private static final Logger logger = LogManager.getLogger(FormActionType.class);

    protected abstract void exec(WebComponent component, String fieldName, String value, Consumer<Browser>... consumers);

    protected WebElement get(WebComponent component, String fieldName) {
        WebElement element = null;
        Class<?> pageClass = component.getClass();
        Field field = null;

        try {
            field = pageClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            element = (WebElement) field.get(component);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Exception on accessing field {" + fieldName + "} of page");
        }

        return element;
    }

    protected String id(WebElement component, String fieldName) {
        String id = component.getAttribute("id");

        return id;
    }

    protected String className(WebElement component, String fieldName) {
        String className = component.getAttribute("class");

        return className;
    }

    protected WebElement calendarOpener(WebComponent component, String fieldName) {
        WebElement element = get(component, fieldName);
        String dateInputId = id(element, fieldName);

        String openerId = dateInputId.substring(0, dateInputId.lastIndexOf("_"));

        element = component.browser.findElement(By.xpath("//*[@id='" + openerId + "']//button"));

        return element;
    }

    protected void goNextMonth(WebComponent component) {
        component.browser.clickTo(By.xpath("//a[contains(@class, 'ui-datepicker-next')]"));
    }
}
