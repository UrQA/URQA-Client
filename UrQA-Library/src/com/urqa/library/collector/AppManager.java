package com.urqa.library.collector;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.util.Locale;

/**
 * @author seunoh
 */
public final class AppManager extends AbstractManager {

    public AppManager(Context context) {
        super(context);
    }


    public boolean isWifi() {
        return getNetwork(ConnectivityManager.TYPE_WIFI);
    }

    public boolean isMobile() {
        return getNetwork(ConnectivityManager.TYPE_MOBILE);
    }

    private boolean getNetwork(int type) {
        if (checkNetworkState()) {
            ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getNetworkInfo(type);
            return info != null && info.isConnected();
        } else {
            return false;
        }

    }

    private boolean checkNetworkState() {
        return checkPermission(Manifest.permission.ACCESS_NETWORK_STATE);
    }


    public String getAppVersionName() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;

        }
    }

    private PackageManager getPackageManager() {
        return this.getContext().getPackageManager() != null ? getContext().getPackageManager() : null;
    }

    public String getPackageName() {
        return this.getContext().getPackageName();
    }


    public boolean checkPermission(String permission) {
        return checkPermission(getPackageManager(), getPackageName(), permission);
    }

    private boolean checkPermission(PackageManager packageManager, String packageName, String permission) {
        return packageManager.checkPermission(permission, packageName) == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
    }


    public int getBatteryLevel() {
        Intent intent = getContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED)); // 배터리 값을 받는 인텐트 변수
        return intent != null ? intent.getIntExtra("level", ERROR) : ERROR;
    }

    /**
     * 나라 코드를 가져온다.
     *
     * @return 나라코드가 제대로 안될시 Unknown 반환 아니면 kr 같은 나라코드 반환
     */
    public String getCountry() {
        final String country = Locale.getDefault().getCountry();

        if ("".equals(country))
            return "unknown";
        else
            return country;
    }

    /**
     * Gps 상태를 가져온다.
     *
     * @return true 사용중 false 면 비사용
     */
    public boolean isGps() {
        if (checkLocation()) {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } else {
            return false;
        }
    }

    private boolean checkLocation() {
        return checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
    }

    public boolean isSuperUser() {
        final String rootPaths[] = {
                "/sbin/",
                "/system/bin/",
                "/system/xbin/",
                "/data/local/xbin/",
                "/data/local/bin/",
                "/system/sd/xbin/",
                "/system/bin/failsafe/",
                "/data/local/"
        };

        for (String path : rootPaths) {
            File file = new File(path + "su");
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }


    public String getLinuxKernelVersion() {
        return System.getProperty("os.version");
    }

    public String getDisplayLanguage() {
        return Locale.getDefault().getDisplayLanguage();
    }
}
