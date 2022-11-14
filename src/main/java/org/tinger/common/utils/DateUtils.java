package org.tinger.common.utils;

import java.util.Date;
import java.util.Objects;

public class DateUtils {
    public static long between(Date date1, Date date2, DateUnit unit) {
        Objects.requireNonNull(date1);
        Objects.requireNonNull(date2);
        return (date1.getTime() - date2.getTime()) / unit.milli;
    }
}
