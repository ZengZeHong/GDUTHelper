package com.zzh.gdut.gduthelper.model.mimplement;

import com.zzh.gdut.gduthelper.model.minterface.ScoreModel;

/**
 * Created by ZengZeHong on 2016/10/8.
 */

public class ScoreModelImp implements ScoreModel {
    public static final String TAG_YEAR = "按学年查询";
    public static final String TAG_TERM = "按学期查询";
    public static final String TAG_ALL = "全部查询";

    @Override
    public void searchScore(String year, String term, int tag) {

    }
}
