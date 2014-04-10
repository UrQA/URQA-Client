package com.urqa.library;

/**
 * @author seunoh on 2014. 03. 23..
 */
public class UrQASetting {

    private boolean mToggleLogCat;
    private int mLogLine;
    private boolean mTransferLog;
    private String mLogFilter;

    public boolean isToggleLogCat() {
        return mToggleLogCat;
    }

    public void setToggleLogCat(boolean toggleLogCat) {
        mToggleLogCat = toggleLogCat;
    }

    public int getLogLine() {
        return mLogLine;
    }

    public void setLogLine(int logLine) {
        mLogLine = logLine;
    }

    public boolean isTransferLog() {
        return mTransferLog;
    }

    public void setTransferLog(boolean transferLog) {
        mTransferLog = transferLog;
    }

    public String getLogFilter() {
        return mLogFilter;
    }

    public void setLogFilter(String logFilter) {
        mLogFilter = logFilter;
    }
}
