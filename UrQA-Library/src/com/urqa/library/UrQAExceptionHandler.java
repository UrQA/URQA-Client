package com.urqa.library;

import com.urqa.library.level.ErrorLevel;
import com.urqa.library.net.ExecuteFactory;

public final class UrQAExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler mUserExceptionHandler = null;

    private UrQAExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    private UrQAExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        this();
        mUserExceptionHandler = handler;
    }


    public static void createInstance() {
        new UrQAExceptionHandler();
    }

    public static void createInstance(Thread.UncaughtExceptionHandler handler) {
        new UrQAExceptionHandler(handler);
    }

    @Override
    public final void uncaughtException(Thread thread, Throwable throwable) {
        if (mUserExceptionHandler != null)
            mUserExceptionHandler.uncaughtException(thread, throwable);
        sendException(throwable);
    }

    private void sendException(Throwable throwable) {
        ExecuteFactory.requestException(UrQAHelper.getContext(), throwable, UrQA.TAG, ErrorLevel.UN_HANDLE);
    }
}
