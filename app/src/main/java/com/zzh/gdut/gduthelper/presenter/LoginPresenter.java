package com.zzh.gdut.gduthelper.presenter;

import com.zzh.gdut.gduthelper.base.BasePresenter;
import com.zzh.gdut.gduthelper.bean.StudentInfo;
import com.zzh.gdut.gduthelper.model.mimplement.LoginModelImp;
import com.zzh.gdut.gduthelper.model.minterface.LoginModel;
import com.zzh.gdut.gduthelper.networkutil.callback.ByteListener;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.view.vinterface.LoginInterface;

/**
 * Created by ZengZeHong on 2016/9/24.
 */

public class LoginPresenter extends BasePresenter<LoginInterface> {
    private LoginModel loginModel;
    private LoginInterface loginInterface;

    public LoginPresenter(LoginInterface loginInterface) {
        loginModel = new LoginModelImp();
        this.loginInterface = loginInterface;
    }

    /**
     * 第一次提交登陆信息
     *
     * @param account
     * @param password
     * @param imgCode
     */
    public void login(String account, String password, String imgCode) {
        loginModel.login(new StudentInfo(account, password, imgCode), new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if (loginInterface != null)
                    loginInterface.loginSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if (loginInterface != null)
                    loginInterface.loginFail(fail);
            }
        });
    }

    /**
     * 第一次登陆后获取主页信息
     *
     * @param path
     */
    public void getInfo(String path) {
        loginModel.getInfo(path, new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if (loginInterface != null)
                    loginInterface.getInfoSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if (loginInterface != null)
                    loginInterface.getInfoFail(fail);
            }
        });
    }

    /**
     * 下载验证码
     */
    public void getImageCode() {
        loginModel.getImageCode(new ByteListener() {
            @Override
            public void setBytesSuccess(byte[] bytes) {
                if (loginInterface != null)
                    loginInterface.getImageCodeSuccess(bytes);
            }

            @Override
            public void setBytesFail(String fail) {
                if (loginInterface != null)
                    loginInterface.getImageCodeFail(fail);
            }
        });
    }

}
