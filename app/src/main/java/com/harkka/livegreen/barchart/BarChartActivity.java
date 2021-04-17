package com.harkka.livegreen.barchart;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

import com.harkka.livegreen.R;


public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        // initialize components
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> emissions = new ArrayList<>();

        //TODO add data inserts from database
        //TODO max one month per one chart
        //TODO when month is full --> move data to file?

        // get date as number of the month
        Calendar c = Calendar.getInstance();
        int wantedDate = c.get(Calendar.DAY_OF_MONTH);
        System.out.println(wantedDate);

        // test emissions for a week
        // format is day of the month (int) + emissions
        emissions.add(new BarEntry(1, 50));
        emissions.add(new BarEntry(2, 220));
        emissions.add(new BarEntry(3, 700));
        emissions.add(new BarEntry(4, 180));
        emissions.add(new BarEntry(10, 400));
        emissions.add(new BarEntry(11, 320));
        emissions.add(new BarEntry(12, 620));
    /*    emissions.add(new BarEntry(8, 450));
        emissions.add(new BarEntry(9, 220));
        emissions.add(new BarEntry(12, 400));
        emissions.add(new BarEntry(13, 320));
        emissions.add(new BarEntry(14, 620));
        emissions.add(new BarEntry(20, 0));
        emissions.add(new BarEntry(21, 700));
        emissions.add(new BarEntry(28, 400));
        emissions.add(new BarEntry(29, 320));
        emissions.add(new BarEntry(30, 20)); */

        // create dataset using library and specify text size and colors
        BarDataSet barDataSet = new BarDataSet(emissions, "Emissions");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // fill the bardata with arraylist data
        BarData barData = new BarData(barDataSet);
        // format the bar chart here
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Daily emissions");
        barChart.animateY(2000);

    }
}
