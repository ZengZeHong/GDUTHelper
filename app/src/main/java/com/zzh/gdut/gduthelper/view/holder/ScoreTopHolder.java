package com.zzh.gdut.gduthelper.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;

/**
 * Created by ZengZeHong on 2016/10/11.
 */

public class ScoreTopHolder extends RecyclerView.ViewHolder {
    private TextView tvGPA;

    public ScoreTopHolder(View itemView) {
        super(itemView);
        tvGPA = (TextView) itemView.findViewById(R.id.tv_gpa);
    }

    public TextView getTvGPA() {
        return tvGPA;
    }

    public void setTvGPA(TextView tvGPA) {
        this.tvGPA = tvGPA;
    }
}
