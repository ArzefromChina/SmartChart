package com.smartmove.smartchart.smartChart.chartValue;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ARZE
 * @version 创建时间：2017/7/7 19:28
 * @说明
 */
public class Line {

    private List<LineData> mLineData;
    private int mColor = Color.parseColor("#387aeb");
    private boolean hasPointCircle = false;
    private float mLineWidth = 4f;
    private boolean hasShape = true;
    private boolean hasCircle = false;
    private Circle mCircle = new Circle();
    private boolean hasShadow = false;
    private int mShadowColor =Color.parseColor("#300000ff");
    private float mShadowArea = 8f;
    private boolean hasLine = true;

    private int mMode = CUB;
    public static final int CUB = 0x0001;
    public static final int NORMAL = 0x0002;


    public void setLineData(List<LineData> lineData) {
        if (null == mLineData)
            mLineData = new ArrayList<>();
        else
            mLineData.clear();
        mLineData.addAll(lineData);
    }

    public List<LineData> getmLineData() {
        if (null == mLineData)
            mLineData = new ArrayList<>();
        return mLineData;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public boolean isHasPointCircle() {
        return hasPointCircle;
    }

    public void setHasPointCircle(boolean hasPointCircle) {
        this.hasPointCircle = hasPointCircle;
    }

    public boolean isHasShape() {
        return hasShape;
    }

    public void setHasShape(boolean hasShape) {
        this.hasShape = hasShape;
    }

    public void setmLineData(List<LineData> mLineData) {
        this.mLineData = mLineData;
    }

    public float getmLineWidth() {
        return mLineWidth;
    }

    public void setmLineWidth(float mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    public int getmMode() {
        return mMode;
    }

    public void setmMode(int mMode) {
        this.mMode = mMode;
    }

    public Circle getmCircle() {
        return mCircle;
    }

    public void setmCircle(Circle mCircle) {
        this.mCircle = mCircle;
    }

    public boolean isHasCircle() {
        return hasCircle;
    }

    public void setHasCircle(boolean hasCircle) {
        this.hasCircle = hasCircle;
    }

    public boolean isHasShadow() {
        return hasShadow;
    }

    public void setHasShadow(boolean hasShadow) {
        this.hasShadow = hasShadow;
    }

    public int getmShadowColor() {
        return mShadowColor;
    }

    public void setmShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
    }

    public float getmShadowArea() {
        return mShadowArea;
    }

    public void setmShadowArea(float mShadowArea) {
        this.mShadowArea = mShadowArea;
    }

    public boolean isHasLine() {
        return hasLine;
    }

    public void setHasLine(boolean hasLine) {
        this.hasLine = hasLine;
    }
}
