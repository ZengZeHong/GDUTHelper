package com.zzh.gdut.gduthelper.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.zzh.gdut.gduthelper.R;
import com.zzh.gdut.gduthelper.util.CalendarUtil;
import com.zzh.gdut.gduthelper.util.DisplayUtil;


/**
 * Created by ZengZeHong on 2016/10/14.
 */

public class ScheduleTop extends View {
    private static final String TAG = "SchduleTop";
    private static final int ROW_LINE = 7;
    private int rowHeight;
    //每一个子行的宽度
    private int rowWidth;
    //View的宽高，默认是屏幕的宽高
    private int viewHeight;
    private int lineWidth;
    private int viewWidth;
    //屏幕的宽高
    private int screenHeight;
    private int screenWidth;
    private Paint mPaint;

    //自定义区域的变量
    private int lineColor;
    private int lineTextColor;
    private float lineTextSize;

    //数据源
    private int[] stringWeek = new int[ROW_LINE];
    private int currentMonth;
    private int currentYear;
    private int baseWeek;//基准周，9月1号为第一周
    private int selectWeek;//当前选定的周
    private int showWeek;
    private boolean isNextYear = false;

    public ScheduleTop(Context context) {
        super(context);
        initData(context);
    }

    public ScheduleTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
        initAttrs(context, attrs);
    }

    public ScheduleTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }

    private void initAttrs(Context context, AttributeSet attrs)     {
        //通过这个方法，将你在attra.xml定义的declare-styleable的所有属性的值存到TypedArray中
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Schedule);
        lineColor = ta.getColor(R.styleable.Schedule_lineColor, Color.GRAY);
        lineTextColor = ta.getColor(R.styleable.Schedule_lineTextColor, Color.GRAY);
        lineTextSize = ta.getDimension(R.styleable.Schedule_lineTextSize, 40);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(viewWidth, viewHeight);
    }

    private void initData(Context context) {
        //默认获取屏幕的宽高
        screenHeight = DisplayUtil.getScreenHeight(context);
        screenWidth = DisplayUtil.getScreenWidth(context);
        //计算出每行每列分配的宽高
        distributeLenght();

        currentYear = CalendarUtil.getCurrentYear();
        currentMonth = CalendarUtil.getCurrentMonth();
        baseWeek = CalendarUtil.getWeekFromDay(currentYear, 9, 1);
        showWeek = baseWeek + selectWeek;

        //初始化画笔通用设置
        mPaint = new Paint();
        //使用抗锯齿
        mPaint.setAntiAlias(true);
        //设置文字居中显示
        mPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 设置当前的周数
     *
     * @param sw
     */
    public void setWeek(int sw) {
        this.selectWeek = sw - 1;
        showWeek = baseWeek + selectWeek;
        if (showWeek > CalendarUtil.getWeekNumOfYear(currentYear)) {
            showWeek -= CalendarUtil.getWeekNumOfYear(currentYear);
            currentYear++;
            isNextYear = true;
        } else if (isNextYear) {
            currentYear--;
            isNextYear = false;
        }
        invalidate();
    }


    /**
     * 计算出每行每列分配的宽高
     */
    private void distributeLenght() {
        //7.5是分配出来的
        viewWidth = screenWidth - getPaddingLeft() - getPaddingRight();
        rowWidth = ((int) (viewWidth / 7.7));
        rowHeight = rowWidth;
        viewHeight = rowHeight;
        lineWidth = Math.round((float) (rowWidth * 0.7));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setCalendarData();
        //显示课表线段，竖直和水平
        drawScheduleLine(canvas);

    }

    /**
     * 获取时间数据.
     * 一般的9月1号这一天或者这个星期算是第一周
     */
    private void setCalendarData() {
        //指定周数下第一天
        int day = CalendarUtil.getDayOfWeek(currentYear, showWeek) + 1;
        currentMonth = CalendarUtil.getMonth(currentYear, showWeek);
        for (int i = 0; i < ROW_LINE; i++) {
            stringWeek[i] = day;
            day++;
            if (day > CalendarUtil.getDayNumOfMonth(currentYear, currentMonth)) {
                currentMonth++;
                if (currentMonth > 12) {
                    currentMonth = 1;
                }
                day = 1;
            }
        }
    }

    /**
     * 显示课表线段
     *
     * @param canvas
     */

    private void drawScheduleLine(Canvas canvas) {
        //先画第一个
        Rect rect = new Rect(getPaddingLeft(), getPaddingTop(), lineWidth + getPaddingLeft(), rowHeight + getPaddingTop());
        mPaint.setColor(lineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(lineTextSize);
        canvas.drawRect(rect, mPaint);
        //绘制文本
        drawText(new String[]{CalendarUtil.getMonth(currentYear, baseWeek + selectWeek) + "", "月"}, rect, canvas, lineTextColor);
        //画出水平日期
        for (int i = 0; i < 7; i++) {
            canvas.save();
            canvas.translate(lineWidth + i * rowWidth + getPaddingLeft(), getPaddingTop());
            Rect rectRow = new Rect(0, 0, rowWidth, rowHeight);
            canvas.drawRect(rectRow, mPaint);
            //绘制文本
            drawText(new String[]{stringWeek[i] + "", getWeek(i)}, rectRow, canvas, lineTextColor);
            canvas.restore();
        }
        canvas.drawLine(getPaddingLeft() + lineWidth, getPaddingTop() + rowHeight, getPaddingLeft() + lineWidth + 7 * rowHeight, getPaddingTop() + rowHeight, mPaint);

    }

    /**
     * 获取日期
     *
     * @param tag
     * @return
     */
    private String getWeek(int tag) {
        switch (tag) {
            case 0:
                return "周一";
            case 1:
                return "周二";
            case 2:
                return "周三";
            case 3:
                return "周四";
            case 4:
                return "周五";
            case 5:
                return "周六";
            case 6:
                return "周日";
        }
        return "";
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

}
