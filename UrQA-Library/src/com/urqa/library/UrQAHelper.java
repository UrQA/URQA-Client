package com.urqa.library;

import android.content.Context;

import com.urqa.library.common.Auth;

import java.util.HashMap;

/**
 * @author seunoh on 2014. 1. 25..
 */

public final class UrQAHelper extends HashMap<UrQAHelper.Keys, String> {


    /**
	 * 
	 */
	private static final long serialVersionUID = 6824762562131521980L;

	private static volatile UrQAHelper sInstance = null;

    private static Context sContext;
    private boolean mTwice;

    static void init(Context context, UrQAOption option) {
        UrQAHelper.sContext = context;
        UrQAHelper.setOption(option);
        getInstance().mTwice = true;
    }


    public static UrQAHelper getInstance() {
        if (sInstance == null)
            sInstance = new UrQAHelper();
        return sInstance;
    }


    public static Context getContext() {
        if (sContext == null)
            throw new IllegalArgumentException("called method UrQA.newSession()");
        else
            return sContext;
    }

     boolean isTwice() {
        return mTwice;
    }


    public final Auth getAuth() {
        if (getInstance().containsKey(UrQAHelper.Keys.API_KEY)
                && getInstance().containsKey(UrQAHelper.Keys.APP_VERSION)) {

            Auth auth = new Auth();
            auth.setApiKey(getInstance().get(UrQAHelper.Keys.API_KEY));
            auth.setAppVersion(getInstance().get(UrQAHelper.Keys.APP_VERSION));

            return auth;
        } else {
            return new Auth();
        }

    }

    public static final void setOption(UrQAOption option) {
        getInstance().put(Keys.LOG_FILTER, option.getLogFilter());
        getInstance().put(Keys.LOG_LINE, String.valueOf(option.getLogLine()));
        getInstance().put(Keys.LOG_TRANSFER, String.valueOf(option.isTransferLog()));
        getInstance().put(Keys.LOG_TOGGLE, String.valueOf(option.isToggleLogCat()));
    }

    public enum Keys {
        API_KEY, APP_VERSION, SESSION, INSTANCE, LOG_LINE, LOG_TRANSFER, LOG_FILTER, LOG_TOGGLE
    }
}
