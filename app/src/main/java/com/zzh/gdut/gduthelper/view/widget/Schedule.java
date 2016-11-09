package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.util.DisplayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZengZeHong on 2016/10/13.
 * 课程表
 */

public class Schedule extends View {
    private static final String TAG = "Schedule";
    //点击类型
    private static final int TAG_CLASS = 0;
    private static final int TAG_ITEM = 1;
    //分割时间符
    public static final String TIME_SEPARATOR = "@";
    //每一个子列的高度
    private int lineHeight;
    //每一列的宽度
    private int lineWidth;
    private int rowHeight;
    //每一个子行的宽度
    private int rowWidth;
    //每一门课程的高度
    private int classHeight;
    //View的宽高，默认是屏幕的宽高
    private int viewHeight;
    private int viewWidth;
    //屏幕的宽高
    private int screenHeight;
    private int screenWidth;
    //+号的长度
    private int addLength = 10;
    private Paint mPaint;

    //自定义区域的变量
    private int lineColor;
    private int lineTextColor;
    private int addIconColor;
    private int itemColor;
    private float circleCorner;
    private float lineTextSize;

    private int currentClickX = -1;
    private int currentClickY = -1;
    private int lastX = -1;
    private int lastY = -1;
    private int location = 0;
    private int currentWeek = 1;
    private boolean isMove = false;
    private boolean isSchedule = false;
    //数据源
    private List<Map<String, List<ScheduleInfo>>> listData = new ArrayList<>();
    //课程对应颜色
    private Map<String, Integer> mapColor = new HashMap<>();
    //配色数组
    private int[] colors = new int[]{
            R.color.scheduleBlue, R.color.schedulePink,
            R.color.scheduleGreen, R.color.scheduleDeepOrg,
            R.color.scheduleDeepGreen, R.color.schedulePurple,
            R.color.scheduleYellow, R.color.scheduleDeepBlue,
            R.color.scheduleDeepPink, R.color.scheduleOrg};
    //设置点击监听
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(List<ScheduleInfo> list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Schedule(Context context) {
        super(context);
        initData(context);
    }

    public Schedule(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initAttrs(context, attrs);
    }

    public Schedule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initData(Context context) {
        //默认获取屏幕的宽高
        screenHeight = DisplayUtil.getScreenHeight(context);
        screenWidth = DisplayUtil.getScreenWidth(context);
        //计算出每行每列分配的宽高
        distributeLenght();
        //初始化画笔通用设置
        mPaint = new Paint();
        //使用抗锯齿
        mPaint.setAntiAlias(true);
        //设置文字居中显示
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setListData(List<Map<String, List<ScheduleInfo>>> list) {
        if (listData != null && list != null) {
            listData = list;
            setColor();
            invalidate();
        }
    }

    /**
     * 给每一门课程分配随机一种配色
     */
    private void setColor() {
        int k = 0;
        for (Map<String, List<ScheduleInfo>> map : listData) {
            for (Map.Entry<String, List<ScheduleInfo>> entry : map.entrySet()) {
                for (ScheduleInfo scheduleInfo : entry.getValue()) {
                    mapColor.put(scheduleInfo.getScheduleName(), new Integer(0));
                    //初始化颜色
                    scheduleInfo.setBackgroundColor(addIconColor);
                    scheduleInfo.setTextColor(Color.GRAY);
                }
                //移除重复项
                removeRepeat(entry.getValue());
            }
        }
        //二次筛选颜色
        for (Map.Entry<String, Integer> entry : mapColor.entrySet()) {
            entry.setValue(new Integer(colors[k]));
            k++;
            if (k >= colors.length)
                k = 0;
        }
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        //通过这个方法，将你在attra.xml定义的declare-styleable的所有属性的值存到TypedArray中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Schedule);
        lineColor = ta.getColor(R.styleable.Schedule_lineColor, Color.GRAY);
        lineTextColor = ta.getColor(R.styleable.Schedule_lineTextColor, Color.GRAY);
        lineTextSize = ta.getDimension(R.styleable.Schedule_lineTextSize, 40);
        addIconColor = ta.getColor(R.styleable.Schedule_addIconColor, Color.GRAY);
        itemColor = ta.getColor(R.styleable.Schedule_itemColor, Color.GRAY);
        circleCorner = ta.getDimension(R.styleable.Schedule_circleCorner, 20);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(viewWidth, viewHeight);
    }


    /**
     * 计算出每行每列分配的宽高
     */
    private void distributeLenght() {
        viewWidth = screenWidth - getPaddingLeft() - getPaddingRight();
        //7.5是分配出来的
        rowWidth = ((int) (viewWidth / 7.7));
        rowHeight = rowWidth;
        lineHeight =rowHeight;
        lineWidth = Math.round((float) (rowWidth * 0.7));
        classHeight = lineHeight * 2;
        viewHeight = lineHeight * 13 - getPaddingBottom() - getPaddingTop();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //显示课表线段，竖直和水平
        drawScheduleLine(canvas);
        //画出每个单元显示的+号
        drawSmallAdd(canvas);
        //画出课表
        drawSchedule(canvas);
        //画出点击效果
        if (currentClickX != -1 && currentClickY != -1) {
            if (!isSchedule)
                drawClickArea(canvas);
        }
    }

    /**
     * 画出课表
     *
     * @param canvas
     */
    private void drawSchedule(Canvas canvas) {
        for (Map<String, List<ScheduleInfo>> map : listData) {
            for (Map.Entry<String, List<ScheduleInfo>> entry : map.entrySet()) {
                if (entry.getValue().size() > 1) {
                    //如果当前节课下有多门课程，则要判断显示
                    //TODO 选择指定周下的课程
                    ScheduleInfo scheduleInfo = selectSchedule(entry.getValue(), currentWeek);
                    if (scheduleInfo != null) {
                        scheduleInfo.setBackgroundColor(getResources().getColor(mapColor.get(scheduleInfo.getScheduleName())));
                        scheduleInfo.setTextColor(Color.WHITE);
                        drawTextData(true, scheduleInfo.getX(), scheduleInfo.getY(), scheduleInfo.getSpan(), scheduleInfo.getString(), scheduleInfo.getBackgroundColor(), Color.WHITE, canvas);
                    } else {
                        scheduleInfo = entry.getValue().get(0);
                        scheduleInfo.setBackgroundColor(addIconColor);
                        scheduleInfo.setTextColor(Color.GRAY);
                        drawTextData(true, scheduleInfo.getX(), scheduleInfo.getY(), scheduleInfo.getSpan(), scheduleInfo.getString(), addIconColor, Color.GRAY, canvas);
                    }
                } else {
                    if (entry.getValue().size() == 1) {
                        //只有一个条目的话
                        ScheduleInfo scheduleInfo = entry.getValue().get(0);
                        if (isCurrentWeek(scheduleInfo, currentWeek)) {
                            //如果是当前周
                            scheduleInfo.setBackgroundColor(getResources().getColor(mapColor.get(scheduleInfo.getScheduleName())));
                            scheduleInfo.setTextColor(Color.WHITE);
                            drawTextData(false, scheduleInfo.getX(), scheduleInfo.getY(), scheduleInfo.getSpan(), scheduleInfo.getString(), scheduleInfo.getBackgroundColor(), Color.WHITE, canvas);
                        } else {
                            scheduleInfo.setBackgroundColor(addIconColor);
                            scheduleInfo.setTextColor(Color.GRAY);
                            drawTextData(false, scheduleInfo.getX(), scheduleInfo.getY(), scheduleInfo.getSpan(), scheduleInfo.getString(), addIconColor, Color.GRAY, canvas);
                        }
                    }
                }
            }
        }
    }


    /**
     * 根据指定周排序同个节数下的课程
     *
     * @param week
     */
    private ScheduleInfo selectSchedule(List<ScheduleInfo> list, int week) {
        Collections.sort(list);
        for (ScheduleInfo scheduleInfo : list) {
            if (isCurrentWeek(scheduleInfo, week))
                return scheduleInfo;
        }
        return null;
    }

    /**
     * 设置当前的周是第几周
     * @param currentWeek
     */
    public void setCurrentWeek(int currentWeek){
        this.currentWeek = currentWeek;
        invalidate();
    }
    /**
     * 判断当前课程是不是在当前周
     *
     * @param scheduleInfo
     * @param week
     * @return
     */
    private boolean isCurrentWeek(ScheduleInfo scheduleInfo, int week) {
        String scheduleTime = scheduleInfo.getScheduleTime();
        String[] times;
        if (scheduleTime.contains(TIME_SEPARATOR)) {
            times = scheduleTime.split(TIME_SEPARATOR);
        } else
            times = new String[]{scheduleTime};

        for (int i = 0; i < times.length; i++) {
            String time = times[i];
            String timeWeek = time.substring(time.indexOf("{") + 1, time.indexOf("}"));
            String[] range = timeWeek.substring(timeWeek.indexOf("第") + 1, timeWeek.indexOf("周")).split("-");
            if (week >= Integer.parseInt(range[0]) && week <= Integer.parseInt(range[1])) {
                //把满足指定周的课程添加到需要显示的List中去
                return true;
            }
        }
        return false;
    }

    /**
     * 移除掉重复项
     *
     * @param list
     */
    private void removeRepeat(List<ScheduleInfo> list) {
        Log.e(TAG, "isFode: before " + list.size());
        List<ScheduleInfo> listTag = new ArrayList<>();
        if (list.size() >= 2) {
            for (int i = 0; i < list.size(); i = i + 2) {
                ScheduleInfo scheduleInfoOne = list.get(i + 0);
                if (i+1 < list.size()) {
                    ScheduleInfo scheduleInfoTwo = list.get(i + 1);
                    if (scheduleInfoOne.getScheduleName().equals(scheduleInfoTwo.getScheduleName())) {
                        //加个分隔符来分割
                        scheduleInfoOne.setScheduleTime(scheduleInfoOne.getScheduleTime() + TIME_SEPARATOR + scheduleInfoTwo.getScheduleTime());
                        //记录对象，最后统一移除
                        listTag.add(scheduleInfoTwo);
                    }
                }
            }
        }
        list.removeAll(listTag);
        Log.e(TAG, "isFode: after " + list.size());
    }

    /**
     * 判断当前课表是否出现重复
     *
     * @param list
     * @return true表示重复
     */
    public boolean isFode(List<ScheduleInfo> list) {
        //>2则需要判断移除重复项，合并时间
        Log.e(TAG, "isFode: after" + list.size());
        if (list.size() > 1)
            return true;
        else {
            return false;
        }
    }

    /**
     * 画出点击效果
     *
     * @param canvas
     */
    private void drawClickArea(Canvas canvas) {
        //边框间距
        int offest = 3;
        RectF rectF = new RectF(getPaddingLeft() + offest + lineWidth + rowWidth * currentClickX, getPaddingTop() + offest + lineHeight * currentClickY, getPaddingLeft() + lineWidth + rowWidth * (currentClickX + 1) - offest, getPaddingTop() + lineHeight * (currentClickY + 1) - offest);
        //背景
        mPaint.setColor(addIconColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
        //绘制文本
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 4, mPaint);
        mPaint.setColor(addIconColor);
        float length = rectF.width() / 8;
        mPaint.setStrokeWidth(5);
        canvas.drawLine(rectF.centerX() - length, rectF.centerY(), rectF.centerX() + length, rectF.centerY(), mPaint);
        canvas.drawLine(rectF.centerX(), rectF.centerY() - length, rectF.centerX(), rectF.centerY() + length, mPaint);
        mPaint.setStrokeWidth(0);
    }

    /**
     * 初始化
     */
    private void initClick() {
        currentClickX = -1;
        currentClickY = -1;
    }

    /**
     * 显示课表线段
     *
     * @param canvas
     */

    private void drawScheduleLine(Canvas canvas) {
        mPaint.setTextSize(lineTextSize);
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        //画出竖直方向
        for (int i = 0; i < 13; i++) {
            canvas.save();
            canvas.translate(getPaddingLeft(), i * lineHeight + getPaddingTop());
            Rect rectLine = new Rect(0, 0, lineWidth, lineHeight);
            canvas.drawRect(rectLine, mPaint);
            //绘制文本
            drawText(new String[]{(i + 1) + ""}, rectLine, canvas, lineTextColor);
            canvas.restore();
        }
        //画出重叠线段
        canvas.drawLine(getPaddingLeft() + lineWidth, getPaddingTop(), getPaddingLeft() + lineWidth, getPaddingTop() + 13 * lineHeight, mPaint);

    }


    /**
     * 画出每个单元格显示的+号
     *
     * @param canvas
     */
    private void drawSmallAdd(Canvas canvas) {
        mPaint.setColor(addIconColor);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 13; j++) {
                canvas.save();
                canvas.translate(lineWidth + i * rowWidth, j * lineHeight);
                canvas.drawLine(rowWidth - addLength, lineHeight, rowWidth + addLength, lineHeight, mPaint);
                canvas.drawLine(rowWidth, lineHeight - addLength, rowWidth, lineHeight + addLength, mPaint);
                canvas.restore();
            }
        }
        mPaint.setColor(lineColor);
    }

