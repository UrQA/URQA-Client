package com.urqa.library.collector;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * @author seunoh on 2014. 2. 11..
 */
public final class MemoryManager extends AbstractManager {

    private MemorySize mInternalMemorySize;

    public MemoryManager(Context context) {
        super(context);
        mInternalMemorySize = new MemorySize(Environment.getDataDirectory());
    }

    public boolean isAvailableExternalStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public long getAvailableInternalMemorySize() {
        return mInternalMemorySize.getAvailableMemorySize();
    }

    public long getTotalInternalMemorySize() {
        return mInternalMemorySize.getTotalMemorySize();
    }


    public long getAvailableExternalMemoryMegaByte() {
        return toMegaByte(getAvailableExternalMemorySize());
    }

    public long getAvailableExternalMemorySize() {
        if (isAvailableExternalStorage()) {
            MemorySize memorySize = new MemorySize(Environment.getExternalStorageDirectory());
            return memorySize.getAvailableMemorySize();
        } else {
            return ERROR;
        }
    }

    public long getTotalExternalMemorySize() {
        if (isAvailableExternalStorage()) {
            MemorySize memorySize = new MemorySize(Environment.getExternalStorageDirectory());
            return memorySize.getTotalMemorySize();
        } else {
            return ERROR;
        }
    }

    public long getTotalMemoryMegaByte() {
        return toMegaByte(getTotalMemory());
    }

    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public long getFreeMemoryMegaByte() {
        return toMegaByte(getFreeMemory());
    }

    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }


    public long getMaxMemoryMegaByte() {
        return toMegaByte(getMaxMemory());
    }

    public long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public boolean isSystemLowMemory() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        return memoryInfo.lowMemory;
    }

    private Long toMegaByte(long byteValue) {
        return (byteValue / 1024) / 1024;
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private class MemorySize {
        private StatFs mFs;

        private MemorySize(StatFs fs) {
            mFs = fs;
        }

        private MemorySize(File path) {
            this(new StatFs(path.getPath()));
        }


        private Long getBlockSize() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                return mFs.getBlockSizeLong();
            else {
                return (long) mFs.getBlockSize();
            }
        }


        private Long getAvailableBlocks() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                return mFs.getAvailableBlocksLong();
            else
                return (long) mFs.getAvailableBlocks();
        }

        private Long getBlockCount() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                return mFs.getBlockCountLong();
            else
                return (long) mFs.getBlockCount();
        }


        private Long getAvailableMemorySize() {
            return getAvailableBlocks() * getBlockSize();
        }

        private Long getTotalMemorySize() {
            return getBlockSize() * getBlockCount();
        }
    }
}
