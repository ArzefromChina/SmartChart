package com.smartmove.smartchart.smartChart.chartValue;

import android.graphics.RectF;

/**
 * Created by Administrator on 2017/7/8.
 */

public class BarData extends BaseChartData {

    private RectF mRectF;
    private boolean isSelect = false;

    public RectF getmRectF() {
        return mRectF;
    }

    public void setmRectF(RectF mRectF) {
        this.mRectF = mRectF;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
