package com.zzh.gdut.gduthelper.bean;

/**
 * Created by ZengZeHong on 2016/10/13.
 * 课程实体类
 */

public class ScheduleInfo {
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
}
