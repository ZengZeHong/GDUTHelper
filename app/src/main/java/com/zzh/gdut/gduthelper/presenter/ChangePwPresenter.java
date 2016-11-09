package com.zzh.gdut.gduthelper.presenter;

import com.zzh.gdut.gduthelper.base.BasePresenter;
import com.zzh.gdut.gduthelper.model.mimplement.ChangePwModelImp;
import com.zzh.gdut.gduthelper.model.minterface.ChangePwModel;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.view.vinterface.ChangePwInterface;

/**
 * Created by ZengZeHong on 2016/10/13.
 */

public class ChangePwPresenter extends BasePresenter<ChangePwInterface> {
    private ChangePwInterface changePwInterface;
    private ChangePwModel changePwModel;
    private String userName;
    private String userNumber;
    public ChangePwPresenter(ChangePwInterface changePwInterface) {
        this.changePwInterface = changePwInterface;
        changePwModel = new ChangePwModelImp();
    }
    public void initData(String userName , String userNumber){
        this.userName = userName;
        this.userNumber = userNumber;
    }
    public void changePassword(String originalPw, String newPw, String confirmPw) {
        changePwModel.changePassword(userNumber , originalPw, newPw, confirmPw, new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if (changePwInterface != null)
                    changePwInterface.changeSuccess(success);
                if (changePwInterface != null)
                    changePwInterface.changeSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if (changePwInterface != null)
                    changePwInterface.changeFail(fail);
            }
        });
    }
}
