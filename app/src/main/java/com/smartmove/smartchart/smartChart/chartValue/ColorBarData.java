package com.smartmove.smartchart.smartChart.chartValue;

/**
 * Created by Administrator on 2017/8/11.
 */

public class ColorBarData extends BarData {

    private float[] mValues = new float[0];
    private int[] mColors = new int[0];

    public float[] getValues() {
        return mValues;
    }

    public void setValues(float[] values) {
        mValues = values;
    }

    public int[] getColors() {
        return mColors;
    }

    public void setColors(int[] colors) {
        mColors = colors;
    }

    public float getValuesSum() {
        float sum = 0f;
        for (int i = 0; i < mValues.length; i++) {
            sum += mValues[i];
        }
        return sum;
    }

    public float getSleepValuesSum() {
        float sum = 0f;
        for (int i = 0; i < mValues.length - 1; i++) {
            sum += mValues[i];
        }
        return sum;
    }
}
