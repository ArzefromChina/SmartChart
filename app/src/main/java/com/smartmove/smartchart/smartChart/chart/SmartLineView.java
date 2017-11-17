package com.smartmove.smartchart.smartChart.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.smartmove.smartchart.smartChart.chartValue.Line;
import com.smartmove.smartchart.smartChart.chartValue.LineData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ARZE
 * @version 创建时间：2017/7/7 19:16
 * @说明
 */
public class SmartLineView extends SmartBaseChart {

    private static final String TAG = "SmartLineView";

    private Line mLine = new Line();
    private Path path = new Path();
    private Paint mLinePaint;

    private PointF mPointF = new PointF();

    public SmartLineView(Context context) {
        super(context);
        initSmartLineView();
    }

    public SmartLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSmartLineView();
    }

    public SmartLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSmartLineView();
    }

    private void initSmartLineView() {
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        setDefaultXY();
        List<LineData> lineDatas = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            LineData data = new LineData();
            data.setmPointX(i * 4);
            data.setmPointY(random.nextInt(40));
            lineDatas.add(data);
        }
        setLineData(lineDatas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != mLine) {
            switch (mLine.getmMode()) {
                case Line.CUB:
                    drawCubLine(canvas);
                    drawArea(canvas);
                    path.reset();
                    break;
                case Line.NORMAL:
                    if (mLine.isHasLine())
                        drawNormaLine(canvas);
                    path.reset();
                    break;
            }

            if (mLine.isHasCircle()) {
                drawCircleLabel(canvas);
            }
        }
        if (mPointF.x > 0f && mPointF.y > 0f) {
            drawLineTip(canvas);
        }
    }

    private void drawLineTip(Canvas canvas) {
        float min = Float.MAX_VALUE;
        LineData minLine = null;
        for (int i = 0; i < getmLine().getmLineData().size(); i++) {
            LineData lineData = getmLine().getmLineData().get(i);
            float x = getXValue(lineData.getmPointX());
            if (Math.abs(x - mPointF.x) < min) {
                minLine = lineData;
                min = Math.abs(x - mPointF.x);
            }
        }
        if (null != minLine && !TextUtils.isEmpty(minLine.getTip())) {
            drawTip(canvas, getYValue(minLine.getmPointY()), getXValue(minLine.getmPointX())
                    , minLine.getTip(), minLine.getTime());
        }
    }

    private void drawCircleLabel(Canvas canvas) {
        mLinePaint.setStyle(mLine.getmCircle().getmCircleStyle());
        mLinePaint.setStrokeWidth(mLine.getmCircle().getmCircleWidth());
        mLinePaint.setStrokeWidth(mLine.getmCircle().getmCircleColor());
        int count = mLine.getmLineData().size();
        for (int i = 0; i < count; i++) {
            LineData lineData = mLine.getmLineData().get(i);
            float x = getXValue(lineData.getmPointX());
            float y = getYValue(lineData.getmPointY());
            float left = x - mLine.getmCircle().getmCircleR();
            float top = y - mLine.getmCircle().getmCircleR();
            float right = x + mLine.getmCircle().getmCircleR();
            float bottom = y + mLine.getmCircle().getmCircleR();
            RectF rectF = new RectF(left, top, right, bottom);
            mLinePaint.setColor(mLine.getmCircle().getmCircleColor());
            canvas.drawOval(rectF, mLinePaint);
            if (!mLine.getmCircle().isFill()) {
                float r = mLine.getmCircle().getmCircleR() - mLine.getmCircle().getmCircleWidth() / 2;
                RectF circleRectF = new RectF(x - r, y - r, x + r, y + r);
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(Color.WHITE);
                canvas.drawOval(circleRectF, paint);
            }
        }
    }

    private void drawNormaLine(Canvas canvas) {
        mLinePaint.setColor(mLine.getmColor());
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mLine.getmLineWidth());
        int count = mLine.getmLineData().size();
        for (int i = 0; i < count; i++) {
            LineData lineData = mLine.getmLineData().get(i);
            float x = getXValue(lineData.getmPointX());
            float y = getYValue(lineData.getmPointY());
            if (0 == i) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        canvas.drawPath(path, mLinePaint);
        if (mLine.isHasShadow()) {
            for (int i = count - 1; i >= 0; i--) {
                LineData lineData = mLine.getmLineData().get(i);
                float x = getXValue(lineData.getmPointX());
                float y = getYValue(lineData.getmPointY()) + mLine.getmShadowArea();
                path.lineTo(x, y);
            }
            path.close();
            Paint paint = new Paint(mLinePaint);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(mLine.getmShadowColor());
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setLineData(List<LineData> lineData) {
        mLine.setLineData(lineData);
        invalidate();
    }

    public void setLineData(List<LineData> lineData, boolean auto, int number) {
        if (auto) {
            int max = getMaxValue(lineData);
            int count = max / number + 1;
            getmYAxis().setmMaxValue(count * number);
        }
        mLine.setmLineData(lineData);
        invalidate();
    }

    private int getMaxValue(List<LineData> lineData) {
        int max = 0;
        for (LineData barData : lineData) {
            max = barData.getmPointY() > max ? (int) barData.getmPointY() : max;
        }
        return max;
    }

    public void setDefaultXY() {
        XAxis x = getmXAxis();
        x.setmLabelCount(7);
        x.setmMaxValue(24);
        x.setHasLabelLine(true);
        x.setHasAxis(false);

        YAxis y = getmYAxis();
        y.setmLabelCount(4);
        y.setmMaxValue(60);
        y.setHasLabelLine(false);
        y.setHasAxis(false);
        invalidate();
    }

    private void drawCubLine(Canvas canvas) {
        mLinePaint.setColor(mLine.getmColor());
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mLine.getmLineWidth());
        canvas.save();
        final int lineSize = mLine.getmLineData().size();
        Log.w(TAG, "RUN---------->" + lineSize);
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX = Float.NaN;
        float nextPointY = Float.NaN;
        List<LineData> list = mLine.getmLineData();
        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                LineData point = list.get(valueIndex);
                currentPointX = getXValue(point.getmPointX());
                currentPointY = getYValue(point.getmPointY());
            }
            if (Float.isNaN(previousPointX)) {
                if (valueIndex > 0) {
                    LineData point = list.get(valueIndex - 1);
                    previousPointX = getXValue(point.getmPointX());
                    previousPointY = getYValue(point.getmPointY());
                } else {
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                if (valueIndex > 1) {
                    LineData point = list.get(valueIndex - 2);
                    prePreviousPointX = getXValue(point.getmPointX());
                    prePreviousPointY = getYValue(point.getmPointY());
                } else {
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }
            if (valueIndex < lineSize - 1) {
                LineData point = list.get(valueIndex + 1);
                nextPointX = getXValue(point.getmPointX());
                nextPointY = getYValue(point.getmPointY());
            } else {
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                path.moveTo(currentPointX, currentPointY);
            } else {
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX = previousPointX + (0.16f * firstDiffX);
                final float firstControlPointY = previousPointY + (0.16f * firstDiffY);
                final float secondControlPointX = currentPointX - (0.16f * secondDiffX);
                final float secondControlPointY = currentPointY - (0.16f * secondDiffY);
                final float maxHeight = getHeight() - getmPaddingBottom();
                path.cubicTo(
                        firstControlPointX,
                        firstControlPointY > maxHeight ? maxHeight : firstControlPointY,
                        secondControlPointX,
                        secondControlPointY > maxHeight ? maxHeight : secondControlPointY,
                        currentPointX,
                        currentPointY > maxHeight ? maxHeight : currentPointY);
            }
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
            Log.w(TAG, "RUN---------->" + currentPointX + " o r  " + currentPointY);
        }
        canvas.drawPath(path, mLinePaint);
        canvas.restore();
    }

    private void drawArea(Canvas canvas) {
        canvas.save();
        if (mLine.getmLineData().size() < 1)
            return;
        List<LineData> points = mLine.getmLineData();
        final float baseRawValue = getHeight() - getmPaddingBottom();
        final float left = getXValue(points.get(0).getmPointX());
        final float right = getXValue(points.get(points.size() - 1).getmPointX());
        path.lineTo(right, baseRawValue);
        path.lineTo(left, baseRawValue);
        path.close();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#0f84beff"));
        paint.setAlpha(50);
        canvas.drawPath(path, paint);
        canvas.restore();
    }

    public Line getmLine() {
        return mLine;
    }

    @Override
    protected void actionUp(float x, float y) {
        super.actionUp(x, y);
        mPointF.x = x;
        mPointF.y = y;
        Log.w(TAG, "actionUp::" + x + " or  " + y);
        invalidate();
    }

    @Override
    protected void actionMove(float x, float y) {
        super.actionMove(x, y);
        mPointF.x = x;
        mPointF.y = y;
        Log.w(TAG, "actionUp::" + x + " or  " + y);
        invalidate();
    }

}
