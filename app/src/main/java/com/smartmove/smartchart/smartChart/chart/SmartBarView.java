package com.smartmove.smartchart.smartChart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.smartmove.smartchart.DisplayUtil;
import com.smartmove.smartchart.smartChart.chartValue.Bar;
import com.smartmove.smartchart.smartChart.chartValue.BarData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @author ARZE
 * @version 创建时间：2017/7/7 20:35
 * @说明
 */
public class SmartBarView extends SmartBaseChart {

    private static final String TAG = "SmartBarView";

    private Bar mBar = new Bar();
    private Paint mBarPaint;
    private Paint mTargetPaint;
    private boolean hasTargetLine = true;
    private int mTargetValue = 10000;
    private OnBarSelectListener mOnBarSelectListener;

    public SmartBarView(Context context) {
        super(context);
        initSmartBarView();
    }

    public SmartBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSmartBarView();
    }

    public SmartBarView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int count = mBar.getmBarData().size();
        mBarPaint.setColor(mBar.getmColor());
        Log.w("ARZE", "run---> drawBar" + count);
        for (int i = 0; i < count; i++) {
            BarData barData = mBar.getmBarData().get(i);
            float width = DisplayUtil.dip2px(getContext(), mBar.getmWidth());
            float x = getXValue(barData.getmPointX());
            float y = getYValue(barData.getmPointY());
            float left = x - width / 2;
            float top = y;
            float right = x + width / 2;
            float bottom = getHeight() - getmPaddingBottom();
            RectF rectF = new RectF(left, top, right, bottom);
            barData.setmRectF(rectF);
            canvas.drawRoundRect(rectF, mBar.getmDegree(), mBar.getmDegree(), mBarPaint);
            if (barData.isSelect()) {
                drawTip(canvas, getYValue(barData.getmPointY()), getXValue(barData.getmPointX())
                        , barData.getTip(), barData.getTime());
            }
        }
    }

    private void initSmartBarView() {
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mTargetPaint = new Paint();
        mTargetPaint.setAntiAlias(true);
        mTargetPaint.setStrokeWidth(1);
        mTargetPaint.setColor(Color.parseColor("#ec3a42"));
        setDefaultXY();
        Random random = new Random();
        List<BarData> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            BarData barData = new BarData();
            barData.setmPointX(i * 4);
            barData.setmPointY(random.nextInt(40));
            Log.w("initSmartBarView", "initSmartBarView" + barData.getmPointY());
            list.add(barData);
        }
        setBarData(list);
        Log.w("ARZE", "run---> initSmartBarView" + mBar.getmBarData().size() + " or " + list.size());
    }

    public void setBarData(List<BarData> lineData) {
        mBar.setmBarData(lineData);
        invalidate();
    }

    public void setBarData(List<BarData> lineData, boolean auto, int number) {
        if (auto) {
            int max = getMaxValue(lineData);
            if (max > number) {
                int count = max / number + 1;
                getmYAxis().setmMaxValue(count * number);
            }
        }
        mBar.setmBarData(lineData);
        invalidate();
    }

    private int getMaxValue(List<BarData> lineData) {
        int max = 0;
        for (BarData barData : lineData) {
            max = barData.getmPointY() > max ? (int) barData.getmPointY() : max;
        }
        return max;
    }

    public Bar getBar() {
        return mBar;
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
        if (null == mBar)
            return;
        List<BarData> barDatas = mBar.getmBarData();
        for (BarData barData : barDatas) {
            RectF rectF = new RectF(barData.getmRectF());
            rectF.left = rectF.left - 10;
            rectF.right = rectF.right + 10;
            if (rectF.contains(x, y)) {
                barData.setSelect(true);
                cancelOtherSelect(barDatas, barData);
                if (null != mOnBarSelectListener) {
                    mOnBarSelectListener.barSelect(barData);
                }
                break;
            }
        }
        invalidate();
    }

    private void cancelOtherSelect(List<BarData> barDatas, BarData data) {
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
