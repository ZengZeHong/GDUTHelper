package com.zzh.gdut.gduthelper.presenter;

import com.zzh.gdut.gduthelper.base.BasePresenter;
import com.zzh.gdut.gduthelper.model.mimplement.ScheduleModelImp;
import com.zzh.gdut.gduthelper.model.minterface.ScheduleModel;
import com.zzh.gdut.gduthelper.networkutil.callback.ResultListener;
import com.zzh.gdut.gduthelper.view.vinterface.ScheduleInterface;

/**
 * Created by ZengZeHong on 2016/10/15.
 */

public class SchedulePresenter extends BasePresenter<ScheduleInterface> {
    private ScheduleInterface scheduleInterface;
    private ScheduleModel scheduleModel;

    public SchedulePresenter(ScheduleInterface scheduleInterface) {
        this.scheduleInterface = scheduleInterface;
        scheduleModel = new ScheduleModelImp();
    }

    public void getSchedule(String year, String term) {
        scheduleModel.getSchedule(year, term, new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if (scheduleInterface != null)
                    scheduleInterface.getScheduleSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if (scheduleInterface != null)
                    scheduleInterface.getScheduleFail(fail);
            }
        });
    }

    public void getSchedule() {
        scheduleModel.getSchedule(new ResultListener() {
            @Override
            public void onResultSuccess(String success) {
                if (scheduleInterface != null)
                    scheduleInterface.getScheduleSuccess(success);
            }

            @Override
            public void onResultFail(String fail) {
                if (scheduleInterface != null)
                    scheduleInterface.getScheduleFail(fail);
            }
        });
    }
}
