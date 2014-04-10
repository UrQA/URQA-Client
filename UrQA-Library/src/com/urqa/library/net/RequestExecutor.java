package com.urqa.library.net;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

/**
 * @author seunoh on 2014. 1. 26..
 */
public final class RequestExecutor {

    RequestExecutor(Request request) {
        new Processing(request).execute();
    }

    private static class Processing extends AsyncTask<Void, Void, Response> {

        private Request mRequest;

        private Processing(Request request) {
            mRequest = request;
        }

        @Override
        protected Response doInBackground(Void... params) {
            Response response;
            try {

                RequestProcessing processing = new RequestProcessing();
                NetworkResponse networkResponse = processing.performRequest(mRequest);
                return mRequest.parseNetworkResponse(networkResponse);
            } catch (Exception e) {
                response = Response.error(e);
            }

            return response;
        }


        @Override
        protected void onPostExecute(Response response) {
            final ResponseExecutor responseExecutor = new ResponseExecutor(new Handler(Looper.myLooper()));
            responseExecutor.postResponse(mRequest, response);
        }
    }
}
