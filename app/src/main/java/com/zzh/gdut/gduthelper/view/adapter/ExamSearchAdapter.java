package com.zzh.gdut.gduthelper.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseRecyclerViewAdapter;
import com.zzh.gdut.gduthelper.bean.ExamInfo;
import com.zzh.gdut.gduthelper.view.holder.ExamSearchHolder;

/**
 * Created by ZengZeHong on 2016/10/6.
 */

public class ExamSearchAdapter extends BaseRecyclerViewAdapter<ExamInfo> {
    public ExamSearchAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view) {
        return new ExamSearchHolder(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_exam_item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ExamSearchHolder examSearchHolder = (ExamSearchHolder) holder;
        examSearchHolder.getTvName().setText(mList.get(position).getExamName());
        examSearchHolder.getTvPlace().setText(mList.get(position).getExamPlace());
        examSearchHolder.getTvTime().setText(mList.get(position).getExamTime());
        examSearchHolder.getTvSeat().setText(mList.get(position).getExamSeat());
        if (position  == 0) {
            examSearchHolder.getImgBottom().setVisibility(View.VISIBLE);
            examSearchHolder.getImgTop().setVisibility(View.GONE);
        } else  if (position  == mList.size() - 1){
            examSearchHolder.getImgBottom().setVisibility(View.GONE);
            examSearchHolder.getImgTop().setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
