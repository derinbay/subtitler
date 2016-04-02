package com.altyazi.test;

import com.altyazi.models.users.Visitor;

import java.util.ArrayList;
import java.util.List;

public class TestContext {

    public static final ThreadLocal<TestContext> contexts = new ThreadLocal<>();

    private List<Visitor> users = new ArrayList<>();
    private String testClassName;
    private String testMethodName;

    private TestContext(String testClassName, String testMethodName) {
        this.testClassName = testClassName;
        this.testMethodName = testMethodName;
    }

    public static TestContext get() {
        return contexts.get();
    }

    public static void init(String testClassName, String testMethodName) {
        contexts.set(new TestContext(testClassName, testMethodName));
    }

    public static void remove() {
        contexts.remove();
    }

    public static void freeVisitors() {
        if (TestContext.get() != null) {
            for (Visitor visitor : TestContext.get().users) {
                visitor.free();
            }
        }
    }

    public static void closeBrowsers() {
        if (TestContext.get() != null) {
            for (Visitor visitor : TestContext.get().users) {
                if (visitor != null && visitor.browser() != null) {
                    visitor.closeBrowser();
                }
            }
        }
    }

    public void addVisitor(Visitor visitor) {
        users.add(visitor);
    }

    public List<Visitor> getUsers() {
        return users;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public String getTestClassName() {
        return testClassName;
    }
}