    /**
     * 绘制单行或者多行居中文本
     *
     * @param text
     * @param rect
     * @param canvas
     */
    private void drawText(String[] text, Rect rect, Canvas canvas, int color) {
        mPaint.setColor(color);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int length = text.length;
        //每个字rect的高度
        int height = rect.height() / (length + 1);
        for (int i = 0; i < length; i++) {
            Rect rectChild = new Rect(rect.left, rect.top + i * height + height, rect.right, rect.top + (i + 1) * height);
            //找到文本基线
            float baseLine = (float) (rectChild.bottom + rectChild.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
            canvas.drawText(text[i], rectChild.centerX(), baseLine, mPaint);
        }
        mPaint.setColor(lineColor);
    }

    /**
     * 显示每一个的数据
     *
     * @param x
     * @param y
     * @param span            暂居多少个格子
     * @param text            文本数据，数据最好为5，不足则添加空”“数据
     * @param backgroundColor 背景颜色
     * @param textColor       字体颜色
     * @param canvas
     */
    private void drawTextData(boolean isFode, int x, int y, int span, String text, int backgroundColor, int textColor, Canvas canvas) {
        //边框间距
        int offest = 3;
        RectF rectF = new RectF(getPaddingLeft() + lineWidth + offest + rowWidth * x, getPaddingTop() + offest + lineHeight * y, getPaddingLeft() + lineWidth + rowWidth * (x + 1) - offest, getPaddingTop() + lineHeight * (y + span) - offest);
        //背景
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
        int offestSmall = rowWidth / 4;
        float rate = (offestSmall * circleCorner) / rowWidth;
        //TODO 如果当前节下有多个课程，则设置折叠效果,要加判断
        if (isFode) {
            //绘制背景层
            if (backgroundColor == addIconColor)
                mPaint.setColor(Color.WHITE);
            else
                mPaint.setColor(addIconColor);
            RectF rectBg = new RectF(getPaddingLeft() + lineWidth + offest + rowWidth * x + rowWidth - offestSmall, getPaddingTop() + offest + lineHeight * y, getPaddingLeft() + lineWidth + rowWidth * (x + 1) - offest, getPaddingTop() + offest + lineHeight * y + offestSmall);
            canvas.drawRoundRect(rectBg, rate, rate, mPaint);
            //绘制折叠层
            if (backgroundColor == addIconColor)
                mPaint.setColor(itemColor);
            else
                mPaint.setColor(backgroundColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAlpha(180);
            Path path = new Path();
            path.moveTo(rectBg.left, rectBg.top);
            path.lineTo(rectBg.right, rectBg.bottom);
            path.lineTo(rectBg.left, rectBg.bottom);
            canvas.drawPath(path, mPaint);
            mPaint.setAlpha(255);
        }
        //点击背景
        if (currentClickX == x && currentClickY >= y && currentClickY < y + span) {
            mPaint.setColor(Color.BLACK);
            mPaint.setAlpha(30);
            canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
            mPaint.setAlpha(255);
            //表示点击的是class
            isSchedule = true;
        }
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制自动换行文本
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(textColor);
        textPaint.setTextSize(lineTextSize);
        StaticLayout layout = new StaticLayout(text, textPaint, (7 * rowWidth) / 8, Layout.Alignment.ALIGN_CENTER, 1.0F, 0.0F, true);
        canvas.save();
        canvas.translate(rectF.left + rowWidth / 16, rectF.top + rowWidth / 16 + 10);
        layout.draw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                //设置点击
                setItemClick(event);
                isMove = false;
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                Log.e(TAG, "onTouchEvent: move");
                isMove = true;
                //设置点击
                setItemClick(event);
            }
            break;
            case MotionEvent.ACTION_UP: {
                Log.e(TAG, "onTouchEvent: up");
                if (isMove == false)
                    setItemClick(event);
                if (lastX == currentClickX && lastY == currentClickY) {
                    if (location != currentClickY * 7 + currentClickX) {
                        if (isSchedule) {
                            //TODO 点击课程的回调
                            Log.e(TAG, "onTouchEvent: Item clasxs " + currentClickX + ">>" + currentClickY);
                            Map<String, List<ScheduleInfo>> map = null;
                            switch (currentClickY) {
                                case 0:
                                case 1: {
                                    map = listData.get(0);
                                }
                                break;
                                case 2:
                                case 3: {
                                    map = listData.get(1);
                                }
                                break;
                                case 5:
                                case 6: {
                                    map = listData.get(2);
                                }
                                break;
                                case 7:
                                case 8: {
                                    map = listData.get(3);
                                }
                                break;
                                case 9:
                                case 10:
                                case 11: {
                                    map = listData.get(4);
                                }
                                break;
                            }
                            //触发课程回调
                            if (onItemClickListener != null && map != null) {

                                onItemClickListener.onItemClick(map.get((currentClickX + "")));
                            }
                            initClick();
                        } else
                            Log.e(TAG, "onTouchEvent: Item normal");

                        invalidate();
                        location = currentClickY * 7 + currentClickX;
                    } else {
                        Log.e(TAG, "onTouchEvent: Item normal click");
                        //TODO 普通监听的回调
                    }
                }
                isMove = false;
            }
            break;
        }
        return true;
    }

