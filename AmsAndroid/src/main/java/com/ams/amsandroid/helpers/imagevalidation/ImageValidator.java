package com.ams.amsandroid.helpers.imagevalidation;

import android.content.Context;
import android.os.Build;

import androidx.test.uiautomator.UiDevice;

import com.ams.amsandroid.helpers.imagevalidation.model.BaseFileUrlResp;
import com.ams.amsandroid.helpers.imagevalidation.model.UploadImageResp;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

import lombok.SneakyThrows;
import okhttp3.Response;

public class ImageValidator {
    @SneakyThrows
    public void verifyPage(Context context, UiDevice mDevice, String tagName, String project, String branchName,
                           String testName, String testMatrixId, int statusBarHeight) {
        System.out.println("System params =>" + tagName + "||" + project + "||" + branchName + "||"
                + testName + "||" + testMatrixId + "||");
        // To be replaced with wait for state
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        String fullFileName = AMSCustomScreenshot.capture(tagName, context);
        System.out.println("fullFileName=>"+fullFileName);

        System.out.println("files dir=> "+context.getFilesDir());

        File file = new File(context.getFilesDir(),
                "ams_custom_screenshots/" +
                fullFileName);


        System.out.println("AMSInfo+"+file.getPath());

        OkHTTPHelper okHTTPHelper = new OkHTTPHelper();
        ObjectMapper objectMapper = new ObjectMapper();
        String model = Build.MODEL.replaceAll("\\s+", "_");
        System.out.println("Model=> " + model);


        Response getBaseFileUrlResp = okHTTPHelper.getBaseFileUrl(tagName, project, testName,
                    branchName, model);
        BaseFileUrlResp getBaseFileUrl = objectMapper.readValue(
                    getBaseFileUrlResp.body().string(), BaseFileUrlResp.class);
            System.out.println("getBaseUrl=> " + getBaseFileUrlResp.body().string());


            System.out.println("getBaseFileUrl => " + getBaseFileUrl.baseFileUrl);

        if (getBaseFileUrl.baseFileUrl.startsWith("Failed")){
            //then baseURL doesn't exist and upload baseURL and end the test
            Response updateBaseImageResp = okHTTPHelper.uploadBaseImage(file,tagName, testName, project,
                    model);
            UploadImageResp uploadImageResp = objectMapper.readValue(
                    updateBaseImageResp.body().string(), UploadImageResp.class);
            // uploading image itself is validation success. No validation as such performed
            System.out.println("imageValidationResp =>"+ uploadImageResp.validationResult);
        }

        else {
            Response uploadImageToCompareResp = okHTTPHelper.uploadImageToCompare(file,tagName, testName,
                    project, branchName, testMatrixId,model);

            UploadImageResp uploadImageResp = objectMapper.readValue(
                    uploadImageToCompareResp.body().string(), UploadImageResp.class);

            Response visualValidateResp = okHTTPHelper.visualValidate(getBaseFileUrl.baseFileUrl,uploadImageResp.url,tagName, testName,
                    project, branchName, testMatrixId,model, mDevice.getDisplayHeight(),
                    mDevice.getDisplayWidth(), statusBarHeight);


            UploadImageResp visualValidationResult = objectMapper.readValue(
                    visualValidateResp.body().string(), UploadImageResp.class);
            System.out.println("We are here after visual validation");

            if (!visualValidationResult.validationResult.contains("Success")) {

                throw new AssertionError("Validation Failed! " +
                        "Click on this for more actions=> "
                );
            }
        }

    }

}
