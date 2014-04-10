package com.urqa.library.net;

import com.urqa.library.common.ReportFactory;

/**
 * @author seunoh on 2014. 1. 26..
 */
public final class LogRequest extends Request {


    public LogRequest(ResponseAdapter adapter, String... url) {
        super(HttpMethod.POST, UrlFactory.create(UrlFactory.Url.LOG, url), adapter);
    }

    @Override
    public byte[] getBody() {
        try {
            return ReportFactory.getLogReport().getBytes(PROTOCOL_CHARSET);
        } catch (Exception e) {
            fail(e);
            return null;
        }
    }

    @Override
    public String getHeaderContentType() {
        return PROTOCOL_CONTENT_TYPE_TEXT;
    }
}
