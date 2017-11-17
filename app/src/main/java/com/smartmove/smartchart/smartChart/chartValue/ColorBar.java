package com.smartmove.smartchart.smartChart.chartValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ColorBar extends Bar {

    private List<ColorBarData> mColorBarData = new ArrayList<>();

    public List<ColorBarData> getColorBarDatas() {
        return mColorBarData;
    }

    public void setColorBarDatas(List<ColorBarData> colorBarDatas) {
        mColorBarData = colorBarDatas;
    }
}
