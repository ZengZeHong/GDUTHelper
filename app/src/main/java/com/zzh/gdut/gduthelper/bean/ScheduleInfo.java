package com.zzh.gdut.gduthelper.bean;

/**
 * Created by ZengZeHong on 2016/10/13.
 * 课程实体类
 */

public class ScheduleInfo {
    //显示的坐标轴位置
    private int x;
    private int y;
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

    private int span = 2;
    private String strings[] = new String[]{"", "", "", "", ""};

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

    public void setData() {
        String data = scheduleName + schedulePlace;
        strings[0] = data.substring(0, 2);
        strings[1] = data.substring(3, 5);
        span = 2;
        if (y == 9)
            span = 3;
    }

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public String[] getString() {
        return strings;
    }

}
