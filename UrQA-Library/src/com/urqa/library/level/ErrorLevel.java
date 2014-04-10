package com.urqa.library.level;

import com.urqa.rank.ErrorRank;

public enum ErrorLevel {
    NOTHING(-1), UN_HANDLE(0), NATIVE(1), CRITICAL(2), MAJOR(3), MINOR(4);

    private final int mLevel;

    private ErrorLevel(int level) {
        this.mLevel = level;
    }

    public int getLevel() {
        return mLevel;
    }


    public static ErrorLevel valueOf(ErrorRank rank) {
        ErrorLevel[] errorLevels = values();
        ErrorLevel errorLevel = ErrorLevel.NOTHING;
        for (ErrorLevel level : errorLevels) {
            if (level.getLevel() == rank.value())
                errorLevel = level;

        }

        return errorLevel;
    }
}
