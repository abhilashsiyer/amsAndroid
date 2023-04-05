package com.ams.amsandroidlib;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends SetUp{
    @Test
    public void useAppContext() {
        // Context of the app under test.
        ams.enterTextAtId("SomeName",R.id.someName);
        ams.verifyPage("LibAppHome", testName.getMethodName());

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.ams.amsandroidlib", appContext.getPackageName());
    }

    @Test
    public void sampleQTest() {
        ams.clickByText("CONTINUE AS GUEST");
        ams.clickByText("SKIP");
        ams.clickByText("While using the app");
        ams.clickByText("While using the app");
        ams.clickByText("LOG IN");
        ams.clickByText("9");
        ams.clickByText("9");
        ams.clickByText("8");
        ams.clickByText("6");
        ams.clickByText("CLOSE");
        ams.verifyPage("QantasLoggedInHome",testName.getMethodName());
    }
}