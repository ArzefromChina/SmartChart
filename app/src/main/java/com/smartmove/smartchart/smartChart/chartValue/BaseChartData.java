package com.smartmove.smartchart.smartChart.chartValue;

/**
 * @author ARZE
 * @version 创建时间：2017/7/7 19:17
 * @说明
 */
public class BaseChartData {

    private float mPointX;
    private float mPointY;
    private String mTip = "";
    private String mTime = "";

    public float getmPointX() {
        return mPointX;
    }

    public void setmPointX(float mPointX) {
        this.mPointX = mPointX;
    }

    public float getmPointY() {
        return mPointY;
    }

    public void setmPointY(float mPointY) {
        this.mPointY = mPointY;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTip() {
        return mTip;
    }

    public void setTip(String tip) {
        mTip = tip;
    }
}
