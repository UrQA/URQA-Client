package com.urqa.library.collector;

import android.content.Context;

/**
 * @author seunoh on 2014. 2. 11..
 */
public abstract class AbstractManager {

    public static final int ERROR = -1;

    private Context mContext;


    protected AbstractManager(Context context) {
        mContext = context;
    }


    protected Context getContext() {
        return mContext;
    }
}
