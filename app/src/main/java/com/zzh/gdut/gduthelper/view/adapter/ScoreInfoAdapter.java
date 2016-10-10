package com.zzh.gdut.gduthelper.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseRecyclerViewAdapter;
import com.zzh.gdut.gduthelper.bean.ScoreInfo;
import com.zzh.gdut.gduthelper.view.holder.ScoreHolder;
import com.zzh.gdut.gduthelper.view.holder.ScoreTopHolder;

import java.text.DecimalFormat;

/**
 * Created by ZengZeHong on 2016/10/11.
 */

public class ScoreInfoAdapter extends BaseRecyclerViewAdapter<ScoreInfo> {
    private static int TAG_TOP = 0;
    private static int TAG_NORMAL = 1;

    public ScoreInfoAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TAG_NORMAL) {
            view = LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false);
            return new ScoreHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.recycler_score_top_item, parent, false);
            return new ScoreTopHolder(view);
        }
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view) {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_score_item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TAG_TOP) {
            ScoreTopHolder scoreTopHolder = (ScoreTopHolder) holder;
            double average = 0;
            for (ScoreInfo scoreInfo : mList) {
                average += getPointFromScore(scoreInfo.getClassScore());
            }
            if (mList.size() != 0) {
                DecimalFormat df = new DecimalFormat("#.000");
                scoreTopHolder.getTvGPA().setText("平均学分绩点：" + df.format(average / mList.size()));
            }
            else
                scoreTopHolder.getTvGPA().setText("平均学分绩点：0.00");

        } else {
            ScoreHolder scoreHolder = (ScoreHolder) holder;
            scoreHolder.getTvScore().setText(mList.get(position - 1).getClassScore());
            scoreHolder.getTvScoreName().setText(mList.get(position - 1).getClassName());
            scoreHolder.getTvScoreCredit().setText(mList.get(position - 1).getClassCredit());
            scoreHolder.getTvScoreAttr().setText(mList.get(position - 1).getClassAttr());
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TAG_TOP;
        else return TAG_NORMAL;
    }

    @Override
    public int getItemCount() {
        //加入顶部
        return mList.size() + 1;
    }

    /**
     * 换算绩点
     * @param scoreString
     * @return
     */
    private double getPointFromScore(String scoreString) {
        switch (scoreString) {
            case "优秀":
                return 4.5;
            case "良好":
                return 3.5;
            case "中等":
                return 2.5;
            case "及格":
                return 1.5;
            case "不及格":
                return 0;
        }
        Double score = Double.parseDouble(scoreString);

        if (score == 100)
            return 5.0;
        else if (score < 60)
            return 0;
        else if (score >= 60 && score < 70)
            return 1.0 + 0.1 * (score % 60);
        else if (score >= 70 && score < 80)
            return 2.0 + 0.1 * (score % 70);
        else if (score >= 80 && score < 90)
            return 3.0 + 0.1 * (score % 80);
        else if (score >= 90 && score < 100)
            return 4.0 + 0.1 * (score % 90);
        return 0;
    }
}
