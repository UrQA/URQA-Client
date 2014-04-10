package com.urqa.library;

import android.content.Context;
import android.util.Log;

import com.urqa.library.eventpath.EventPathManager;
import com.urqa.library.net.ErrorNativeRequest;
import com.urqa.library.net.ExecuteFactory;

import java.io.File;

/**
 * @author seunoh on 2014. 1. 25..
 */
public final class UrQA {

    public static final String TAG = "UrQA-Library";
    public static final String VERSION = "0.92";

    public synchronized static void newSession(Context context, String apiKey) {
        if (context == null)
            throw new IllegalArgumentException("Context is null");

        if (apiKey == null || "".equals(apiKey))
            throw new IllegalArgumentException("ApiKey must not be empty: " + apiKey);

        if (!UrQAHelper.getInstance().isTwice()) {
            UrQAHelper.init(context, new UrQASetting());
            UrQAExceptionHandler.createInstance();
            EventPathManager.getInstance().clear();
            start(context, apiKey);
        } else {
            Log.w(TAG, new IllegalAccessException("Method is called twice"));
        }
    }


    private static void start(Context context, String apiKey) {
        ExecuteFactory.requestAuth(context, apiKey);
    }


    public synchronized static void leaveBreadCrumb() {
        EventPathManager.getInstance().create(2, "");
    }

    public synchronized static void leaveBreadCrumb(String tag) {
        EventPathManager.getInstance().create(2, tag);
    }

    public static void nativeCrashCallback(String str) {
        // TODO errorNativeRequest
        ErrorNativeRequest errorNativeRequest = new ErrorNativeRequest(str);
        errorNativeRequest.start();

        try {
            errorNativeRequest.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * use Android NDK
     *
     * @return android cache path
     */
    public static String getCacheDirPath() {
        File file = UrQAHelper.getContext().getCacheDir();
        return file != null ? file.getAbsolutePath() : null;
    }
}
