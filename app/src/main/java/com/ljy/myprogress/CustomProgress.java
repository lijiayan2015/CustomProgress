package com.ljy.myprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class CustomProgress extends View {

    //画笔
    private Paint paint;

    //圆环的颜色
    private int roundColor;

    //圆环进度条的颜色
    private int roundProgressColor;

    //中间进度条百分比字符串的颜色
    private int textColor;

    //字体大小
    private float textSize;

    //圆环的宽度
    private float roundWidth;

    //最大进度
    private int max;

    //当前进度条
    private int progress;

    //是否显示中间的进度条文本
    private boolean textIsDiaplay;

    //进度条的风格:实心或者空心
    private int style;

    public static final int STROKE = 1;
    public static final int FILL = 0;

    public CustomProgress(Context context) {
        this(context, null);
    }

    public CustomProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomProgress);
        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.CustomProgress_roundColor,
                Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.CustomProgress_roundProgressColor,
                Color.BLUE);
        textColor = mTypedArray.getColor(R.styleable.CustomProgress_textColor,
                Color.RED);
        textSize = mTypedArray.getDimension(R.styleable.CustomProgress_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.CustomProgress_roundWidth, 5);
        max = mTypedArray.getInt(R.styleable.CustomProgress_max, 100);
        textIsDiaplay = mTypedArray.getBoolean(R.styleable.CustomProgress_textIsDisplay, true);
        style = mTypedArray.getInt(R.styleable.CustomProgress_style, 0);
        mTypedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取控件的宽
        int width = getWidth();
        //获取控件的高
        int height = getHeight();
        //中心点的坐标是宽和高中最小的一半
        int center = width > height ? height / 2 : width / 2;
        //半径
        int radius = (int) (center - roundWidth / 2);
        //设置颜色
        paint.setColor(roundColor);
        //s设置空心
        paint.setStyle(Paint.Style.STROKE);
        //设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        //消除锯齿
        paint.setAntiAlias(true);
        //画圆环
        canvas.drawCircle(center, center, radius, paint);

        //画进度百分比
        paint.setColor(textColor);
        paint.setStrokeWidth(0);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        int percent = (int) (((float) progress) / ((float) max) * 100);
        String text = percent + "%";
        float textWidth = paint.measureText(text);
        if (textIsDiaplay && percent != 0 && style == STROKE) {
            canvas.drawText(text, center - textWidth / 2, center + textSize / 2, paint);
        }

        //画圆弧,画圆环的进度
        paint.setStrokeWidth(roundWidth);
        paint.setColor(roundProgressColor);
        RectF ovel = new RectF(center - radius, center - radius, center + radius, center + radius);
        switch (style) {
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(ovel, 0, 360 * (((float) progress) / max), false, paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawArc(ovel, 0, 360 * (((float) progress) / max), true, paint);
                break;
        }
    }

    /**
     * 获取进度条的最大值
     *
     * @return
     */
    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度条的最大值
     *
     * @param max
     */
    public synchronized void setMax(int max) {
        if (max <= 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取当前的进度
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    public synchronized void  setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max)
            progress = max;

        if (progress <= max) {
            this.progress = progress;
            postInvalidate();
        }
    }


    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getRoundProgressColor() {
        return roundProgressColor;
    }

    public void setRoundProgressColor(int roundProgressColor) {
        this.roundProgressColor = roundProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public boolean isTextIsDiaplay() {
        return textIsDiaplay;
    }

    public void setTextIsDiaplay(boolean textIsDiaplay) {
        this.textIsDiaplay = textIsDiaplay;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
