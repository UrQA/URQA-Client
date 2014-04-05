package com.urqa.exceptionhandler;

import android.os.Looper;

import com.urqa.Collector.ErrorReport;
import com.urqa.Collector.ErrorReportFactory;
import com.urqa.common.SendErrorProcess;
import com.urqa.common.StateData;
import com.urqa.rank.ErrorRank;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler{

	Thread.UncaughtExceptionHandler mUncaughtExceptionHandler;
	
	public ExceptionHandler() {
		// TODO Auto-generated constructor stub
		mUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
	
		ErrorReport report = ErrorReportFactory.CreateErrorReport(ex, "", ErrorRank.Unhandle, StateData.AppContext);
		
		SendErrorProcess errprocess = new SendErrorProcess(report, "client/send/exception");
		errprocess.start();
		
        /**
         * Android App 강제 종료 문제
         */
        //		m_DefaultExceptionHandler.uncaughtException(thread, ex);
		
        //		android.os.Process.killProcess(android.os.Process.myPid());
        //        System.exit(0);
        
        
        // MainThread 에서 Error 발생시 종료 함.
        exitMainThreadApplication(thread, ex);
    }

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
    
    private void exitMainThreadApplication(Thread thread, Throwable throwable) {
        if (thread == Looper.getMainLooper().getThread()) {
            mUncaughtExceptionHandler.uncaughtException(thread, throwable);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

}
