package com.ams.amsandroid.helpers.uiautomator;

import static com.ams.amsandroid.exception.ExceptionManager.uiObjectNotClickable;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import android.graphics.Rect;

import androidx.test.uiautomator.UiObject2;

import com.ams.amsandroid.exception.UiObjectNotClickable;

import org.junit.Assert;

public class UiObjectActions {


    public UiObjectActions() {
    }

    public void click(UiObject2 object, String textIdentifier)  {
        if (object.isClickable()) {
            object.click();
        } else {
            uiObjectNotClickable("Element not clickable "+textIdentifier);
        }
    }

    public void click(UiObject2 object, String packageName, String identifier) {
        if (object.isClickable()) {
            object.click();
        } else {
            uiObjectNotClickable("No matching element found for package "
                    + packageName +" with identifier "+ identifier);
        }
    }

    public void enterText(UiObject2 object, String textIdentifier, String textToEnter)  {
        if (object.isClickable()) {
            object.clear();
            object.setText(textToEnter);
        } else {
            uiObjectNotClickable("Element not clickable "+textIdentifier);
        }
    }

    public void enterText(UiObject2 object, String packageName, String identifier,
                          String textToEnter) {
        if (object.isClickable()) {
            object.clear();
            object.setText(textToEnter);
        } else {
            uiObjectNotClickable("No matching element found for package "
                    + packageName +" with identifier "+ identifier);
        }
    }

    public void checkVisibleBounds(UiObject2 object){
        Rect bounds = object.getVisibleBounds();

        if (bounds.width() > 0 && bounds.height() > 0) {
            assertTrue("Element visible", true);
//            suite.setTestResult("Pass");
        } else {
//            suite.setTestResult("Fail");
            fail("Element not visible");
        }
    }

    public void checkTextIsVisible(UiObject2 object, String valueToMatch){
        String valueOriginal = object.getText();

        try {
            Assert.assertEquals(valueToMatch, valueOriginal);
//            suite.setTestResult("Pass");
        } catch (AssertionError e) {
//            suite.setTestResult("Fail");
            throw new AssertionError(e.getMessage());
        }
    }
}
