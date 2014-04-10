package com.urqa.library.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author seunoh on 2014. 1. 26..
 */
public class Auth implements ToMap {
    private String mApiKey;
    private String mAppVersion;

    public Auth() {
    }

    public Auth(String apiKey, String appVersion) {
        mApiKey = apiKey;
        mAppVersion = appVersion;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public void setApiKey(String apiKey) {
        mApiKey = apiKey;
    }

    public String getAppVersion() {
        return mAppVersion;
    }

    public void setAppVersion(String appVersion) {
        mAppVersion = appVersion;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put(Keys.API_KEY.getKey(), getApiKey());
        object.put(Keys.APP_VERSION.getKey(), getAppVersion());
        return object;
    }
}
