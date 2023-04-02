package com.ams.amsandroid;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;
import androidx.test.platform.app.InstrumentationRegistry;
import static org.junit.Assert.assertThat;

import com.ams.amsandroid.api.AMS;
import com.ams.amsandroid.helpers.imagevalidation.ImageValidator;
import com.ams.amsandroid.helpers.reporter.Reporter;
import com.ams.amsandroid.helpers.reporter.Suite;
import com.ams.amsandroid.helpers.uiautomator.UiObjectActions;
import com.ams.amsandroid.helpers.uiautomator.UiObjectFinder;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.CoreMatchers.notNullValue;
import org.junit.rules.TestName;

import java.io.IOException;
import java.util.ArrayList;

public class AMSAndroid implements AMS {
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final int DEFAULT_TIMEOUT = 30000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private String packageName;
    private String apiKey;
    private String testMatrixId;
    private String project;
    private String branch;
    private final UiDevice mDevice;
    private final UiObjectFinder uiObjectFinder = new UiObjectFinder();
    private final UiObjectActions uiObjectActions = new UiObjectActions();
    private final ImageValidator imageValidator = new ImageValidator();

    private Reporter reporter = new Reporter();
    private ArrayList<Suite> suites = new ArrayList<>();
    private Suite suite = new Suite();
    private ArrayList<String> testName = new ArrayList<>();


    public AMSAndroid(String apiKey, String project, String branch, String testMatrixId) {
        this.apiKey = apiKey; // Verify apiKey
        this.branch = branch;
        this.project = project;
        this.testMatrixId=testMatrixId;
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        reporter = new Reporter();
        suites = new ArrayList<>();
        suite = new Suite();
        testName = new ArrayList<>();
    }

    public void launchApp(String packageName) {
        this.packageName = packageName;
        // Start from the home screen
        mDevice.pressHome();
        // Wait for device launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the blueprint app
        Context context = getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(this.packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);
        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(this.packageName).depth(0)), LAUNCH_TIMEOUT);
    }

    public void clickById(int resId) {
        String resourceId = getApplicationContext().getResources().getResourceEntryName(resId);
        uiObjectActions.click(uiObjectFinder.findObjectByResourceId(mDevice,this.packageName,resourceId),
                packageName,resourceId);
    }

    public void clickById(String resourceId) {
        uiObjectActions.click(uiObjectFinder.findObjectByResourceId(mDevice,this.packageName,resourceId),
                packageName,resourceId);
    }

    public void clickById(String packageName, String resourceId) {
        uiObjectActions.click(uiObjectFinder.findObjectByResourceId(mDevice,packageName,resourceId),
                packageName,resourceId);
    }

    public void clickByText(String textIdentifier) {
        uiObjectActions.click(uiObjectFinder.findObjectByText(mDevice,textIdentifier),textIdentifier);
    }

    @Override
    public void clickByText(String text, int resId) {
        String resourceId = getApplicationContext().getResources().getResourceEntryName(resId);

        uiObjectActions.click(uiObjectFinder.findObjectFromObjects(mDevice, this.
                packageName, text, resourceId), packageName, resourceId);
    }


    public void verifyTextIsVisible(String textIdentifier){
        uiObjectActions.checkVisibleBounds(
                uiObjectFinder.findObjectByText(mDevice,textIdentifier));
    }


    @Override
    public void verifyTextIsVisibleAtId(String valueToMatch, int resId) {
        String resourceId = getApplicationContext().getResources().getResourceEntryName(resId);

        uiObjectActions.checkTextIsVisible(uiObjectFinder.
                findObjectByResourceId(mDevice,this.packageName,resourceId),valueToMatch);
    }

    @Override
    public void verifyTextIsVisibleAtId(String resourceId, String pkgName, String valueToMatch) {
        uiObjectActions.checkTextIsVisible(uiObjectFinder.
                findObjectByResourceId(mDevice,pkgName,resourceId),valueToMatch);
    }

    @Override
    public void enterTextAtId(String valueToMatch, int resId) {

    }

    @Override
    public void verifyTextIsVisibleAtId(String resourceId, String valueToMatch) {
        uiObjectActions.checkTextIsVisible(uiObjectFinder.
                findObjectByResourceId(mDevice,this.packageName,resourceId),valueToMatch);
    }

    @Override
    public void verifyPage(String tagName, String testName){
        imageValidator.verifyPage(mDevice,tagName,this.project, this.branch,testName,
                this.testMatrixId);
    }

    @Override
    public void end(TestName testName) {
        System.out.println("Test method name " + testName.getMethodName());

//        suite.setTestName(testName);
        suites.add(suite);
//        reporter.setSuite(suites);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Mapper begin");
        try {
            String jsonInString = mapper.writeValueAsString(reporter);
            System.out.println("JSON str" + jsonInString);
        } catch (IOException e) {
            System.out.println("Mapper failed");
            e.printStackTrace();
        }


    }


    /**
     * Uses package manager to find the package name of the device launcher. Usually this package
     * is "com.android.launcher" but can be different at times. This is a generic solution which
     * works on all platforms.`
     */

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package name
        PackageManager pm = getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }


}
