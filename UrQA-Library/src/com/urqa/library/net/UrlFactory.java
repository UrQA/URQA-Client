package com.urqa.library.net;

/**
 * @author seunoh on 2014. 1. 26..
 */
public class UrlFactory {
    private static final String UrQA_URL;

    static {
        UrQA_URL = "http://ur-qa.com";
    }

    public static String create(Url url) {
        return UrQA_URL + url.getUrl();
    }

    public static String create(Url url, String... subUrl) {
        StringBuilder builder = new StringBuilder();
        builder.append(UrQA_URL);
        builder.append(url.getUrl());

        for (String s : subUrl) {
            if (!s.startsWith("/"))
                builder.append("/");
            builder.append(s);
        }
        return builder.toString();
    }


    public enum Url {
        CONNECT("/urqa/client/connect"), //
        EXCEPTION("/urqa/client/send/exception"), //
        NATIVE("/urqa/client/send/exception/native"), //
        LOG("/urqa/client/send/exception/log"), //
        DUMP("/urqa/client/send/exception/dump"), //
        ;

        private final String mUrl;

        private Url(String url) {
            this.mUrl = url;

        }

        public String getUrl() {
            return mUrl;
        }
    }


}
