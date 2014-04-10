package com.urqa.library.common;

import com.urqa.library.collector.MemoryManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author seunoh on 2014. 2. 28..
 */
public class MemoryReport implements ToMap {
    private final int mAvailableExternal;
    private final int mMemoryTotal;
    private final int mMemoryFree;
    private final int mMemoryMax;
    private final int mSystemMemoryLow;


    public MemoryReport(Builder builder) {
        mAvailableExternal = (int) builder.mAvailableExternal;
        mMemoryTotal = (int) builder.mMemoryTotal;
        mMemoryFree = (int) builder.mMemoryFree;
        mMemoryMax = (int) builder.mMemoryMax;
        mSystemMemoryLow = (int) builder.mSystemMemoryLow;
    }


    public long getAvailableExternal() {
        return mAvailableExternal;
    }

    public long getMemoryTotal() {
        return mMemoryTotal;
    }

    public long getMemoryFree() {
        return mMemoryFree;
    }

    public long getMemoryMax() {
        return mMemoryMax;
    }

    public long getSystemMemoryLow() {
        return mSystemMemoryLow;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> object = new HashMap<String, Object>();
        object.put(Keys.MEMORY_AVAILABLE_EXTERNAL.getKey(), getAvailableExternal());
        object.put(Keys.MEMORY_TOTAL.getKey(), getMemoryTotal());
        object.put(Keys.MEMORY_FREE.getKey(), getMemoryFree());
        object.put(Keys.MEMORY_MAX.getKey(), getMemoryMax());
        object.put(Keys.SYSTEM_MEMORY_LOW.getKey(), getSystemMemoryLow());
        return object;
    }

    public static class Builder {

        private long mAvailableExternal;
        private long mMemoryTotal;
        private long mMemoryFree;
        private long mMemoryMax;
        private long mSystemMemoryLow;

        public Builder(MemoryManager manager) {
            mAvailableExternal = manager.getAvailableExternalMemoryMegaByte();
            mMemoryTotal = manager.getTotalMemoryMegaByte();
            mMemoryFree = manager.getFreeMemoryMegaByte();
            mMemoryMax = manager.getMaxMemoryMegaByte();
            mSystemMemoryLow = manager.isSystemLowMemory() ? 1 : 0;
        }


        public MemoryReport build() {
            return new MemoryReport(this);
        }
    }
}
