package com.ams.amsandroid.helpers.reporter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskResult {
    private String baseImage;
    private String imageDiffResult;
    private String message;
    private String validationResult;
}
