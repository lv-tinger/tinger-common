package org.tinger.common.utils;

public enum DateUnit {
    MILLISECOND(1),
    SECOND(1000),
    MINUTE(SECOND.milli * 60),
    HOUR(MINUTE.milli * 60),
    DAY(HOUR.milli * 24),
    WEEK(DAY.milli * 7);

    public final long milli;

    DateUnit(long Milli) {
        this.milli = Milli;
    }
}
