package com.zzh.gdut.gduthelper.presenter;

import com.zzh.gdut.gduthelper.base.BasePresenter;
import com.zzh.gdut.gduthelper.model.mimplement.ExamModelmp;
import com.zzh.gdut.gduthelper.model.minterface.ExamModel;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.view.vinterface.ExamInterface;

/**
 * Created by ZengZeHong on 2016/10/8.
 */

public class ExamPresenter extends BasePresenter<ExamInterface> {
    private ExamModel examModel;
    private ExamInterface examInterface;
    public ExamPresenter(ExamInterface examInterface){
        this.examInterface = examInterface;
        examModel = new ExamModelmp();
    }
    public void searchExam(){
        examModel.searchExam(new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if(examInterface != null)
                    examInterface.searchSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if(examInterface != null)
                    examInterface.searchFail(fail);
            }
        });
    }
}
