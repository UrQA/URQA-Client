package com.urqa.library.eventpath;

import com.urqa.library.collector.DateCollector;
import com.urqa.library.common.EventPathReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author JeongSeungsu
 * @since 2013. 8. 12.오후 4:49:57
 */
public class EventPathManager {

    private static volatile EventPathManager sInstance = null;
    private List<EventPathReport> mEventPathReports = null;

    private static final int MAX_EVENT_PATH = 10;

    private EventPathReport[] mErrorEventPathReports = null;
    private int mEventCount = 0;


    private EventPathManager() {
        mEventPathReports = new ArrayList<EventPathReport>();
        mErrorEventPathReports = new EventPathReport[MAX_EVENT_PATH];
    }


    public static EventPathManager getInstance() {
        if (sInstance == null)
            sInstance = new EventPathManager();
        return sInstance;
    }

    public synchronized void create(int step, String label) {
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        EventPathReport.Builder builder = new EventPathReport.Builder(trace[step]);
        builder.setDateTime(DateCollector.getDate());
        builder.setLabel(label);

        EventPathReport eventPathReport = builder.build();

        shiftErrorEventPath();
        mErrorEventPathReports[MAX_EVENT_PATH - 1] = eventPathReport;
        mEventCount++;
        mEventPathReports.add(eventPathReport);
    }

    private void shiftErrorEventPath() {
        System.arraycopy(mErrorEventPathReports, 1, mErrorEventPathReports, 0, MAX_EVENT_PATH - 1);
    }

    public List<EventPathReport> getEventPath() {
        return mEventPathReports;
    }

    public int getEventCount() {
        return mEventCount;
    }

    public List<EventPathReport> getErrorEventPath() {
        List<EventPathReport> eventPathReports = new ArrayList<EventPathReport>();

        int maxCount = mEventCount;
        if (maxCount >= MAX_EVENT_PATH)
            maxCount = MAX_EVENT_PATH;

        eventPathReports.addAll(Arrays.asList(mErrorEventPathReports).subList(MAX_EVENT_PATH - maxCount, MAX_EVENT_PATH));

        return eventPathReports;
    }

    public void clear() {
        getInstance().getEventPath().clear();
    }

    public static List<EventPathReport> getNumberofEventPath(int number) {
        final int size = getInstance().getEventPath().size();
        final int start = (size - number) < 0 ? 0 : (size - number);

        List<EventPathReport> eventPathReports = new ArrayList<EventPathReport>();
        for (int i = start; i < size; i++) {
            eventPathReports.add(getInstance().getEventPath().get(i));
        }

        return eventPathReports;
    }

}
