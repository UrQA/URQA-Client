package com.urqa.library.net;

import com.urqa.library.common.ToMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author seunoh on 2014. 1. 26..
 */
public class Request {

    /**
     * Default encoding for POST or PUT parameters. See {@link #getParamsEncoding()}.
     */
    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    /**
     * Charset for request.
     */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    protected static final String PROTOCOL_CONTENT_TYPE_JSON =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    protected static final String PROTOCOL_CONTENT_TYPE_TEXT =
            String.format("text/plain; charset=%s", PROTOCOL_CHARSET);


    private static final int TIMEOUT_MS = 20000;
    private final HttpMethod mMethod;
    private final String mUrl;
    private Map<String, Object> mParams;
    private ResponseAdapter mAdapter;


    private Proxy mProxy;

    public Request(HttpMethod method, String url, ResponseAdapter adapter) {
        this.mMethod = method;
        this.mUrl = url;
        this.mAdapter = adapter;


        mParams = new HashMap<String, Object>();
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getTimeoutMs() {
        return TIMEOUT_MS;
    }

    public void success(JSONObject s) {
        if (mAdapter != null)
        	mAdapter.response(s);
    }

    public void fail(Exception e) {
        if (mAdapter != null)
        	mAdapter.errorResponse(e);
    }

    public void finish() {
        if (mAdapter != null)
        	mAdapter.finish();
    }

    public void addParams(ToMap toMap) {
        mParams.putAll(toMap.toMap());
    }

    public Map<String, Object> getParams() {
        return mParams;
    }


    public JSONObject getParamToJson() {
        Map<String, Object> params = getParams();
        return new JSONObject(params);

    }

    protected String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public String getHeaderContentType() {
        return PROTOCOL_CONTENT_TYPE_JSON;
    }

    /**
     * Returns the raw POST or PUT body to be sent.
     */
    public byte[] getBody() {
        try {
            return getParamToJson().toString().getBytes(PROTOCOL_CHARSET);
        } catch (Exception e) {
            fail(e);
            return null;
        }

    }

    public Proxy getProxy() {
        return mProxy;
    }

    @SuppressWarnings("unused")
    private void setProxy(Proxy proxy) {
        mProxy = proxy;
    }

    public Response parseNetworkResponse(NetworkResponse response) {
        String jsonString = null;
        try {
            jsonString = new String(response.data, getParamsEncoding());
            return Response.success(new JSONObject(jsonString));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new Exception(e));
        } catch (JSONException je) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("data", jsonString);
            return Response.success(new JSONObject(map));
        }

    }
}
