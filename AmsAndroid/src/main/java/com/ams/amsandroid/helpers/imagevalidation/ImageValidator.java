package com.ams.amsandroid.helpers.imagevalidation;

import android.os.Build;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;

import com.ams.amsandroid.helpers.imagevalidation.model.BaseFileUrlResp;
import com.ams.amsandroid.helpers.imagevalidation.model.UploadImageResp;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import lombok.SneakyThrows;
import okhttp3.Response;

public class ImageValidator {
    @SneakyThrows
    public void verifyPage(UiDevice mDevice, String tagName, String project, String branchName,
                           String testName, String testMatrixId) {
        System.out.println("System params =>" + tagName + "||" + project + "||" + branchName + "||"
                + testName + "||" + testMatrixId + "||");

        String fullFileName = AMSCustomScreenshot.capture(tagName);
        System.out.println("fullFileName=>"+fullFileName);

        File file = new File(InstrumentationRegistry.getInstrumentation().
                getTargetContext().getFilesDir(), "ams_custom_screenshots/" +
                fullFileName);


        System.out.println("AMSInfo+"+file.getPath());

        OkHTTPHelper okHTTPHelper = new OkHTTPHelper();
        ObjectMapper objectMapper = new ObjectMapper();
        String model = Build.MODEL.replaceAll("\\s+", "_");
        System.out.println("Model=> " + model);

        try {

            Response getBaseFileUrlResp = okHTTPHelper.getBaseFileUrl(tagName, project, testName,
                    branchName, model);
            BaseFileUrlResp getBaseFileUrl = objectMapper.readValue(
                    getBaseFileUrlResp.body().string(), BaseFileUrlResp.class);

            System.out.println("getBaseUrl=> " + getBaseFileUrlResp.body().string());

            System.out.println("getBaseFileUrl => " + getBaseFileUrl.baseFileUrl);

            Response visualValidateResp = okHTTPHelper.visualValidate(file, tagName, testName,
                    project, branchName, getBaseFileUrl.baseFileUrl, testMatrixId, model);

            UploadImageResp imageValidationResp = objectMapper.readValue(
                    visualValidateResp.body().string(), UploadImageResp.class);

            System.out.println("imageValidationResp=> " + visualValidateResp.body().string());

            System.out.println("validationResult => " + imageValidationResp.validationResult);

            if (!imageValidationResp.validationResult.contains("Success")) {
                throw new AssertionError("Validation Failed! " +
                        "Click on this for more actions" + imageValidationResp.validationResult
                );
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}