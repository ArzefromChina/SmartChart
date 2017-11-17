package com.smartmove.smartchart.smartChart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.smartmove.smartchart.DisplayUtil;


/**
 * @author ARZE
 * @version 创建时间：2017/7/15 10:38
 * @说明
 */
public class SmartChartDelegate {

    private Context mContext;
    private int mWidth;
    private int mHeight;

    private Paint mTipPaint;
    private Paint mTipTextPaint;
    private int mTipColor = Color.parseColor("#387aeb");
    private static float TIP_HEIGHT = 72;
    private static float TIP_WIDTH = 160;

    public SmartChartDelegate(Context mContext, int mWidth, int mHeight) {
        this.mContext = mContext;
        this.mWidth = mWidth;
        this.mHeight = mHeight;
        init();
    }

    private void init() {
        mTipPaint = new Paint();
        mTipPaint.setAntiAlias(true);
        mTipPaint.setColor(mTipColor);

        TIP_HEIGHT = DisplayUtil.dip2px(mContext, 36);
        TIP_WIDTH = DisplayUtil.dip2px(mContext, 80);

    }

    private RectF correct(RectF rectF) {
        if (rectF.left < 0) {
            float left = rectF.left;
            rectF.left = 0;
            rectF.right = rectF.right - left;
        }
        if (rectF.right > mWidth) {
            float right = rectF.right;
            rectF.right = mWidth;
            rectF.left = rectF.left - (right - mWidth);
        }
        return rectF;
    }

    protected void drawRoundRect(Canvas canvas, float t, float v, String tip) {
        float left = v - TIP_WIDTH / 2;
        float top = 0;
        float right = left + TIP_WIDTH;
        float bottom = top + TIP_HEIGHT;
        RectF rectF = new RectF(left, top, right, bottom);
        rectF = correct(rectF);
        float dp = DisplayUtil.dip2px(mContext, 8);
        canvas.drawRoundRect(rectF, dp, dp, mTipPaint);
    }

    protected void drawArrow(Canvas canvas, float v) {
        float value = DisplayUtil.dip2px(mContext, 6);
        Path path = new Path();
        path.moveTo(v, TIP_HEIGHT + value);
        path.lineTo(v + value, TIP_HEIGHT);
        path.lineTo(v - value, TIP_HEIGHT);
        path.close();
        canvas.drawPath(path, mTipPaint);
        path.reset();
    }

    protected void drawTipText(Canvas canvas, float t, float v, String tip) {
        float textLength = getTextPaint().measureText(tip);
        float textHeight = DisplayUtil.getFontHeight(DisplayUtil.sp2px(mContext, 12));
        float x = v - textLength / 2;
        float y = TIP_HEIGHT / 2 + textHeight / 4;
        canvas.drawText(tip,x,y,getTextPaint());
    }

    protected void drawTipText(Canvas canvas, float t, float v, String tip,String time) {
        float textLength = getTextPaint().measureText(tip);
        float timeTextLength = getTextPaint().measureText(time);
        float textHeight = DisplayUtil.getFontHeight(DisplayUtil.sp2px(mContext, 12));
        float x = v - textLength / 2;
        float timeX = v - timeTextLength / 2;
        float y = TIP_HEIGHT / 2 - textHeight / 4;
        float timeY = TIP_HEIGHT / 2 + 3 * textHeight /4;
        canvas.drawText(tip,x,y,getTextPaint());
        canvas.drawText(time,timeX,timeY,getTextPaint());
    }

    protected void drawDashLine(Canvas canvas, float t, float v) {
        float x1 = v;
        float y1 = TIP_HEIGHT;
        float x2 = v;
        float y2 = t;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mTipColor);
        drawDashLine(canvas, x1, y1, x2, y2, paint);
    }

    protected void drawDashLine(Canvas canvas, float x1, float y1, float x2, float y2, Paint paint) {
        Path path = new Path();
        path.addRect(new RectF(0, 0, 1, 12), Path.Direction.CCW);
        paint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        paint.setStyle(Paint.Style.STROKE);
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        canvas.drawPath(path, paint);
    }

    private Paint getTextPaint() {
        if (null == mTipTextPaint) {
            mTipTextPaint = new Paint();
            mTipTextPaint.setAntiAlias(true);
            mTipTextPaint.setColor(Color.WHITE);
            mTipTextPaint.setTextSize(DisplayUtil.sp2px(mContext, 10));
        }
        return mTipTextPaint;
    }

}
