package com.urqa.library.common;

import android.content.Context;

import com.urqa.library.collector.AppManager;
import com.urqa.library.collector.CallStackCollector;
import com.urqa.library.collector.DisplayManager;
import com.urqa.library.collector.LogCollector;
import com.urqa.library.collector.MemoryManager;
import com.urqa.library.level.ErrorLevel;

public class ReportFactory {


    private static String sLogReport = null;

    public static ApplicationReport createApplication(String tag, ErrorLevel level) {
        return new ApplicationReport.Builder(tag, level).build();
    }

    public static MemoryReport createMemory(Context context) {
        return new MemoryReport.Builder(new MemoryManager(context)).build();
    }

    public static DisplayReport createDisplay(Context context) {
        return new DisplayReport.Builder(new DisplayManager(context)).build();
    }

    public static AndroidReport createAndroid(Context context) {
        return new AndroidReport.Builder(new AppManager(context)).build();
    }

    public static CallStackReport createCallStack(Throwable throwable) {
        return CallStackCollector.parseStackTrace(throwable);
    }

    public static String getLogReport() {
        return sLogReport;
    }

    public static void createLog() {
        sLogReport = LogCollector.getLog();
    }
}
