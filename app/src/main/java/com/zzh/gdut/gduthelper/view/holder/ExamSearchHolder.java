package com.zzh.gdut.gduthelper.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;

/**
 * Created by ZengZeHong on 2016/10/6.
 */

public class ExamSearchHolder extends RecyclerView.ViewHolder {
    private TextView tvName;
    private TextView tvTime;
    private TextView tvPlace;
    private TextView tvSeat;
    public ExamSearchHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_exam_name);
        tvTime = (TextView) itemView.findViewById(R.id.tv_exam_time);
        tvPlace = (TextView) itemView.findViewById(R.id.tv_exam_place);
        tvSeat = (TextView) itemView.findViewById(R.id.tv_exam_seat);
    }


    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvTime() {
        return tvTime;
    }

    public void setTvTime(TextView tvTime) {
        this.tvTime = tvTime;
    }

    public TextView getTvPlace() {
        return tvPlace;
    }

    public void setTvPlace(TextView tvPlace) {
        this.tvPlace = tvPlace;
    }

    public TextView getTvSeat() {
        return tvSeat;
    }

    public void setTvSeat(TextView tvSeat) {
        this.tvSeat = tvSeat;
    }
}
