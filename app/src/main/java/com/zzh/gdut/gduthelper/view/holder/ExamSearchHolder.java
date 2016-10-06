package com.zzh.gdut.gduthelper.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private ImageView imgTop;
    private ImageView imgBottom;
    private RelativeLayout rlContent;
    private RelativeLayout rlImg;
    public ExamSearchHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_exam_name);
        tvTime = (TextView) itemView.findViewById(R.id.tv_exam_time);
        tvPlace = (TextView) itemView.findViewById(R.id.tv_exam_place);
        tvSeat = (TextView) itemView.findViewById(R.id.tv_exam_seat);
        imgBottom = (ImageView) itemView.findViewById(R.id.img_bottom);
        imgTop = (ImageView) itemView.findViewById(R.id.img_top);
        rlContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        rlImg = (RelativeLayout) itemView.findViewById(R.id.rl_img);
    }

    public RelativeLayout getRlContent() {
        return rlContent;
    }

    public void setRlContent(RelativeLayout rlContent) {
        this.rlContent = rlContent;
    }

    public RelativeLayout getRlImg() {
        return rlImg;
    }

    public void setRlImg(RelativeLayout rlImg) {
        this.rlImg = rlImg;
    }

    public ImageView getImgTop() {
        return imgTop;
    }

    public void setImgTop(ImageView imgTop) {
        this.imgTop = imgTop;
    }

    public ImageView getImgBottom() {
        return imgBottom;
    }

    public void setImgBottom(ImageView imgBottom) {
        this.imgBottom = imgBottom;
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
