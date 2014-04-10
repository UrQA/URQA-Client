package com.urqa.library.net;

import org.json.JSONObject;

/**
 * @author seunoh on 2014. 1. 26..
 */
public class Response {

    public final JSONObject result;

    public final Exception exception;

    public Response(JSONObject result) {
        this.result = result;
        this.exception = null;
    }


    private Response(Exception error) {
        this.result = null;
        this.exception = error;
    }

    /**
     * Returns a successful response containing the parsed result.
     */
    public static Response success(JSONObject result) {
        return new Response(result);
    }

    public static Response error(Exception error) {
        return new Response(error);
    }

    public boolean isSuccess() {
        return exception == null;
    }
}
