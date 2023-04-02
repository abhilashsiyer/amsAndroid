package com.ams.amsandroid.helpers.imagevalidation;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.screenshot.BasicScreenCaptureProcessor;
import androidx.test.runner.screenshot.ScreenCapture;
import androidx.test.runner.screenshot.Screenshot;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class AMSCustomScreenshot {

    private static final AtomicInteger screenshotId = new AtomicInteger(1);

    public static String capture(String screenshotName) {
        try {
            screenshotName = String.format("%04d-%s", screenshotId.getAndIncrement(), screenshotName);

            ScreenCapture capture = Screenshot.capture();
            capture.setFormat(Bitmap.CompressFormat.PNG);
            capture.setName(screenshotName);

            System.out.println("Capture Name =>"+ capture.getName());

            AMSScreenCaptureProcessor processor = new AMSScreenCaptureProcessor();
            String name = processor.process(capture);

            System.out.println("Actual file Name =>"+ name);


            File file = new File(processor.getPathForFile(screenshotName)
                    + ".png");

            String savedScreenshotPath = file.getPath();

            Log.i("AMSInfo", String.format("Screenshot saved to %s", savedScreenshotPath));

            return name;
        } catch (Exception e) {
            Log.e("AMSInfo", "Failed to capture screenshot", e);
            return "error";
        }
    }

    private static class AMSScreenCaptureProcessor extends BasicScreenCaptureProcessor {

        private static final String AMS_CUSTOM_SCREENSHOTS = "ams_custom_screenshots";

        public AMSScreenCaptureProcessor() {
            super();
            System.out.println("** Save to File");
            File downloadsDir = InstrumentationRegistry.getInstrumentation().
                    getTargetContext().getFilesDir();
            System.out.println("** Data dir" + downloadsDir.getAbsolutePath());
            File screenshotsDir = new File(downloadsDir, AMS_CUSTOM_SCREENSHOTS);
            this.mDefaultScreenshotPath = screenshotsDir;
        }

        public String getPathForFile(String name) {
            File file = new File(mDefaultScreenshotPath, getFilename(name));
            return file.getAbsolutePath();
        }
    }
}
