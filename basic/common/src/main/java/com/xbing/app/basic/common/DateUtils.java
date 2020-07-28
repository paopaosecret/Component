package com.xbing.app.basic.common;

import java.util.Calendar;

/**
 * Created by Administrator on 2017/7/12.
 */

public class DateUtils {

    public static long getDayBegin() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 001);
        return cal.getTimeInMillis();
    }
}
