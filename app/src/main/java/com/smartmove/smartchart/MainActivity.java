package com.smartmove.smartchart;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.smartmove.smartchart.smartChart.chart.SmartLineView;
import com.smartmove.smartchart.smartChart.chartValue.Circle;
import com.smartmove.smartchart.smartChart.chartValue.Line;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testChart();
    }

    private void testChart() {
        setContentView(R.layout.activity_main);
        SmartLineView smartLineView = (SmartLineView) findViewById(R.id.line_chart);
        smartLineView.getmLine().setmMode(Line.NORMAL);
        smartLineView.getmLine().setHasCircle(false);

        SmartLineView circleLineView = (SmartLineView) findViewById(R.id.circle_line_chart);
        circleLineView.getmLine().setmMode(Line.NORMAL);
        circleLineView.getmLine().setHasCircle(true);
        circleLineView.getmLine().setHasShadow(true);
        circleLineView.getmLine().getmCircle().setFill(false);

        SmartLineView onlyCircleView = (SmartLineView) findViewById(R.id.only_circle_line_chart);
        onlyCircleView.getmLine().setmMode(Line.NORMAL);
        onlyCircleView.getmLine().setHasLine(false);
        onlyCircleView.getmLine().setHasCircle(true);
        Circle circle = onlyCircleView.getmLine().getmCircle();
        circle.setmCircleR(10f);
        circle.setFill(false);
        circle.setmCircleStyle(Paint.Style.FILL_AND_STROKE);
        circle.setmCircleColor(Color.parseColor("#38dede"));
    }

}
