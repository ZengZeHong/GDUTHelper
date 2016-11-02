package com.zzh.gdut.gduthelper.view.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.base.BaseNormalActivity;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.view.adapter.GalleryAdapter;
import com.zzh.gdut.gduthelper.view.widget.SCGallery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZengZeHong on 2016/10/23.
 */

public class ScheduleShowActivity extends BaseNormalActivity {
    public static final String LIST_TAG = "list";
    private static final String TAG = "ScheduleShowActivity";
    private GalleryAdapter adapter;
    private List<ScheduleInfo> list = new ArrayList<>();
    @BindView(R.id.gallery)
    SCGallery gallery;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedule_show;
    }

    @Override
    protected void initViews() {
        adapter = new GalleryAdapter(ScheduleShowActivity.this, list);
        gallery = (SCGallery) findViewById(R.id.gallery);
        gallery.setAdapter(adapter);
        gallery.setSelection(getPosition(list));
        Animator anim = AnimatorInflater.loadAnimator(this, R.animator.gallery_anim);
        anim.setTarget(gallery);
        anim.start();
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //普通点击
                Intent intent = new Intent(ScheduleShowActivity.this , ScheduleItemActivity.class);
                intent.putExtra(ScheduleItemActivity.TAG_ITEM , list.get(position));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void initAttributes() {
        ButterKnife.bind(ScheduleShowActivity.this);
    }

    @Override
    protected void getIntentData(Intent intent) {
        list = intent.getParcelableArrayListExtra(LIST_TAG);
        Log.e(TAG, "getIntentData: " + list.size());
    }

    @OnClick({R.id.rl_shadow})
    public void onClick(View v) {
        if (v.getId() == R.id.rl_shadow) {
            backAnimation();
        }
    }


    /**
     * 获取显示位置
     *
     * @param list
     * @return
     */
    private int getPosition(List<ScheduleInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            ScheduleInfo scheduleInfo = list.get(i);
            if (scheduleInfo.getTextColor() == Color.WHITE)
                return i;
        }
        return 0;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按下的是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回动画
            backAnimation();
        }
        return true;
    }

    @Override
    protected void backAnimation() {
        finish();
        overridePendingTransition(R.anim.translate_not_move, R.anim.alpha_out);
    }
}
