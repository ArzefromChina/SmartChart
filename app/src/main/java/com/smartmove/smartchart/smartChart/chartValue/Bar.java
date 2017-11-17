package com.smartmove.smartchart.smartChart.chartValue;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class Bar {

    private List<BarData> mBarData;
    private int mColor = Color.parseColor("#387aeb");
    private float mWidth = 6;
    private float mDegree = 10f;

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }

    public List<BarData> getmBarData() {
        if (null == mBarData)
            mBarData = new ArrayList<>();
        return mBarData;
    }

    public void setmBarData(List<BarData> barData) {
        if (null == this.mBarData)
            this.mBarData = new ArrayList<>();
        else
            this.mBarData.clear();
        this.mBarData.addAll(barData);
    }

    public float getmWidth() {
        return mWidth;
    }

    public void setmWidth(float mWidth) {
        this.mWidth = mWidth;
    }

    public float getmDegree() {
        return mDegree;
    }

    public void setmDegree(float mDegree) {
        this.mDegree = mDegree;
    }
}