    /**
     * 设置点击
     *
     * @param event
     */
    private void setItemClick(MotionEvent event) {
        if (lineWidth != 0) {
            int offestY = (int) event.getY();
            int offestX = (int) event.getX() - lineWidth;
            if (offestX > 0) {
                //余数，表示在表上哪一个单元，几行几列
                //求出指定点在几行几列
                int y = offestY / lineHeight + 1;
                int x = offestX / rowWidth + 1;
                Log.e(TAG, "setItemClick: " + x + ">>" + y);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    currentClickX = x - 1;
                    currentClickY = y - 1;
                    if (isSchedule = isSchedule())
                        invalidate();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE || !isMove) {
                    //如果是在移动中，则判断当前位置是否有发生变化
                    lastX = x - 1;
                    lastY = y - 1;
                    Log.e(TAG, "setItemClick: compare " + lastX + ">>" + currentClickX + ">>" + lastY + ">>" + currentClickY);
                    if (lastX == currentClickX && lastY == currentClickY) {
                        //如在还在当前的位置，则释放可以触发up
                        getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        //表示移动到其他区域了，则拦截
                        getParent().requestDisallowInterceptTouchEvent(false);
                        initClick();
                        invalidate();
                    }
                }
            }
        }
    }

    /**
     * 判断当前点击区域是否是课程
     *
     * @return
     */
    private boolean isSchedule() {
        for (Map<String, List<ScheduleInfo>> map : listData) {
            for (Map.Entry<String, List<ScheduleInfo>> entry : map.entrySet()) {
                for (ScheduleInfo scheduleInfo : entry.getValue()) {
                    if (currentClickX == scheduleInfo.getX() && currentClickY >= scheduleInfo.getY() && currentClickY < scheduleInfo.getY() + scheduleInfo.getSpan())
                        return true;
                }
            }
        }
        return false;
    }
}
