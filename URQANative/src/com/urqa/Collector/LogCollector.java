package com.urqa.Collector;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.urqa.common.StateData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class LogCollector {


	private static String hasReadLogsPermission = null;

	private static boolean hasPermission(String permission, Context context) {

		final PackageManager pm = context.getPackageManager();
		if (pm == null) {
			return false;
		}

		try {
			return pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
		} catch (RuntimeException e) {
			return false;
		}
	}

	private static int getAPILevel() {
		int apiLevel;
		try {
			final Field SDK_INT = Build.VERSION.class.getField("SDK_INT");
			apiLevel = SDK_INT.getInt(null);
		} catch (Exception e) {
			apiLevel = Integer.parseInt(Build.VERSION.SDK);
		}

		return apiLevel;
	}



	public final static String getLog(Context context) {

		if(LogCollector.hasReadLogsPermission == null){
			LogCollector.hasReadLogsPermission = (hasPermission(Manifest.permission.READ_LOGS, context) || (getAPILevel() >= 16))? "TRUE" : "FALSE";
		}

		if(LogCollector.hasReadLogsPermission.equals("FALSE"))
			return "";

		StringBuilder LOGCAT_CMD = new StringBuilder();
	    LOGCAT_CMD.append("logcat").append(" -d").append(" -v").append(" time").append(" tags").append(" *:V");
	    //.append(StateData.LogFilter);
	    
	    Process logcatProc = null;
	    
	    try {
	        logcatProc = Runtime.getRuntime().exec(LOGCAT_CMD.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "";
	    }
	    BufferedReader reader = null;
	    StringBuilder strOutput = new StringBuilder();
	    ArrayList<String> LogList = new ArrayList<String>();
	    try {
	        reader = new BufferedReader(new InputStreamReader(logcatProc.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	LogList.add(line);
	        }
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    int LogLineStart = LogList.size() - StateData.LogLine;
	    if(LogLineStart < 0)
	    	LogLineStart = 0;
	     
	    int LogLineEnd = LogList.size();
	    
	    for(int i = LogLineStart ; i < LogLineEnd; i++)
	    {
	    	strOutput.append(LogList.get(i)).append("\n");
	    }
	    return strOutput.toString();
	}
	
}
