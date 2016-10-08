package com.zzh.gdut.gduthelper.model.minterface;

/**
 * Created by ZengZeHong on 2016/10/8.
 */

public interface ScoreModel {
    //tag分为按学期查询，按学年查询，在校学习成绩查询这些
    void searchScore(String year , String term , int tag);
}
