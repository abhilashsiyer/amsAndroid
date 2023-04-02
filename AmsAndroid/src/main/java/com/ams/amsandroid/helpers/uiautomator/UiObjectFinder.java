package com.ams.amsandroid.helpers.uiautomator;

import static com.ams.amsandroid.exception.ExceptionManager.noMatchingUiObject;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import java.util.List;
import java.util.stream.Collectors;

public class UiObjectFinder {

    private static final int DEFAULT_TIMEOUT = 5000;

    public UiObjectFinder() {
    }

    public UiObject2 findObjectByText(UiDevice mDevice, String textIdentifier){

        UiObject2 object = mDevice.wait(Until
                .findObject(By.text(textIdentifier)), DEFAULT_TIMEOUT);

        if (object == null) {
            noMatchingUiObject("No matching element found with the text identifier "
                    + textIdentifier);
        }
        return object;
    }

    public UiObject2 findObjectByResourceId(UiDevice mDevice, String packageName, String identifier){

        UiObject2 object = mDevice.wait(Until
                .findObject(By.res(packageName, identifier)), DEFAULT_TIMEOUT);

        if (object == null) {
            noMatchingUiObject("No matching element found for package "
                    + packageName +" with identifier "+ identifier);
        }
        return object;
    }

    public UiObject2 findObjectFromObjects(UiDevice mDevice, String packageName,
                                           String filterText, String identifier){

        List<UiObject2> uiObject2 = mDevice.wait(Until
                .findObjects(By.res(packageName, identifier)), DEFAULT_TIMEOUT);

        UiObject2 uiObject = uiObject2.
                stream().
                filter(uiObject21 -> uiObject21.getText().contains(filterText))
                .collect(Collectors.toList()).get(0);

        return uiObject;
    }


    }
