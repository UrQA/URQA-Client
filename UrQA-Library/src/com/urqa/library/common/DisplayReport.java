package com.urqa.library.common;

import java.util.HashMap;
import java.util.Map;

import com.urqa.library.collector.DisplayManager;

/**
 * @author seunoh on 2014. 2. 28..
 */
public class DisplayReport implements ToMap {

    private final int mWidth;
    private final int mHeight;
    private final float mXDpi;
    private final float mYDpi;
    private final int mOrientation;


    public DisplayReport(Builder builder) {
        mWidth = builder.mWidth;
        mHeight = builder.mHeight;
        mXDpi = builder.mXDpi;
        mYDpi = builder.mYDpi;
        mOrientation = builder.mOrientation;
    }


    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public float getXDpi() {
        return mXDpi;
    }

    public float getYDpi() {
        return mYDpi;
    }

    public int getOrientation() {
        return mOrientation;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put(Keys.DISPLAY_WIDTH.getKey(), getWidth());
        object.put(Keys.DISPLAY_HEIGHT.getKey(), getHeight());
        object.put(Keys.DISPLAY_XDPI.getKey(), getXDpi());
        object.put(Keys.DISPLAY_YDPI.getKey(), getYDpi());
        object.put(Keys.DISPLAY_ORIENTATION.getKey(), getOrientation());
        return object;
    }

    public static class Builder {
        private int mWidth;
        private int mHeight;
        private float mXDpi;
        private float mYDpi;
        private int mOrientation;

        public Builder(DisplayManager manager) {
            this.mWidth = manager.getWidthPixels();
            this.mHeight = manager.getHeightPixels();
            this.mXDpi = manager.getXDpi();
            this.mYDpi = manager.getYDpi();
            this.mOrientation = manager.getOrientation();
        }


        public DisplayReport build() {
            return new DisplayReport(this);
        }
    }


}
