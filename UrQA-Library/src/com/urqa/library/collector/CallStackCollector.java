package com.urqa.library.collector;

import android.app.Activity;

import com.urqa.library.common.CallStackReport;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class CallStackCollector {

    public static String getCallStack(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);

        throwable.printStackTrace(printWriter);
        String callStackString = writer.toString();
        printWriter.close();

        return callStackString;
    }

    public static CallStackReport parseStackTrace(Throwable throwable) {
        return parseStackTrace(throwable, getCallStack(throwable));
    }

    public static CallStackReport parseStackTrace(Throwable errorThrow, String callStack) {
        Throwable recordThrowable;
        if (errorThrow.getCause() != null) {
            recordThrowable = errorThrow.getCause();
        } else {
            recordThrowable = errorThrow;
        }

        CallStackReport data = new CallStackReport();
        String[] errorName = callStack.split("\n");
        data.setErrorName(errorName[0]);

        StackTraceElement[] ErrorElements = recordThrowable.getStackTrace();

        data.setClassName(ErrorElements[0].getClassName());
        data.setLine(ErrorElements[0].getLineNumber());
        data.setActivityName(searchCallStackInActivity(ErrorElements));
        data.setCallStack(callStack);

        return data;
    }

    public static String searchCallStackInActivity(StackTraceElement[] ErrorElements) {
        String activityClassName = "";
        for (StackTraceElement ErrorElement : ErrorElements) {
            String name = ErrorElement.getClassName();
            try {
                Class forName = Class.forName(name);
                if (Activity.class.isAssignableFrom(forName)) {
                    activityClassName = name;
                    break;
                }
            } catch (Exception e) {
                activityClassName = "";
            }
        }
        return activityClassName;
    }
}
