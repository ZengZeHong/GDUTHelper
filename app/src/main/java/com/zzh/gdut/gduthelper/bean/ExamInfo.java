package com.zzh.gdut.gduthelper.bean;

/**
 * Created by ZengZeHong on 2016/10/6.
 * 考试安排
 */

public class ExamInfo {
    private String examName;
    private String examTime;
    private String examPlace;
    private String examSeat;

    public ExamInfo(String examName, String examTime, String examPlace, String examSeat) {
        this.examName = examName;
        this.examTime = examTime;
        this.examPlace = examPlace;
        this.examSeat = examSeat;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getExamPlace() {
        return examPlace;
    }

    public void setExamPlace(String examPlace) {
        this.examPlace = examPlace;
    }

    public String getExamSeat() {
        return examSeat;
    }

    public void setExamSeat(String examSeat) {
        this.examSeat = examSeat;
    }
}
