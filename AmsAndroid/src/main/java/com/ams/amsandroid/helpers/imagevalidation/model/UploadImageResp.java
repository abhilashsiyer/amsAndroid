package com.ams.amsandroid.helpers.imagevalidation.model;

import lombok.Data;

@Data
public class UploadImageResp {
    public String message;
    public String validationResult;
    public String url;
    public String image;
    public String diff;

}