package com.zzh.gdut.gduthelper.bean;

/**
 * Created by ZengZeHong on 2016/10/13.
 * 课程实体类
 */

public class ScheduleInfo implements Comparable {
    //显示的坐标轴位置
    private int x;
    private int y;
    private int span = 2;
    //课程名字
    private String scheduleName;
    //地点
    private String schedulePlace;
    //老师
    private String scheduleTeacher;
    //时间
    private String scheduleTime;
    //周数
    private String scheduleWeek;
    private int backgroundColor ;
    private int textColor;

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getX() {
        return x;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public String getSchedulePlace() {
        return schedulePlace;
    }

    public void setSchedulePlace(String schedulePlace) {
        this.schedulePlace = schedulePlace;
    }

    public String getScheduleTeacher() {
        return scheduleTeacher;
    }

    public void setScheduleTeacher(String scheduleTeacher) {
        this.scheduleTeacher = scheduleTeacher;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getScheduleWeek() {
        return scheduleWeek;
    }

    public void setScheduleWeek(String scheduleWeek) {
        this.scheduleWeek = scheduleWeek;
    }


    public int getSpan() {
        if(y == 9)
            span = 3;
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public String getString() {
        return scheduleName + "@" + schedulePlace;
    }


    @Override
    public String toString() {
        String data = scheduleName + " " + schedulePlace + " " + scheduleTeacher + " " + scheduleTime + ">>" + x + ">>" + y;
        return data;
    }

    @Override
    public int compareTo(Object another) {
        String timeWeek = scheduleTime.substring(scheduleTime.indexOf("{") + 1, scheduleTime.indexOf("}"));
        String[] range = timeWeek.substring(timeWeek.indexOf("第") + 1, timeWeek.indexOf("周")).split("-");
        if (another instanceof ScheduleInfo) {
            ScheduleInfo scheduleInfo = (ScheduleInfo) another;
            String timeWeekAn = scheduleInfo.getScheduleTime().substring(scheduleInfo.getScheduleTime().indexOf("{") + 1, scheduleInfo.getScheduleTime().indexOf("}"));
            String[] rangeAn = timeWeekAn.substring(timeWeekAn.indexOf("第") + 1, timeWeekAn.indexOf("周")).split("-");
            if (Integer.parseInt(range[1]) > Integer.parseInt(rangeAn[0]))
                return 1; //按低到高排序
            else return -1;
        }
        return 0;
    }
}
