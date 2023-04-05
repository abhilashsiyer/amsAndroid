package com.ams.amsandroid.helpers.imagevalidation;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHTTPHelper {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//    private String HOST_NAME = "http://0.0.0.0:106";
    private String HOST_NAME = "https://sampleuiautomator.ts.r.appspot.com";

    public Response visualValidate(File file, String tagName, String testName, String project,
                                   String branchName, String baseFileUrl, String testMatrixId,
                                   String deviceModel) {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tag", tagName)
                .addFormDataPart("project", project)
                .addFormDataPart("branchName", branchName)
                .addFormDataPart("testMatrixId", testMatrixId)
                .addFormDataPart("testCaseName", testName)
                .addFormDataPart("baseFileUrl", baseFileUrl)
                .addFormDataPart("deviceModel",deviceModel)
//                .addFormDataPart("file", tagName+".png",
//                        RequestBody.create(MEDIA_TYPE_PNG, file))
                .addFormDataPart("file",file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                file))
                .build();

        Request request = new Request.Builder()
                .url("https://sampleuiautomator.ts.r.appspot.com/visual-validate/")
                .method("POST", requestBody)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }

    public Response getBaseFileUrl(String tagName, String project, String testCaseName,
                                   String branch, String model) throws IOException{
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tag", tagName)
                .addFormDataPart("project", project)
                .addFormDataPart("testCaseName", testCaseName)
                .addFormDataPart("branch", branch)
                .addFormDataPart("deviceModel", model)
                .build();

        Request request = new Request.Builder()
                .url(HOST_NAME+"/get_base_file_url/")
                .method("POST", requestBody)
                .build();
        Response response = null;
            response = client.newCall(request).execute();
        return response;
    }
}
