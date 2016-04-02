package com.altyazi.test;

import com.altyazi.models.users.VisitorPool;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class FreeUser implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                    return;
                } finally {
                    TestContext.freeVisitors();
                    VisitorPool.freeCurrentVisitor();
                }
            }
        };
    }
}
