package com.zzh.gdut.gduthelper.presenter;

import com.zzh.gdut.gduthelper.base.BasePresenter;
import com.zzh.gdut.gduthelper.model.mimplement.PersonInfoModelImp;
import com.zzh.gdut.gduthelper.model.minterface.PersonInfoModel;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.view.vinterface.PersonInfoInterface;

/**
 * Created by ZengZeHong on 2016/9/26.
 */

public class PersonInfoPresenter extends BasePresenter<PersonInfoInterface> {
    private PersonInfoModel personInfoModel;
    public PersonInfoInterface personInfoInterface;

    public PersonInfoPresenter(PersonInfoInterface personInfoInterface) {
        this.personInfoInterface = personInfoInterface;
        personInfoModel = new PersonInfoModelImp();
    }

    public void getPersonInfoData() {
        personInfoModel.getPersonInfoData(new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if (personInfoInterface != null)
                    personInfoInterface.getInfoSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if (personInfoInterface != null)
                    personInfoInterface.getInfoFail(fail);
            }
        });
    }

    public void getData(String path){
        personInfoModel.submitData(path, new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if (personInfoInterface != null)
                    personInfoInterface.submitSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if (personInfoInterface != null)
                    personInfoInterface.submitFail(fail);
            }
        });
    }

    public void getImageHead() {
        personInfoModel.getUserImageHead(new ByteListener() {
            @Override
            public void setBytesSuccess(byte[] bytes) {
                if (personInfoInterface != null)
                    personInfoInterface.getImageHeadSuccess(bytes);
            }

            @Override
            public void setBytesFail(String fail) {
                if (personInfoInterface != null)
                    personInfoInterface.getImageHeadFail(fail);
            }
        });
    }
}
