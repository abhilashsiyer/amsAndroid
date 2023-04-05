package com.ams.amsandroid.api;

import org.junit.rules.TestName;

public interface AMS {
    void launchApp(String pkgName);

    void launchAppUsingShell(String pkgName, String activityName);
    void clickById(String identifier);
    void clickById(int resId);
    void clickById(String pkgName, String identifier);
    void clickByText(String textIdentifier);
    void clickByText(String text, int resId);
    void verifyPage(String tagName, String testName);
    void verifyTextIsVisible(String textIdentifier) ;
    void verifyTextIsVisibleAtId(String resourceId, String valueToMatch);
    void verifyTextIsVisibleAtId(String valueToMatch, int resId);
    void verifyTextIsVisibleAtId(String resourceId, String pkgName, String valueToMatch);
    void enterTextAtId(String textToEnter, int resId);
    void enterTextAtId(String textToEnter, String identifier);
    void enterTextAtId(String textToEnter, String pkgName, String identifier);
    void end(TestName testName);
}
