package com.ams.amsandroid.exception;

public class NoMatchingUiObject extends RuntimeException{

    public NoMatchingUiObject(String message)
    {
        super(message);
    }

    public NoMatchingUiObject() {

    }
}
