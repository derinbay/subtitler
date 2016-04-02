package com.altyazi.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;


public class ResponseStatusReporter implements TestRule {

    private static final Logger logger = LogManager.getLogger("PageStatusLogger");

    @Override
    public Statement apply(Statement base, Description description) {
        return statement(base, description);
    }

    private Statement statement(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Throwable caughtThrowable = null;
                try {
                    base.evaluate();
                    return;
                } catch (Throwable t) {
                    caughtThrowable = t;
                    logPageStatus();
                }
                throw caughtThrowable;
            }
        };
    }

    private void logPageStatus() {
        // TODO Activate here
//        try{
//            Browser browser = VisitorPool.getVisitorInThreadLocal().browser();
//
//            if (browser.currentURL().contains("/404")) {
//                logger.warn("404 PAGE NOT FOUND ERROR! ~ at " + browser.page().url());
//            }
//            if (browser.currentURL().contains("/500")) {
//                logger.warn("500 INTERNAL SERVER ERROR! ~ at " + browser.page().url());
//            }
//            if (browser.pageSource().contains("Exception")) {
//                logger.warn("JAVA EXCEPTION WHILE LOADING PAGE! ~ at " + browser.page().url() +"\n"+browser.pageSource());
//            }
//        } catch (Exception ex) {
//            logger.error("Exception on reporting page status! ~ at ", ex);
//        }

    }
}
