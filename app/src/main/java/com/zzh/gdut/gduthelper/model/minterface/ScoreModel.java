package com.zzh.gdut.gduthelper.model.minterface;

import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;

/**
 * Created by ZengZeHong on 2016/10/8.
 */

public interface ScoreModel {
    //tag分为按学期查询，按学年查询，在校学习成绩查询这些
    void searchScore(String year , String term , String tag , ResultListener resultListener , String userName , String userNumber);
}
