package com.harkka.livegreen.ui.dataview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.harkka.livegreen.R;
import com.harkka.livegreen.entry.Entry;
import com.harkka.livegreen.entry.EntryManager;
import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.roomdb.UserDao;
import com.harkka.livegreen.roomdb.UserDatabase;
import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.user.UserManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class DataFragment extends Fragment {

    // Variables for user management
    UserManager uManager = UserManager.getInstance(getContext()); // Singleton for User class usage
    // Variables for entry management
    EntryManager entryManager = EntryManager.getInstance(); // Singleton for Entry class usage
    UserDatabase userDatabase;
    UserDao userDao;
    DataDao dataDao;
    UUID auxGuid;
    DataEntity[] dataEntities;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_data, container, false);

        // initialize components
        BarChart barChart = root.findViewById(R.id.barChart);
        ArrayList<BarEntry> foodUsage = new ArrayList<>();
        Context context = this.getContext();

        //Todo for test use
        userDatabase = UserDatabase.getUserDatabase(context.getApplicationContext());
        userDao = userDatabase.userDao();
        dataDao = userDatabase.dataDao();
        UUID uGuid = null;
        uGuid = uManager.getCurrentUserUUID();

        Entry entry = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            entry = entryManager.createEntry(uGuid);
        }
        entryManager.setEntry(entry);
        auxGuid = uGuid;

        new Thread(() -> {
            System.out.println("In new Thread - Load entities ******************" + auxGuid.toString() + "******************");
            dataEntities = dataDao.loadAllDataEntitiesByUserId(auxGuid.toString());
        }).start();

        // Sleep for 1second so db thread has time to finalize load
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // clear old values from arraylist
        foodUsage.clear();
        //add values from database
        for (int i = 0; i < dataEntities.length; i++) {

            float totalGrams = Float.parseFloat(dataEntities[i].getDairyUsed()) + Float.parseFloat(dataEntities[i].getMeatUsed()) + Float.parseFloat(dataEntities[i].getVegeUsed());
            System.out.println("Total FOOD USED IN GRAMS: " + totalGrams);
            foodUsage.add(new BarEntry(i, totalGrams));
        }

        // create dataset using library and specify text size and colors
        BarDataSet barDataSet = new BarDataSet(foodUsage, "");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);

        // fill the bardata with arraylist data
        BarData barData = new BarData(barDataSet);
        // format the bar chart here
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Food usage");
        barChart.animateY(2000);

        /////////////////////////////////////////////////////////////////
        //////////////////     Draw second chart       //////////////////
        /////////////////////////////////////////////////////////////////

        // initialize components
        BarChart barChart2 = root.findViewById(R.id.barChart2);
        ArrayList<BarEntry> emissions = new ArrayList<>();

        // clear old values from arraylist
        emissions.clear();
        // add values from database
        for (int i = 0; i < dataEntities.length; i++) {

            emissions.add(new BarEntry(i, Float.parseFloat(dataEntities[i].getTotalResult())));
        }

        // create dataset using library and specify text size and colors
        BarDataSet barDataSet2 = new BarDataSet(emissions, "");
        barDataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet2.setValueTextColor(Color.WHITE);
        barDataSet2.setValueTextSize(16f);

        // fill the bardata with arraylist data
        BarData barData2 = new BarData(barDataSet2);
        // format the bar chart here
        barChart2.setFitBars(true);
        barChart2.setData(barData2);
        barChart2.getDescription().setText("Daily emissions");
        barChart2.animateY(2000);

        return root;
    }
}