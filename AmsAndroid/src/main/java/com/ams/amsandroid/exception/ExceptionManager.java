package com.ams.amsandroid.exception;

import lombok.SneakyThrows;

public class ExceptionManager {

    @SneakyThrows
    static public void noMatchingUiObject(String message){
        if (message == null) {
            throw new NoMatchingUiObject();
        }
        throw new NoMatchingUiObject(message);
    }

    @SneakyThrows
    static public void uiObjectNotClickable(String message) {
        if (message == null) {
            throw new UiObjectNotClickable();
        }
        throw new UiObjectNotClickable(message);
    }
}
