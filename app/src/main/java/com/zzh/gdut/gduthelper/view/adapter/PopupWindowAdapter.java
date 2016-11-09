package com.zzh.gdut.gduthelper.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseRecyclerViewAdapter;
import com.zzh.gdut.gduthelper.view.holder.PopupWindowHolder;
import com.zzh.gdut.gduthelper.view.vinterface.RecyclerItemClick;

import java.util.List;

/**
 * Created by ZengZeHong on 2016/11/7.
 */

public class PopupWindowAdapter extends BaseRecyclerViewAdapter<String> {
    private RecyclerItemClick mRecyclerItemClick;
    private int currentPosition;
    public void setRecyclerItemClick(RecyclerItemClick mRecyclerItemClick){
        this.mRecyclerItemClick = mRecyclerItemClick;
    }
    public PopupWindowAdapter(Context mContext , List<String> list , int currentPosition) {
        super(mContext);
        mList = list;
        this.currentPosition = currentPosition;
    }

    @Override
    protected RecyclerView.ViewHolder createViewHolder(View view) {
        return new PopupWindowHolder(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_pw_item;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PopupWindowHolder popupWindowHolder = (PopupWindowHolder) holder;
        popupWindowHolder.setPoint(position);
        popupWindowHolder.getTvTerm().setText(mList.get(position));
        popupWindowHolder.getRlTerm().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRecyclerItemClick != null)
                    mRecyclerItemClick.onItemClick(position);
            }
        });
        if(position == currentPosition)
            popupWindowHolder.getRlTerm().setPressed(true);
        else
            popupWindowHolder.getRlTerm().setPressed(false);
    }

    @Override
    public void onClick(View v) {

    }
}
