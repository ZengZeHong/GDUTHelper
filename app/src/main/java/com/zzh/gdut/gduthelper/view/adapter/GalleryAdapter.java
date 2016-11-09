package com.zzh.gdut.gduthelper.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.view.widget.ClickBackgroundView;

import java.util.List;

/**
 * Created by ZengZeHong on 2016/10/20.
 */

public class GalleryAdapter extends BaseAdapter {
    private static final String TAG = "GalleryAdapter";
    private List<ScheduleInfo> list;
    private Context context;

    public GalleryAdapter(Context context, List<ScheduleInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
            holder.clickBackgroundView = (ClickBackgroundView) convertView.findViewById(R.id.click_view);
            holder.tvSchedule = (TextView) convertView.findViewById(R.id.tv_schedule);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ScheduleInfo scheduleInfo = list.get(position);
        holder.clickBackgroundView.setBackground(scheduleInfo.getBackgroundColor());
        holder.tvSchedule.setText(scheduleInfo.getScheduleName() + "@" + scheduleInfo.getSchedulePlace());
        holder.tvSchedule.setTextColor(scheduleInfo.getTextColor());
        return convertView;
    }

    class ViewHolder {
        private ClickBackgroundView clickBackgroundView;
        private TextView tvSchedule;
    }

}
