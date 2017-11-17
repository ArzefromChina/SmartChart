package com.smartmove.smartchart.smartChart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.smartmove.smartchart.DisplayUtil;


/**
 * @author ARZE
 * @version 创建时间：2017/7/7 17:35
 * @说明
 */
public class SmartBaseChart extends View {

    private static final String TAG = "SmartBaseChart";

    private RectF mDataRectF;
    private XAxis mXAxis = new XAxis();
    private YAxis mYAxis = new YAxis();
    private static int mPaddingLeft = 150;
    private static int mPaddingBottom = 100;
    private static int mPaddingRight = 50;
    private static int mPaddingTop = 140;
    private static int mYAxisPadding = 12;
    private Paint mXPaint;
    private Paint mYPaint;
    private Paint mTextPaint;

    private SmartChartDelegate mSmartCharDelegate;

    public SmartBaseChart(Context context) {
        super(context);
        init();
    }

    public SmartBaseChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartBaseChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mXPaint = new Paint();
        mXPaint.setAntiAlias(true);
        mXPaint.setColor(mXAxis.getmColor());

        mYPaint = new Paint();
        mYPaint.setAntiAlias(true);
        mYPaint.setColor(mYAxis.getmColor());

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);

        mYAxisPadding = DisplayUtil.dip2px(getContext(), 8);
        mPaddingLeft = DisplayUtil.dip2px(getContext(), 48);
        mPaddingBottom = DisplayUtil.dip2px(getContext(), 30);
        mPaddingRight = DisplayUtil.dip2px(getContext(), 20);
        mPaddingTop = DisplayUtil.dip2px(getContext(), 50);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDataRectF = new RectF(mPaddingLeft, mPaddingTop, w - mPaddingRight, h - mPaddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawXAxis(canvas);
        drawYAxis(canvas);
    }

    private void drawXAxis(Canvas canvas) {
        if (mXAxis.isHasAxis()) {
            canvas.drawLine(mPaddingLeft, getHeight() - mPaddingBottom - mXAxis.getmLineWidht() / 2,
                    getWidth(), getHeight() - mPaddingBottom + mXAxis.getmLineWidht() / 2, mXPaint);
        }
        int interval = (getWidth() - mPaddingLeft - mPaddingRight) / (mXAxis.getmLabelCount() - 1);
        for (int i = 0; i < mXAxis.getmLabelCount(); i++) {
            float left = mPaddingLeft + i * interval;
            float top = mPaddingTop;
            float right = mPaddingLeft + i * interval;
            float bottom = getHeight() - mPaddingBottom;
            if (mXAxis.isHasLabelLine()) {
                LinearGradient gradient = new LinearGradient(left, bottom, right, top,
                        new int[]{mXAxis.getmStartColor(), mXAxis.getmEndColor()}, new float[]{0f, 1f}, Shader.TileMode.CLAMP);
                mXPaint.setShader(gradient);
                canvas.drawLine(left, top, right, bottom, mXPaint);
            }
            if (mXAxis.isHasLabel()) {
                mTextPaint.setTextSize(DisplayUtil.sp2px(getContext(), mXAxis.getmTextSize()));
                mTextPaint.setColor(mXAxis.getmColor());
                String text = "";
                if (null == mXAxis.getmAxisName()) {
                    int xValue = mXAxis.getmMinValue() + i * mXAxis.getmInterval();
                    if (0 == i || mXAxis.getmLabelCount() - 1 == i || 0 == xValue % mXAxis.getNameCount())
                        if (!(30 == xValue && 31 == mXAxis.getmLabelCount()))
                            text = String.valueOf(xValue);
                } else {
                    text = mXAxis.getmAxisName()[i];
                }
                float textLenght = mTextPaint.measureText(text);
                float textHeight = DisplayUtil.getFontHeight(mTextPaint.getTextSize());
                float x = left - textLenght / 2;
                float y = bottom + textHeight;
                canvas.drawText(text, x, y, mTextPaint);
            }
            if (0 == i) {
                if (mXAxis.isHasDescription()) {
                    String text = mXAxis.getmDescription();
                    float textLenght = mTextPaint.measureText(text);
                    float textHeight = DisplayUtil.getFontHeight(mTextPaint.getTextSize());

                    float x = left - textLenght - mYAxisPadding;
                    float y = bottom + textHeight;
                    mTextPaint.setColor(mXAxis.getmDescriotionColor());
                    canvas.drawText(text, x, y, mTextPaint);
                }
            }
        }
    }

    private void drawYAxis(Canvas canvas) {
        if (mYAxis.isHasAxis()) {
            canvas.drawLine(mPaddingLeft, mPaddingTop,
                    mPaddingLeft + mYAxis.getmLineWidht() / 2, getHeight() - mPaddingBottom, mYPaint);
        }
        int interval = (getHeight() - mPaddingBottom - mPaddingTop) / (mYAxis.getmLabelCount() - 1);
        for (int i = 0; i < mYAxis.getmLabelCount(); i++) {
            float left = mPaddingLeft;
            float top = getHeight() - (mPaddingBottom + i * interval);
            float right = getWidth() - mPaddingRight;
            float bottom = getHeight() - (mPaddingBottom + i * interval);
            if (mYAxis.isHasLabelLine()) {
                canvas.drawLine(left, top, right, bottom, mYPaint);
            }
            if (mYAxis.isHasLabel() && 0 != i) {
                mTextPaint.setTextSize(DisplayUtil.dip2px(getContext(), mYAxis.getmTextSize()));
                if (null == mYAxis.getmAxisName()) {
                    String text = String.valueOf(mYAxis.getmMinValue() + (i * mYAxis.getmInterval()));
                    float textLenght = mTextPaint.measureText(text);
                    float textHeight = DisplayUtil.getFontHeight(mTextPaint.getTextSize());
                    float x = left - textLenght - mYAxisPadding;
                    float y = bottom + textHeight / 4;
                    canvas.drawText(text, x, y, mTextPaint);
                }
            }
        }
    }

    public XAxis getmXAxis() {
        return mXAxis;
    }

    public YAxis getmYAxis() {
        return mYAxis;
    }


    public float getXValue(float x) {
        return null == mDataRectF ? 0 : (mDataRectF.width() * x / (mXAxis.getmMaxValue() - mXAxis.getmMinValue()) + mPaddingLeft);
    }

    public float getYValue(float y) {
        return null == mDataRectF ? 0 : (getHeight() - mDataRectF.height() * y / mYAxis.getmMaxValue() - mPaddingBottom);
    }

    public static int getmPaddingBottom() {
        return mPaddingBottom;
    }

    public int getXValue() {
        return mXAxis.getmMaxValue() - mXAxis.getmMinValue();
    }

    public static int getmPaddingLeft() {
        return mPaddingLeft;
    }

    public static int getmPaddingRight() {
        return mPaddingRight;
    }

    public static int getmPaddingTop() {
        return mPaddingTop;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                actionUp(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                 x = event.getX();
                 y = event.getY();
                actionMove(x,y);
                break;
        }
        return true;
    }

    protected void actionUp(float x, float y) {

    }

    protected void actionMove(float x, float y) {

    }

    protected void drawTip(Canvas canvas, float t, float v, String tip) {
        getSmartChartDelegate().drawRoundRect(canvas, t, v, tip);
        getSmartChartDelegate().drawDashLine(canvas, t, v);
        getSmartChartDelegate().drawArrow(canvas, v);
        getSmartChartDelegate().drawTipText(canvas, t, v, tip);
    }

    protected void drawTip(Canvas canvas, float t, float v, String tip,String time) {
        getSmartChartDelegate().drawRoundRect(canvas, t, v, tip);
        getSmartChartDelegate().drawDashLine(canvas, t, v);
        getSmartChartDelegate().drawArrow(canvas, v);
        getSmartChartDelegate().drawTipText(canvas, t, v, tip,time);
    }


    protected void drawDashLine(Canvas canvas, float x1, float y1, float x2, float y2, Paint paint) {
        getSmartChartDelegate().drawDashLine(canvas, x1, y1, x2, y2, paint);
    }

    private SmartChartDelegate getSmartChartDelegate() {
        if (null == mSmartCharDelegate) {
            mSmartCharDelegate = new SmartChartDelegate(getContext(), getWidth(), getHeight());
        }
        return mSmartCharDelegate;
    }

}
