package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.util.DisplayUtil;

/**
 * Created by ZengZeHong on 2016/10/13.
 * 课程表
 */

public class Schedule extends View {
    private static final String TAG = "Schedule";
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
    private Paint mPaint;
    //自定义区域的变量
    private int lineColor;
    private int lineTextColor;
    private float lineStroke;
    private float lineTextSize;

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
        //初始化画笔通用设置
        mPaint = new Paint();
        //使用抗锯齿
        mPaint.setAntiAlias(true);
        //设置文字居中显示
        mPaint.setTextAlign(Paint.Align.CENTER);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        //通过这个方法，将你在attra.xml定义的declare-styleable的所有属性的值存到TypedArray中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Schedule);
        lineColor = ta.getColor(R.styleable.Schedule_lineColor, Color.GRAY);
        lineStroke = ta.getDimension(R.styleable.Schedule_lineWidth, 1);
        lineTextColor = ta.getColor(R.styleable.Schedule_lineTextColor, Color.GRAY);
        lineTextSize = ta.getDimension(R.styleable.Schedule_lineTextSize, 40);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureLength(widthMeasureSpec, screenWidth), measureLength(heightMeasureSpec, screenHeight));
    }

    /**
     * 设置默认宽高
     *
     * @param measureSpec
     * @param length
     * @return
     */
    private int measureLength(int measureSpec, int length) {
        int size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY)
            size = specSize;
        else if (specMode == MeasureSpec.AT_MOST)
            size = Math.min(specSize, length);
        else
            size = length;
        return size;
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        //获取测量后View的宽高
        viewHeight = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();
        viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        Log.e(TAG, "layout: " + getMeasuredWidth() + ">>" + getPaddingLeft());
        //计算出每行每列分配的宽高
        distributeLenght();
    }

    /**
     * 计算出每行每列分配的宽高
     */
    private void distributeLenght() {
        //7.5是分配出来的
        rowWidth = Math.round((float) (viewWidth / 7.5));
        rowHeight = rowWidth;
        lineHeight = (viewHeight - rowHeight) / 10;
        lineWidth = rowWidth / 2;
        classHeight = lineHeight * 2;
        Log.e(TAG, "distributeLenght: " + rowWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画出显示顶部的日期
        drawTopWeek(canvas);
        //画出每一行显示的数据
        drawLineData(canvas);
    }

    /**
     * 显示顶部日期
     *
     * @param canvas
     */
    private void drawTopWeek(Canvas canvas) {
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        //先画第一个
        Rect rect = new Rect(getPaddingLeft(), getPaddingTop(), lineWidth + getPaddingLeft(), rowHeight + getPaddingTop());
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(lineTextSize);
        canvas.drawRect(rect, mPaint);
        canvas.drawText("1", rect.centerX() , rect.centerY() + 20 ,  mPaint);

        //画出水平日期
        for (int i = 0; i < 7; i++) {
            canvas.save();
            canvas.translate(lineWidth + i * rowWidth + getPaddingLeft(), getPaddingTop());
            Rect rectRow = new Rect(0, 0, rowWidth, rowHeight);
            canvas.drawRect(rectRow, mPaint);
            canvas.restore();
        }
        //画出竖直方向
        for (int i = 0; i < 10; i++) {
            canvas.save();
            canvas.translate(getPaddingLeft(), rowHeight + i * lineHeight + getPaddingTop());
            Rect rectLine = new Rect(0, 0, lineWidth, lineHeight);
            canvas.drawRect(rectLine, mPaint);
            canvas.restore();
        }
    }

    /**
     * 显示每一个的数据
     *
     * @param canvas
     */
    private void drawLineData(Canvas canvas) {
    }
}
