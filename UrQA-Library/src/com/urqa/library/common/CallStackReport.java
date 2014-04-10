package com.urqa.library.common;

import java.util.HashMap;
import java.util.Map;

public class CallStackReport implements ToMap {
    private String mActivityName;
    private String mErrorName;
    private String mClassName;
    private int mLine;
    private String mCallStack;

    public String getActivityName() {
        return mActivityName;
    }

    public void setActivityName(String activityName) {
        mActivityName = activityName;
    }

    public String getErrorName() {
        return mErrorName;
    }

    public void setErrorName(String errorName) {
        mErrorName = errorName;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public int getLine() {
        return mLine;
    }

    public void setLine(int line) {
        mLine = line;
    }

    public String getCallStack() {
        return mCallStack;
    }

    public void setCallStack(String callStack) {
        mCallStack = callStack;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put(Keys.ERROR_ACTIVITY.getKey(), getActivityName());
        object.put(Keys.ERROR_NAME.getKey(), getErrorName());
        object.put(Keys.ERROR_CLASS_NAME.getKey(), getClassName());
        object.put(Keys.ERROR_LINE.getKey(), getLine());
        object.put(Keys.ERROR_CALL_STACK.getKey(), getCallStack());
        return object;
    }
}
