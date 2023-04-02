package com.ams.amsandroid.helpers.reporter;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Suite {
    private String suiteName;
    private ArrayList<String> testName;
    private String testResult;
}