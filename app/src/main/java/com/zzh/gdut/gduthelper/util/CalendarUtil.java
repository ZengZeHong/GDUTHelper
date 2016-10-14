package com.zzh.gdut.gduthelper.util;

import java.util.Calendar;

/**
 * Created by ZengZeHong on 2016/10/14.
 */

public class CalendarUtil {
    private static final String TAG = "CalendarUtil";

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1; //自动加1表示一月，原本是0
    }

    /**
     * 获取指定周数下的月份
     *
     * @param year
     * @param week
     * @return
     */
    public static int getMonth(int year, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * 获取某年最大周数
     */
    public static int getWeekNumOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        return calendar.getMaximum(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定日期下的周数，在一年中是第几周
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static int getWeekFromDay(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定周数下，星期一是第几号
     *
     * @param year
     * @param week
     * @return
     */
    public static int getDayOfWeek(int year,int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 获取对应月份的天数
     */
    public static int getDayNumOfMonth(int year, int month) {
        switch (month) {
            case 0: // 上一年的12月
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13: // 下一年1月
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(year))
                    return 29;
                else
                    return 28;
        }
        return -1;
    }

}
