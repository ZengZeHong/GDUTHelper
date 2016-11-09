package com.zzh.gdut.gduthelper.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;

/**
 * Created by ZengZeHong on 2016/11/7.
 */

public class PopupWindowHolder extends RecyclerView.ViewHolder {
    private int point;
    private TextView tvTerm;
    private RelativeLayout rlTerm;
    public PopupWindowHolder(View itemView) {
        super(itemView);
        tvTerm = (TextView) itemView.findViewById(R.id.tv_term);
        rlTerm = (RelativeLayout) itemView.findViewById(R.id.rl_term);
    }

    public RelativeLayout getRlTerm() {
        return rlTerm;
    }

    public void setRlTerm(RelativeLayout rlTerm) {
        this.rlTerm = rlTerm;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public TextView getTvTerm() {
        return tvTerm;
    }

    public void setTvTerm(TextView tvTerm) {
        this.tvTerm = tvTerm;
    }
}
