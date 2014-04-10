package com.urqa.library.collector;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author JeongSeungsu on 2012. 11. 15.오전 6:01:42
 * @author seunoh on 2014. 2. 11..
 */
public final class DisplayManager extends AbstractManager {

    public DisplayManager(Context context) {
        super(context);
    }

    /**
     * 가로 스크린 사이즈를 가져온다.
     *
     * @return 가로 스크린 크기
     */
    public int getWidthPixels() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * 세로 스크린 사이즈를 가져온다.
     *
     * @return 세로 스크린 크기
     */
    public int getHeightPixels() {
        return getDisplayMetrics().heightPixels;
    }

    public float getXDpi() {
        return getDisplayMetrics().xdpi;
    }

    public float getYDpi() {
        return getDisplayMetrics().ydpi;
    }

    /**
     * @return 0 세로 1 가로
     */
    public int getOrientation() {
        Display display = ((WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
            return display.getRotation();
        else
            return display.getOrientation();
    }


    private DisplayMetrics getDisplayMetrics() {
        return getContext().getResources().getDisplayMetrics();
    }
}
