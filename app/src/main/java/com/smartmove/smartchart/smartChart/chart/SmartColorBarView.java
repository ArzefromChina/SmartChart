package com.smartmove.smartchart.smartChart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.smartmove.smartchart.DisplayUtil;
import com.smartmove.smartchart.smartChart.chartValue.BarData;
import com.smartmove.smartchart.smartChart.chartValue.ColorBar;
import com.smartmove.smartchart.smartChart.chartValue.ColorBarData;

import java.util.List;


/**
 * Created by Administrator on 2017/8/11.
 */

public class SmartColorBarView extends SmartBaseChart {

    private static final String TAG = "SmartBarView";

    private ColorBar mColorBar = new ColorBar();
    private Paint mBarPaint;
    private Paint mTargetPaint;
    private boolean hasTargetLine = true;
    private int mTargetValue = 10000;
    private OnBarSelectListener mOnBarSelectListener;

    public SmartColorBarView(Context context) {
        super(context);
        initSmartBarView();
    }

    public SmartColorBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSmartBarView();
    }

    public SmartColorBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSmartBarView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBar(canvas);
        if (hasTargetLine)
            drawTargetLine(canvas);
    }

    private void drawTargetLine(Canvas canvas) {
        float x1 = 0 + getmPaddingLeft() - 6;
        float y1 = getYValue(mTargetValue);
        float x2 = getWidth() - getmPaddingRight();
        float y2 = y1;
        drawDashLine(canvas, x1, y1, x2, y2, mTargetPaint);
    }

    private void drawBar(Canvas canvas) {
        int count = mColorBar.getColorBarDatas().size();
        mBarPaint.setColor(mColorBar.getmColor());
        for (int i = 0; i < count; i++) {
            ColorBarData colorBarData = mColorBar.getColorBarDatas().get(i);
            float width = DisplayUtil.dip2px(getContext(), mColorBar.getmWidth());
            float x = getXValue(colorBarData.getmPointX());
            float y = getYValue(colorBarData.getmPointY());
            float left = x - width / 2;
            float top = y;
            float right = x + width / 2;
            float bottom = getHeight() - getmPaddingBottom();
            RectF rectF = new RectF(left, top, right, bottom);
            colorBarData.setmRectF(rectF);
            if (3 == colorBarData.getValues().length) {
                drawPartRectF(rectF, colorBarData, canvas, 0);
                  drawPartRectF(rectF, colorBarData, canvas, 2);
                drawPartRectF(rectF, colorBarData, canvas, 1);
            }
            if (colorBarData.isSelect() ) {
                drawTip(canvas, top, rectF.centerX(),colorBarData.getTip(),colorBarData.getTime());
            }
        }
    }

    private void drawPartRectF(RectF rectF, ColorBarData colorBarData, Canvas canvas, int position) {
        RectF partRectF = new RectF(rectF.left, rectF.top, rectF.right, rectF.bottom);
        partRectF.bottom = rectF.bottom - getValue(position, colorBarData.getValues()) / colorBarData.getmPointY() * rectF.height();
        partRectF.top = partRectF.bottom - colorBarData.getValues()[position] / colorBarData.getmPointY() * rectF.height();
        Log.w(TAG, "drawBar:: " + partRectF.toString() + "    or  " + position);
        mBarPaint.setColor(colorBarData.getColors()[position]);
        Log.w(TAG, "drawBarColor:: " + mBarPaint.getColor() + "");
        if (1 == position) {
            partRectF.bottom = partRectF.bottom + 4f;
            partRectF.top = partRectF.top - 4f;
            canvas.drawRoundRect(partRectF, 0, 0, mBarPaint);
        } else {
            canvas.drawRoundRect(partRectF, mColorBar.getmDegree(), mColorBar.getmDegree(), mBarPaint);
        }
    }

    private float getValue(int j, float[] values) {
        float sum = 0f;
        for (int i = 0; i < values.length; i++) {
            if (i < j)
                sum += values[i];
            else
                break;
        }
        return sum;
    }

    private void initSmartBarView() {
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);

        mTargetPaint = new Paint();
        mTargetPaint.setAntiAlias(true);
        mTargetPaint.setStrokeWidth(1);
        mTargetPaint.setColor(Color.parseColor("#ec3a42"));
        setDefaultXY();
    }

    public void setBarData(List<ColorBarData> lineData) {
        mColorBar.setColorBarDatas(lineData);
        invalidate();
    }

    public void setBarData(List<ColorBarData> lineData, boolean isAuto) {
        if (isAuto) {
            float max = 0f;
            for (int i = 0; i < lineData.size(); i++) {
                max = max > lineData.get(i).getmPointY() ? max : lineData.get(i).getmPointY();
            }
            if (max > 12) {
                getmYAxis().setmMaxValue(24);
            }
        }
        mColorBar.setColorBarDatas(lineData);
        invalidate();
    }

    public ColorBar getColorBar() {
        return mColorBar;
    }

    public void setDefaultXY() {
        XAxis x = getmXAxis();
        x.setmLabelCount(7);
        x.setmMaxValue(24);
        x.setHasLabelLine(true);
        x.setHasAxis(false);

        YAxis y = getmYAxis();
        y.setmLabelCount(3);
        y.setmMaxValue(60);
        y.setHasLabelLine(false);
        y.setHasAxis(false);
        invalidate();
    }

    @Override
    protected void actionUp(float x, float y) {
        super.actionUp(x, y);
        if (null == mColorBar)
            return;
        List<ColorBarData> barDatas = mColorBar.getColorBarDatas();
        for (ColorBarData colorBarData : barDatas) {
            RectF rectF = new RectF(colorBarData.getmRectF());
            rectF.left = rectF.left - 10;
            rectF.right = rectF.right + 10;
            if (rectF.contains(x, y)) {
                colorBarData.setSelect(true);
                cancelOtherSelect(barDatas, colorBarData);
                if (null != mOnBarSelectListener) {
                    mOnBarSelectListener.barSelect(colorBarData);
                }
                break;
            }
        }
        invalidate();
    }

    private void cancelOtherSelect(List<ColorBarData> barDatas, BarData data) {
        for (BarData barData : barDatas) {
            if (barData != data) {
                barData.setSelect(false);
            }
        }
    }

    public interface OnBarSelectListener {
        void barSelect(BarData barData);
    }

    public void setOnBarSelectListener(OnBarSelectListener onBarSelectListener) {
        mOnBarSelectListener = onBarSelectListener;
    }

    public void setTargetValue(int targetValue) {
        mTargetValue = targetValue;
        invalidate();
    }
}
