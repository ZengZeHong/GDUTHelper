package com.rdc.spg.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ZengZeHong on 2016/7/17.
 * T 为对应的数据类型
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter implements View.OnClickListener {
    protected List<T> mList;
    protected Context mContext;

    protected abstract RecyclerView.ViewHolder createViewHolder(View view);

    protected abstract int getLayoutId();

    public BaseRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //设置数据
    public List<T> getListData() {
        return mList;
    }

    public void setListData(List<T> mList) {
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null) Log.e("nullaaaa" , "mCOntext");
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(), parent, false);
        return createViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
