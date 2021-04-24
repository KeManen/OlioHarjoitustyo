package com.harkka.livegreen.ui.dataview;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.harkka.livegreen.R;

import java.util.ArrayList;
import java.util.Calendar;

public class DataFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_data, container, false);

        // initialize components
        BarChart barChart = root.findViewById(R.id.barChart);
        ArrayList<BarEntry> emissions = new ArrayList<>();

        // get date as number of the month
        Calendar c = Calendar.getInstance();
        int wantedDate = c.get(Calendar.DAY_OF_MONTH);
        System.out.println(wantedDate);

        //TODO add data inserts from database
        //TODO max one month per one chart
        //TODO when month is full --> move data to file?
        //TODO format is date as integer + Emissions as KG

        // test emissions for a week
        // format is day of the month (int) + emissions
        emissions.add(new BarEntry(1, 50));
        emissions.add(new BarEntry(2, 220));
        emissions.add(new BarEntry(3, 700));
        emissions.add(new BarEntry(4, 180));
        emissions.add(new BarEntry(10, 400));
        emissions.add(new BarEntry(11, 320));
        emissions.add(new BarEntry(12, 620));

        // create dataset using library and specify text size and colors
        BarDataSet barDataSet = new BarDataSet(emissions, "");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);

        // fill the bardata with arraylist data
        BarData barData = new BarData(barDataSet);
        // format the bar chart here
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Daily emissions");
        barChart.animateY(2000);

/////////////////////////////////////////////////////////////////


        // initialize components
        BarChart barChart2 = root.findViewById(R.id.barChart2);
        ArrayList<BarEntry> foodUsage = new ArrayList<>();

        // get date as number of the month
        Calendar c2 = Calendar.getInstance();
        int wantedDate2 = c2.get(Calendar.DAY_OF_MONTH);
        System.out.println(wantedDate2);

        //TODO max one month per one chart ?
        //TODO when month is full refresh chart? --> move data to file?

        // test food usage for a week
        // format is day of the month (int) + food used
        //TODO add data inserts from database
        //TODO format is day as integer, food usage as grams

        foodUsage.add(new BarEntry(1, 5));
        foodUsage.add(new BarEntry(2, 9));
        foodUsage.add(new BarEntry(3, 5));
        foodUsage.add(new BarEntry(4, 4));
        foodUsage.add(new BarEntry(6, 5));
        foodUsage.add(new BarEntry(7, 9));
        foodUsage.add(new BarEntry(9, 5));
        foodUsage.add(new BarEntry(10, 4));
        foodUsage.add(new BarEntry(11, 3));
        foodUsage.add(new BarEntry(12, 4));

        // create dataset using library and specify text size and colors
        BarDataSet barDataSet2 = new BarDataSet(foodUsage, "");
        barDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet2.setValueTextColor(Color.WHITE);
        barDataSet2.setValueTextSize(16f);

        // fill the bardata with arraylist data
        BarData barData2 = new BarData(barDataSet2);
        // format the bar chart here
        barChart2.setFitBars(true);
        barChart2.setData(barData2);
        barChart2.getDescription().setText("Food usage");
        barChart2.animateY(2000);

        return root;
    }
}