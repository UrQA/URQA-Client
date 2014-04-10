package com.urqa.library.net;

import android.content.Context;

import com.urqa.library.UrQAHelper;
import com.urqa.library.collector.AppManager;
import com.urqa.library.common.AndroidReport;
import com.urqa.library.common.ApplicationReport;
import com.urqa.library.common.Auth;
import com.urqa.library.common.CallStackReport;
import com.urqa.library.common.DisplayReport;
import com.urqa.library.common.MemoryReport;
import com.urqa.library.common.ReportFactory;
import com.urqa.library.common.ToMap;
import com.urqa.library.level.ErrorLevel;

import org.json.JSONObject;

import java.security.InvalidKeyException;

/**
 * @author seunoh on 2014. 1. 26..
 */
public final class ExecuteFactory {

    private ExecuteFactory() {
    }


    public static void requestAuth(Context context, String key) {
        final Auth auth = new Auth(key, new AppManager(context).getAppVersionName());
        Request request = new Request(HttpMethod.POST, UrlFactory.create(UrlFactory.Url.CONNECT), new ResponseAdapter() {
            @Override
            public void response(JSONObject response) {
                String session = response.optString(ToMap.Keys.SESSION.getKey());
                if (!"".equals(session)) {
                    UrQAHelper.getInstance().put(UrQAHelper.Keys.API_KEY, auth.getApiKey());
                    UrQAHelper.getInstance().put(UrQAHelper.Keys.APP_VERSION, auth.getAppVersion());
                    UrQAHelper.getInstance().put(UrQAHelper.Keys.SESSION, session);
                } else {
                    errorResponse(new InvalidKeyException("apiKey"));
                }
            }
        });

        request.addParams(auth);
        new RequestExecutor(request);
    }

    public static void requestException(Context context, Throwable e, String tag, ErrorLevel level) {
        requestException(context, e, tag, level, null);

    }

    public static void requestException(Context context, Throwable e, String tag, ErrorLevel level, final ResponseAdapter adapter) {
        Request request = new Request(HttpMethod.POST, UrlFactory.create(UrlFactory.Url.EXCEPTION), new ResponseAdapter() {
            @Override
            public void response(JSONObject response) {
                String instance = response.optString(ToMap.Keys.INSTANCE.getKey());
                if (!"".equals(instance)) {
                    LogRequest logRequest = new LogRequest(new ResponseAdapter(), instance);
                    new RequestExecutor(logRequest);
                } else {
                    errorResponse(new InvalidKeyException("Instance"));
                }

                if (adapter != null) adapter.response(response);
            }

            @Override
            public void errorResponse(Exception e) {
                if (adapter != null) adapter.errorResponse(e);
            }

            @Override
            public void finish() {
                if (adapter != null) adapter.finish();
            }
        });


        ApplicationReport applicationReport = ReportFactory.createApplication(tag, level);
        AndroidReport androidReport = ReportFactory.createAndroid(context);
        CallStackReport callStackReport = ReportFactory.createCallStack(e);
        DisplayReport displayReport = ReportFactory.createDisplay(context);
        MemoryReport memoryReport = ReportFactory.createMemory(context);
        ReportFactory.createLog();

        request.addParams(UrQAHelper.getInstance().getAuth());
        request.addParams(applicationReport);
        request.addParams(androidReport);
        request.addParams(callStackReport);
        request.addParams(displayReport);
        request.addParams(memoryReport);

        new RequestExecutor(request);
    }
}
