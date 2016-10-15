package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.bean.ScheduleInfo;
import com.zzh.gdut.gduthelper.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZengZeHong on 2016/10/13.
 * 课程表
 */

public class Schedule extends View {
    private static final String TAG = "Schedule";
    //点击类型
    private static final int TAG_CLASS = 0;
    private static final int TAG_ITEM = 1;
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
    private float circleCorner;
    private float lineTextSize;

    private int currentClickX = -1;
    private int currentClickY = -1;
    private int lastX = -1;
    private int lastY = -1;
    private int location = 0;
    private boolean isMove = false;
    private boolean isSchedule = false;
    //数据源
    private List<ScheduleInfo> listData;
    //设置点击监听
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
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
        listData = new ArrayList<>();

        ScheduleInfo scheduleInfo = new ScheduleInfo();
        scheduleInfo.setScheduleName("教务处理系统");
        scheduleInfo.setSchedulePlace("@教4-210");
        scheduleInfo.setLocation(0, 0);

        ScheduleInfo scheduleInfo1 = new ScheduleInfo();
        scheduleInfo1.setScheduleName("计算机系统结构");
        scheduleInfo1.setSchedulePlace("@教4-210");
        scheduleInfo1.setLocation(2, 2);

        ScheduleInfo scheduleInfo2 = new ScheduleInfo();
        scheduleInfo2.setScheduleName("软件工程");
        scheduleInfo2.setSchedulePlace("@教4-210");
        scheduleInfo2.setLocation(1, 5);

        ScheduleInfo scheduleInfo3 = new ScheduleInfo();
        scheduleInfo3.setScheduleName("操作系统");
        scheduleInfo3.setSchedulePlace("@教4-210");
        scheduleInfo3.setLocation(0, 9);
        listData.add(scheduleInfo);
        listData.add(scheduleInfo1);
        listData.add(scheduleInfo2);
        listData.add(scheduleInfo3);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        //通过这个方法，将你在attra.xml定义的declare-styleable的所有属性的值存到TypedArray中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Schedule);
        lineColor = ta.getColor(R.styleable.Schedule_lineColor, Color.GRAY);
        lineTextColor = ta.getColor(R.styleable.Schedule_lineTextColor, Color.GRAY);
        lineTextSize = ta.getDimension(R.styleable.Schedule_lineTextSize, 40);
        addIconColor = ta.getColor(R.styleable.Schedule_addIconColor, Color.GRAY);
        circleCorner = ta.getDimension(R.styleable.Schedule_circleCorner, 20);
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
        lineHeight = rowHeight + 4;
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
        for (ScheduleInfo scheduleInfo : listData) {
            scheduleInfo.setData();
            drawTextData(scheduleInfo.getX(), scheduleInfo.getY(), scheduleInfo.getSpan(), scheduleInfo.getString(), Color.parseColor("#FFF67E7E"), Color.WHITE, canvas);
        }
        //画出点击效果
        if (currentClickX != -1 && currentClickY != -1) {
            if (!isSchedule)
                drawClickArea(canvas);
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
    private void drawTextData(int x, int y, int span, String[] text, int backgroundColor, int textColor, Canvas canvas) {
        //边框间距
        int offest = 3;
        RectF rectF = new RectF(getPaddingLeft() + lineWidth + offest + rowWidth * x, getPaddingTop() + offest + lineHeight * y, getPaddingLeft() + lineWidth + rowWidth * (x + 1) - offest, getPaddingTop() + lineHeight * (y + span) - offest);
        Rect rect = new Rect(getPaddingLeft() + lineWidth + offest + rowWidth * x, getPaddingTop() + offest + lineHeight * y, getPaddingLeft() + lineWidth + rowWidth * (x + 1) - offest, getPaddingTop() + lineHeight * (y + span) - offest);
        //背景
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
        if (currentClickX == x && currentClickY >= y && currentClickY < y + span) {
            //点击背景
            mPaint.setColor(Color.BLACK);
            mPaint.setAlpha(30);
            canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
            mPaint.setAlpha(255);
            //表示点击的是class
            isSchedule = true;
        }
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制文本
        drawText(text, rect, canvas, textColor);
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
                            initClick();
                            //TODO 点击课程的回调
                            Log.e(TAG, "onTouchEvent: Item clasxs");
                        } else
                            Log.e(TAG, "onTouchEvent: Item normal");

                        invalidate();
                        location = currentClickY * 7 + currentClickX;
                    } else {
                        Log.e(TAG, "onTouchEvent: Item normal click");
                        //TODO 普通监听的回调
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick();
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
        for (ScheduleInfo scheduleInfo : listData) {
            if (currentClickX == scheduleInfo.getX() && currentClickY >= scheduleInfo.getY() && currentClickY < scheduleInfo.getY() + scheduleInfo.getSpan())
                return true;
        }
        return false;
    }
}
