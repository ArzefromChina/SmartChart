package com.smartmove.smartchart.smartChart.chartValue;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author ARZE
 * @version 创建时间：2017/7/8 9:57
 * @说明
 */
public class Circle {

    private Paint.Style mCircleStyle = Paint.Style.STROKE;
    private int mCircleColor = Color.parseColor("#0000ff");
    private float mCircleR = 6f;
    private float mCircleWidth = 3f;
    private boolean isFill = false;

    public Paint.Style getmCircleStyle() {
        return mCircleStyle;
    }

    public void setmCircleStyle(Paint.Style mCircleStyle) {
        this.mCircleStyle = mCircleStyle;
    }

    public int getmCircleColor() {
        return mCircleColor;
    }

    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
    }

    public float getmCircleR() {
        return mCircleR;
    }

    public void setmCircleR(float mCircleR) {
        this.mCircleR = mCircleR;
    }

    public float getmCircleWidth() {
        return mCircleWidth;
    }

    public void setmCircleWidth(float mCircleWidth) {
        this.mCircleWidth = mCircleWidth;
    }

    public boolean isFill() {
        return isFill;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }
}
