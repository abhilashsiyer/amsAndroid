package com.ams.amsandroid.helpers.imagevalidation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseFileUrlResp {
    public String baseFileUrl;
}