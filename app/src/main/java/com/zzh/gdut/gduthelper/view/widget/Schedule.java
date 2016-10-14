package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
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
    //+号的长度
    private int addLength = 10;
    private Paint mPaint;

    //自定义区域的变量
    private int lineColor;
    private int lineTextColor;
    private int addIconColor;
    private float circleCorner;
    private float lineTextSize;

    //滑动的y轴位移
    private int startY = 0;
    private int lastY = 0;
    private int translateOffest = 0;
    private int temp = 0;

    private boolean isTop = false;
    private boolean isBottom = false;

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
        //画出每一行显示的数据
        drawTextData(new String[]{"好莱芜", "123"}, Color.parseColor("#FFF67E7E"), Color.WHITE, canvas);

    }

    /**
     * 显示课表线段
     *
     * @param canvas
     */

    private void drawScheduleLine(Canvas canvas) {
        mPaint.setTextSize(lineTextSize);
        //画出竖直方向
        for (int i = 0; i < 13; i++) {
            canvas.save();
            canvas.translate(getPaddingLeft(), i * lineHeight + getPaddingTop() + translateOffest);
            Rect rectLine = new Rect(0, 0, lineWidth, lineHeight);
            canvas.drawRect(rectLine, mPaint);
            //绘制文本
            drawText(new String[]{(i + 1) + ""}, rectLine, canvas, lineTextColor);
            canvas.restore();
        }
        //画出重叠线段
        canvas.drawLine(getPaddingLeft() + lineWidth, getPaddingTop() + translateOffest, getPaddingLeft() + lineWidth + 7 * rowHeight, getPaddingTop() + translateOffest, mPaint);
        canvas.drawLine(getPaddingLeft() + lineWidth, getPaddingTop() + translateOffest, getPaddingLeft() + lineWidth, getPaddingTop() + 13 * lineHeight + translateOffest, mPaint);

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
                canvas.translate(lineWidth + i * rowWidth, j * lineHeight + translateOffest);
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
     * @param text            文本数据，数据最好为5，不足则添加空”“数据
     * @param backgroundColor 背景颜色
     * @param textColor       字体颜色
     * @param canvas
     */
    private void drawTextData(String[] text, int backgroundColor, int textColor, Canvas canvas) {
        //边框间距
        int offest = 3;
        RectF rectF = new RectF(getPaddingLeft() + lineWidth + offest, getPaddingTop() + offest + translateOffest, getPaddingLeft() + lineWidth + rowWidth - offest, getPaddingTop() + classHeight - offest + translateOffest);
        Rect rect = new Rect(getPaddingLeft() + lineWidth + offest, getPaddingTop() + offest + translateOffest, getPaddingLeft() + lineWidth + rowWidth - offest, getPaddingTop() + classHeight - offest + translateOffest);
        //背景
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, circleCorner, circleCorner, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制文本
        drawText(text, rect, canvas, textColor);
    }
/*

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.e(TAG, "onTouchEvent: down");
                startY = y;
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                int dy = y - startY;
                //   Log.e(TAG, "onTouchEvent: dy " + temp + ">>" + (lastY - dy) + ">>" + dy);
                Log.e(TAG, "onTouchEvent: height " + getMeasuredHeight() + ">>" + getHeight() + ">>" + getScrollY() + ">>" + getY());

        *//*        //判断顶部
                if ((temp >= 0 && (lastY - dy) <= 0))
                    isTop = true;
                else isTop = false;

                if (!(isTop && dy > 0) && getScrollY() == 0) {*//*
                scrollTo(0, lastY - dy);
                invalidate();
                //记录上一次的状态
                temp = lastY - dy;
         *//*       }

            *//*
            }
            break;
            case MotionEvent.ACTION_UP: {
                int dy = y - startY;
                //   if (!((lastY == 0 && dy > 0) || (lastY >= 3 * lineHeight && dy < 0))) {
                lastY = getScrollY();
                //   }
            }
            break;
        }
        return true;
    }*/
}
