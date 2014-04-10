package com.urqa.library.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.urqa.library.UrQA;
import com.urqa.library.collector.DateCollector;
import com.urqa.library.eventpath.EventPathManager;
import com.urqa.library.level.ErrorLevel;


public class ApplicationReport implements ToMap {

    private final String mUrQAVersion;
    private final String mTag;
    private final int mLevel;
    private final String mDateTime;
    private final String mDevice;
    private final String mOSVersion;
    private final List<EventPathReport> mEventPathReportList;


    public ApplicationReport(Builder builder) {
        mUrQAVersion = builder.mUrQAVersion;
        mTag = builder.mTag;
        mLevel = builder.mLevel;
        mDateTime = builder.mDateTime;
        mDevice = builder.mDevice;
        mOSVersion = builder.mOSVersion;
        mEventPathReportList = builder.mEventPathReportList;
    }

    public String getUrQAVersion() {
        return mUrQAVersion;
    }

    public String getTag() {
        return mTag;
    }

    public int getLevel() {
        return mLevel;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public String getDevice() {
        return mDevice;
    }

    public String getOSVersion() {
        return mOSVersion;
    }

    public List<EventPathReport> getEventPathReportList() {
        return mEventPathReportList;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put(Keys.UrQA_VERSION.getKey(), getUrQAVersion());
        object.put(Keys.TAG.getKey(), getTag());
        object.put(Keys.ERROR_LEVEL.getKey(), getLevel());
        object.put(Keys.DATE.getKey(), getDateTime());
        object.put(Keys.DEVICE.getKey(), getDevice());
        object.put(Keys.OS_VERSION.getKey(), getOSVersion());
        object.put(Keys.EVENT_PATH.getKey(), toJsonArray(getEventPathReportList()));
        return object;
    }

    private JSONArray toJsonArray(List<EventPathReport> list) {
        JSONArray jsonArray = new JSONArray();
        for (EventPathReport path : list) {
            jsonArray.put(new JSONObject(path.toMap()));
        }

        return jsonArray;
    }

    public static class Builder {
        private String mUrQAVersion;
        private String mTag;
        private int mLevel;
        private String mDateTime;
        private String mDevice;
        private String mOSVersion;
        private List<EventPathReport> mEventPathReportList;

        public Builder(String tag, ErrorLevel level) {
            this.mUrQAVersion = UrQA.VERSION;
            this.mEventPathReportList = EventPathManager.getInstance().getEventPath();
            this.mDateTime = DateCollector.getDate();
            this.mDevice = android.os.Build.MODEL;
            this.mOSVersion = android.os.Build.VERSION.RELEASE;
            this.mTag = tag;
            this.mLevel = level.getLevel();

        }

        public ApplicationReport build() {
            return new ApplicationReport(this);
        }
    }
}
