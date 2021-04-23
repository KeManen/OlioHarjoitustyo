package com.harkka.livegreen.barchart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.harkka.livegreen.R;

import java.util.ArrayList;
import java.util.Calendar;

public class BarChart2Activity extends AppCompatActivity {

    // TODO add button or command when to draw or refresh the chart

    // Draws bar chart of users food usage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        // initialize components
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> foodUsage = new ArrayList<>();

        // get date as number of the month
        Calendar c = Calendar.getInstance();
        int wantedDate = c.get(Calendar.DAY_OF_MONTH);
        System.out.println(wantedDate);

        //TODO max one month per one chart ?
        //TODO when month is full refresh chart? --> move data to file?

        // test food usage for a week
        // format is day of the month (int) + food used
        //TODO add data inserts from database
        //TODO format is day as integer, food usage as grams

        foodUsage.add(new BarEntry(1, 50));
        foodUsage.add(new BarEntry(2, 220));
        foodUsage.add(new BarEntry(3, 700));
        foodUsage.add(new BarEntry(4, 180));
        foodUsage.add(new BarEntry(10, 400));
        foodUsage.add(new BarEntry(11, 320));
        foodUsage.add(new BarEntry(12, 620));

        // create dataset using library and specify text size and colors
        BarDataSet barDataSet = new BarDataSet(foodUsage, "Food used");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // fill the bardata with arraylist data
        BarData barData = new BarData(barDataSet);
        // format the bar chart here
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Food usage");
        barChart.animateY(2000);

    }
}
