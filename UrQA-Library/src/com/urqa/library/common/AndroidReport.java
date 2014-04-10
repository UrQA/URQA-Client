package com.urqa.library.common;

import com.urqa.library.collector.AppManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author seunoh on 2014. 2. 28..
 */
public class AndroidReport implements ToMap {


    private final int mGpsOn;
    private final int mWifiOn;
    private final int mMobileOn;
    private final int mBatteryLevel;
    private final int mRooted;
    private final String mKernelVersion;
    private final String mLocale;
    private final String mCountry;

    public AndroidReport(Builder builder) {
        mGpsOn = builder.mGpsOn;
        mWifiOn = builder.mWifiOn;
        mMobileOn = builder.mMobileOn;
        mBatteryLevel = builder.mBatteryLevel;
        mRooted = builder.mRooted;
        mKernelVersion = builder.mKernelVersion;
        mLocale = builder.mLocale;
        mCountry = builder.mCountry;
    }

    public int getGpsOn() {
        return mGpsOn;
    }

    public int getWifiOn() {
        return mWifiOn;
    }

    public int getMobileOn() {
        return mMobileOn;
    }

    public int getBatteryLevel() {
        return mBatteryLevel;
    }

    public int getRooted() {
        return mRooted;
    }

    public String getKernelVersion() {
        return mKernelVersion;
    }

    public String getLocale() {
        return mLocale;
    }

    public String getCountry() {
        return mCountry;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put(Keys.GPS.getKey(), getGpsOn());
        object.put(Keys.WIFI.getKey(), getWifiOn());
        object.put(Keys.MOBILE.getKey(), getMobileOn());
        object.put(Keys.BATTERY_LEVEL.getKey(), getBatteryLevel());
        object.put(Keys.SUPER_USER.getKey(), getRooted());
        object.put(Keys.KERNEL_VERSION.getKey(), getKernelVersion());
        object.put(Keys.LOCALE.getKey(), getLocale());
        object.put(Keys.COUNTRY.getKey(), getCountry());
        return object;
    }

    public static class Builder {
        private int mGpsOn;
        private int mWifiOn;
        private int mMobileOn;
        private int mBatteryLevel;
        private int mRooted;
        private String mKernelVersion;
        private String mLocale;
        private String mCountry;

        public Builder(AppManager manager) {
            this.mGpsOn = manager.isGps() ? 1 : 0;
            this.mWifiOn = manager.isWifi() ? 1 : 0;
            this.mMobileOn = manager.isMobile() ? 1 : 0;
            this.mBatteryLevel = manager.getBatteryLevel();
            this.mRooted = manager.isSuperUser() ? 1 : 0;
            this.mKernelVersion = manager.getLinuxKernelVersion();
            this.mLocale = manager.getDisplayLanguage();
            this.mCountry = manager.getCountry();

        }


        public AndroidReport build() {
            return new AndroidReport(this);
        }

    }
}
