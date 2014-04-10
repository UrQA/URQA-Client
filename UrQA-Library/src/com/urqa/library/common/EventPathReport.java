package com.urqa.library.common;

import java.util.HashMap;
import java.util.Map;

public class EventPathReport implements ToMap {

    private final String mDateTime;

    private final String mEventClassName;

    private final String mEventMethodName;

    private final int mEventLine;

    private final String mLabel;

    public EventPathReport(Builder builder) {
        mDateTime = builder.mDateTime;
        mEventClassName = builder.mEventClassName;
        mEventMethodName = builder.mEventMethodName;
        mEventLine = builder.mEventLine;
        mLabel = builder.mLabel;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public String getEventClassName() {
        return mEventClassName;
    }

    public String getEventMethodName() {
        return mEventMethodName;
    }

    public int getEventLine() {
        return mEventLine;
    }

    public String getLabel() {
        return mLabel;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put(Keys.EVENT_DATE.getKey(), getDateTime());
        object.put(Keys.EVENT_CLASS_NAME.getKey(), getEventClassName());
        object.put(Keys.EVENT_METHOD_NAME.getKey(), getEventMethodName());
        object.put(Keys.EVENT_LINE.getKey(), String.valueOf(getEventLine()));
        object.put(Keys.EVENT_LABEL.getKey(), getLabel());
        return object;
    }

    public static class Builder {
        private String mDateTime;
        private String mEventClassName;
        private String mEventMethodName;
        private int mEventLine;
        private String mLabel;

        public Builder(StackTraceElement element) {
            setEventClassName(element.getClassName());
            setEventMethodName(element.getMethodName());
            setEventLine(element.getLineNumber());
        }

        public Builder setDateTime(String dateTime) {
            mDateTime = dateTime;
            return this;
        }

        public Builder setEventClassName(String eventClassName) {
            mEventClassName = eventClassName;
            return this;
        }

        public Builder setEventMethodName(String eventMethodName) {
            mEventMethodName = eventMethodName;
            return this;
        }


        public Builder setEventLine(int eventLine) {
            mEventLine = eventLine;
            return this;
        }

        public Builder setLabel(String label) {
            mLabel = label;
            return this;
        }


        public EventPathReport build() {
            return new EventPathReport(this);
        }
    }
}
