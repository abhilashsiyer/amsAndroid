package com.ams.amsandroid.helpers.imagevalidation;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHTTPHelper {
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    // private String HOST_NAME = "http://127.0.0.1:5000";
    private String HOST_NAME = "https://sampleuiautomator.ts.r.appspot.com";

    public Response visualValidate(String baseFileUrl, String toCompareFileUrl, String tagName,
                                   String testName, String project,
                                   String branchName, String testMatrixId,
                                   String deviceModel, int displayHeight, int displayWidth,
                                   int statusBarHeight) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tag", tagName)
                .addFormDataPart("project", project)
                .addFormDataPart("branchName", branchName)
                .addFormDataPart("testMatrixId", testMatrixId)
                .addFormDataPart("testCaseName", testName)
                .addFormDataPart("baseFileUrl", baseFileUrl)
                .addFormDataPart("toCompareFileUrl", toCompareFileUrl)
                .addFormDataPart("deviceModel",deviceModel)
                .addFormDataPart("statusBarHeight", String.valueOf(statusBarHeight))
                .addFormDataPart("displayHeight",String.valueOf(displayHeight))
                .addFormDataPart("displayWidth",String.valueOf(displayWidth))
                .build();

        Request request = new Request.Builder()
                .url(HOST_NAME+"/visual-validate/")
                .method("POST", requestBody)
                .build();
        Response response = client.newCall(request).execute();

        return response;
    }

    public Response uploadResult(String diff, String toCompareFileUrl, String tagName,
                                 String testName, String project,
                                 String branchName, String testMatrixId,
                                 String deviceModel, int displayHeight, int displayWidth,
                                 int statusBarHeight) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tag", tagName)
                .addFormDataPart("project", project)
                .addFormDataPart("branchName", branchName)
                .addFormDataPart("testMatrixId", testMatrixId)
                .addFormDataPart("testCaseName", testName)
                .addFormDataPart("deviceModel",deviceModel)
                .addFormDataPart("diff",diff)
                .addFormDataPart("statusBarHeight", String.valueOf(statusBarHeight))
                .addFormDataPart("displayHeight",String.valueOf(displayHeight))
                .addFormDataPart("displayWidth",String.valueOf(displayWidth))
                .addFormDataPart("toCompareFileUrl", toCompareFileUrl)
                .build();

        Request request = new Request.Builder()
                .url(HOST_NAME+"/upload-result-file/")
                .method("POST", requestBody)
                .build();
        Response response = client.newCall(request).execute();

        return response;
    }

    public Response getBaseFileUrl(String tagName, String project, String testCaseName,
                                   String branch, String model) throws IOException {
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
        Response response = client.newCall(request).execute();
        return response;
    }

    public Response uploadBaseImage(File file, String tagName, String testName, String project,
                                    String deviceModel) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tag", tagName)
                .addFormDataPart("project", project)
                .addFormDataPart("testCaseName", testName)
                .addFormDataPart("deviceModel",deviceModel)
                .addFormDataPart("file",file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                file))
                .build();

        Request request = new Request.Builder()
                .url(HOST_NAME+"/upload-base-file/")
                .method("POST", requestBody)
                .build();
        Response response = client.newCall(request).execute();

        return response;
    }

    public Response uploadImageToCompare(File file, String tagName, String testName, String project,
                                         String branchName, String testMatrixId, String deviceModel)
            throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("tag", tagName)
                .addFormDataPart("project", project)
                .addFormDataPart("branchName", branchName)
                .addFormDataPart("testMatrixId", testMatrixId)
                .addFormDataPart("testCaseName", testName)
                .addFormDataPart("deviceModel",deviceModel)
                .addFormDataPart("file",file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                file))
                .build();

        Request request = new Request.Builder()
                .url(HOST_NAME+"/upload_to_compare_file/")
                .method("POST", requestBody)
                .build();
        Response response = client.newCall(request).execute();

        return response;
    }
}
