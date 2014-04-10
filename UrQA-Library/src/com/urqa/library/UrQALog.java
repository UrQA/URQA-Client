package com.urqa.library;

import android.util.Log;

import com.urqa.library.level.ErrorLevel;
import com.urqa.library.net.ExecuteFactory;
import com.urqa.rank.ErrorRank;

/**
 * @author seunoh on 2014. 1. 26..
 */
public final class UrQALog {

    public static int v(String tag, String msg, Throwable tr) {
        return println(Log.VERBOSE, tag, msg, tr);
    }

    public static int v(String tag, String msg) {
        return println(Log.VERBOSE, tag, msg);
    }

    public static int d(String tag, String msg, Throwable tr) {
        return println(Log.DEBUG, tag, msg, tr);
    }

    public static int d(String tag, String msg) {
        return println(Log.DEBUG, tag, msg);
    }

    public static int i(String tag, String msg, Throwable tr) {
        return println(Log.INFO, tag, msg, tr);
    }

    public static int i(String tag, String msg) {
        return println(Log.INFO, tag, msg);
    }

    public static int w(String tag, String msg, Throwable tr) {
        return println(Log.WARN, tag, msg, tr);
    }

    public static int w(String tag, String msg) {
        return println(Log.WARN, tag, msg);
    }

    public static int e(String tag, String msg, Throwable tr) {
        return println(Log.ERROR, tag, msg, tr);
    }

    public static int e(String tag, String msg) {
        return println(Log.ERROR, tag, msg);
    }


    private static int println(int priority, String tag, String msg) {
        return Log.println(priority, tag, msg);
    }


    private static int println(int priority, String tag, String msg, Throwable throwable) {
        switch (priority) {
            case Log.VERBOSE:
                return Log.v(tag, msg, throwable);
            case Log.DEBUG:
                return Log.d(tag, msg, throwable);
            case Log.INFO:
                return Log.i(tag, msg, throwable);
            case Log.WARN:
                return Log.w(tag, msg, throwable);
            case Log.ERROR:
                return Log.e(tag, msg, throwable);
            default:
                return Log.e(tag, msg, throwable);
        }
    }

    public static void sendException(Exception e) {
        sendException(e, UrQA.TAG, ErrorLevel.CRITICAL);
    }

    public static void sendException(Exception e, String tag) {
        sendException(e, tag, ErrorLevel.CRITICAL);
    }

    public static void sendException(Exception e, String tag, ErrorLevel level) {
        ExecuteFactory.requestException(UrQAHelper.getContext(), e, tag, level);
    }


    /**
     * @deprecated Use {@link com.urqa.library.UrQALog#sendException(Exception, String, com.urqa.library.level.ErrorLevel)} instead.
     */
    public static void sendException(Exception e, String Tag, ErrorRank rank) {
        sendException(e, Tag, ErrorLevel.valueOf(rank));
    }


}
