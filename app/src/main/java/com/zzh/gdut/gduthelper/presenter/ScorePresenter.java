package com.zzh.gdut.gduthelper.presenter;

import com.zzh.gdut.gduthelper.base.BasePresenter;
import com.zzh.gdut.gduthelper.model.mimplement.ScoreModelImp;
import com.zzh.gdut.gduthelper.model.minterface.ScoreModel;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.view.vinterface.ScoreInfoInterface;

/**
 * Created by ZengZeHong on 2016/10/8.
 */

public class ScorePresenter extends BasePresenter<ScoreInfoInterface> {
    private ScoreModel scoreModel;
    private ScoreInfoInterface scoreInfoInterface;

    public ScorePresenter(ScoreInfoInterface scoreInfoInterface) {
        this.scoreInfoInterface = scoreInfoInterface;
        scoreModel = new ScoreModelImp();
    }

    /**
     * 成绩查询
     * @param year
     * @param term
     * @param tag
     */
    public void searchScore(String year, String term, String tag) {
        scoreModel.searchScore(year, term, tag, new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                scoreInfoInterface.getScoreSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                scoreInfoInterface.getScoreFail(fail);
            }
        });
    }
}
