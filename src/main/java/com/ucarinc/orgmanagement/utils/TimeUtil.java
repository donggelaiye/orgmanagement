package com.ucarinc.orgmanagement.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: orgmanagement
 * @description:
 * @author: Wu.tengfei
 * @create: Create in 15:34 2019/8/5
 **/
public class TimeUtil {
    public static Date toDateTime(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateTime = sdf.parse(sdf.format(date));
        return dateTime;
    }
    public static Date toDate(Date date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(sdf.format(date));
        return date1;
    }
}
