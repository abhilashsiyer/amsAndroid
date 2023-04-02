package com.ams.amsandroid.helpers.imagevalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class DownloadUrl {
    private String baseImage;
    private String message;
    private Boolean validation_result;
}
