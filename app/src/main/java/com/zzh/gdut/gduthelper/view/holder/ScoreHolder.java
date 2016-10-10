package com.zzh.gdut.gduthelper.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;

/**
 * Created by ZengZeHong on 2016/10/11.
 */

public class ScoreHolder extends RecyclerView.ViewHolder {
    private TextView tvScoreName;
    private TextView tvScore;
    private TextView tvScoreAttr;
    private TextView tvScoreCredit;

    public ScoreHolder(View itemView) {
        super(itemView);
        tvScoreName = (TextView) itemView.findViewById(R.id.tv_score_name);
        tvScore = (TextView) itemView.findViewById(R.id.tv_score);
        tvScoreAttr = (TextView) itemView.findViewById(R.id.tv_score_attr);
        tvScoreCredit = (TextView) itemView.findViewById(R.id.tv_score_credit);
    }

    public TextView getTvScoreName() {
        return tvScoreName;
    }

    public void setTvScoreName(TextView tvScoreName) {
        this.tvScoreName = tvScoreName;
    }

    public TextView getTvScore() {
        return tvScore;
    }

    public void setTvScore(TextView tvScore) {
        this.tvScore = tvScore;
    }

    public TextView getTvScoreAttr() {
        return tvScoreAttr;
    }

    public void setTvScoreAttr(TextView tvScoreAttr) {
        this.tvScoreAttr = tvScoreAttr;
    }

    public TextView getTvScoreCredit() {
        return tvScoreCredit;
    }

    public void setTvScoreCredit(TextView tvScoreCredit) {
        this.tvScoreCredit = tvScoreCredit;
    }
}
