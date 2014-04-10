package com.urqa.library.net;

import android.os.Handler;

import java.util.concurrent.Executor;

/**
 * @author seunoh on 2014. 1. 26..
 */
public class ResponseExecutor {

    private Executor mExecutor;


    public ResponseExecutor(final Handler handler) {
        // Make an Executor that just wraps the handler.
        mExecutor = new Executor() {
            @Override
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }



    public void postResponse(Request request, Response response) {
        mExecutor.execute(new ResponseRunnable(request, response));
    }

    private class ResponseRunnable implements Runnable {
        private final Request mRequest;
        private final Response mResponse;

        public ResponseRunnable(Request request, Response response) {
            mRequest = request;
            mResponse = response;
        }

        @Override
        public void run() {
            if (mResponse.isSuccess()) {
                mRequest.success(mResponse.result);
            } else {
                mRequest.fail(mResponse.exception);
            }
            mRequest.finish();
        }
    }
}
