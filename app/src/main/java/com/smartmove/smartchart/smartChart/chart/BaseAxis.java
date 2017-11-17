package com.smartmove.smartchart.smartChart.chart;

import android.graphics.Color;

/**
 * @author ARZE
 * @version 创建时间：2017/7/7 17:41
 * @说明
 */
public class BaseAxis {

    private int mColor = Color.parseColor("#999999");
    private int mStartColor = Color.parseColor("#e6e6e6");
    private int mEndColor = Color.parseColor("#ffffff");
    private float mLineWidth = 3f;
    private int mMaxValue = 24;
    private int mLabelCount = 4;
    private boolean hasDescription = true;
    private String mDescription = "时间";
    private boolean hasLabelLine = false;
    private boolean hasAxis = false;
    private String[] mAxisName;
    private int mInterval = 1;
    private int mMinValue = 0;
    private boolean hasLabel = true;
    private float mTextSize = 11;
    private int mDescriotionColor = Color.parseColor("#387aeb");
    private int mNameCount = 1;

    public BaseAxis setmColor(int mColor) {
        this.mColor = mColor;
        return this;
    }

    public BaseAxis setmLineWidht(float mLineWidth) {
        this.mLineWidth = mLineWidth;
        return this;
    }

    public BaseAxis setmMaxValue(int mMaxValue) {
        this.mMaxValue = mMaxValue;
        int interval = (mMaxValue - mMinValue) / (mLabelCount - 1);
        if (0 != (mMaxValue - mMinValue) % (mLabelCount - 1)) {
            interval ++;
        }
        setmInterval(interval);
        return this;
    }

    public BaseAxis setmLabelCount(int mLabelCount) {
        this.mLabelCount = mLabelCount;
        setmInterval(mMaxValue / (mLabelCount - 1));
        return this;
    }

    public void setHasDescription(boolean hasDescription) {
        this.hasDescription = hasDescription;
    }

    public int getmColor() {
        return mColor;
    }

    public float getmLineWidht() {
        return mLineWidth;
    }

    public int getmMaxValue() {
        return mMaxValue;
    }

    public int getmLabelCount() {
        return mLabelCount;
    }

    public boolean isHasDescription() {
        return hasDescription;
    }

    public float getmLineWidth() {
        return mLineWidth;
    }

    public void setmLineWidth(float mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    public boolean isHasLabelLine() {
        return hasLabelLine;
    }

    public void setHasLabelLine(boolean hasLabelLine) {
        this.hasLabelLine = hasLabelLine;
    }

    public boolean isHasAxis() {
        return hasAxis;
    }

    public void setHasAxis(boolean hasAxis) {
        this.hasAxis = hasAxis;
    }

    public String[] getmAxisName() {
        return mAxisName;
    }

    public BaseAxis setmAxisName(String[] mAxisName) {
        this.mAxisName = mAxisName;
        return this;
    }

    public int getmInterval() {
        return mInterval;
    }

    public BaseAxis setmInterval(int mInterval) {
        this.mInterval = mInterval;
        return this;
    }

    public int getmMinValue() {
        return mMinValue;
    }

    public BaseAxis setmMinValue(int mMinValue) {
        this.mMinValue = mMinValue;
        return this;
    }

    public boolean isHasLabel() {
        return hasLabel;
    }

    public void setHasLabel(boolean hasLabel) {
        this.hasLabel = hasLabel;
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getmStartColor() {
        return mStartColor;
    }

    public int getmEndColor() {
        return mEndColor;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmDescriotionColor() {
        return mDescriotionColor;
    }

    public void setmDescriotionColor(int mDescriotionColor) {
        this.mDescriotionColor = mDescriotionColor;
    }

    public int getNameCount() {
        return mNameCount;
    }

    public BaseAxis setNameCount(int nameCount) {
        mNameCount = nameCount;
        return this;
    }
}