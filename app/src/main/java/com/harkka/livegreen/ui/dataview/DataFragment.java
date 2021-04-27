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

    // TODO remove when works
    // Variables for user management
    UserManager uManager = UserManager.getInstance(getContext()); // Singleton for User class usage
    // Variables for entry management
    EntryManager entryManager = EntryManager.getInstance(); // Singleton for Entry class usage
    UserDatabase userDatabase;
    UserDao userDao;
    DataDao dataDao;
    UserEntity userEntity = UserEntity.getInstance();
    DataEntity dataEntity = DataEntity.getInstance();
    UUID auxGuid;
    UUID entryGuid;
    DataEntity[] dataEntities;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_data, container, false);

        // initialize components
        BarChart barChart = root.findViewById(R.id.barChart);
        ArrayList<BarEntry> emissions = new ArrayList<>();
        Context context = this.getContext();

        //Todo for test use
        userDatabase = UserDatabase.getUserDatabase(context.getApplicationContext());
        userDao = userDatabase.userDao();
        dataDao = userDatabase.dataDao();
        String testString = "123 ";
        UUID uGuid = null;
        //uGuid = uManager.createUser().getUserId(); // New user creation
        uGuid = uManager.getCurrentUserUUID();
        System.out.println(testString + ": " + uGuid);


        // get date as number of the month
        Calendar c = Calendar.getInstance();
        int wantedDate = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("Wanted date: " + wantedDate);

        // TODO remove when works

        //System.out.println(dataEntity.getUserId() + " EntryId: " + dataEntity.getEntryId());
        Entry entry = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            entry = entryManager.createEntry(uGuid);
        }
        entryManager.setEntry(entry);
        entry = entryManager.getEntry();
        auxGuid = uGuid;

        new Thread(() -> {
            //userDatabase.userDao().loadUserEntityByUserId(String.valueOf(uGuid));
            String testString2 = "12321";
            System.out.println("In new Thread - Load entities ******************" + auxGuid.toString() + "******************");
            dataEntities = dataDao.loadAllDataEntitiesByUserId(auxGuid.toString());
        }).start();

        // Sleep for 1second so db thread has time to finalize load
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (dataEntities[0].getTotalResult() != null) {
            // clear old values from arraylist
            emissions.clear();

            // TODO ends here

            float totalGrams = 0f;
            totalGrams = Float.parseFloat(dataEntities[0].getDairyUsed()) + Float.parseFloat(dataEntities[0].getMeatUsed()) + Float.parseFloat(dataEntities[0].getVegeUsed());
            System.out.println(Float.parseFloat(dataEntities[0].getDairyUsed()) + " --------------DAIRY-------------");
            System.out.println(Float.parseFloat(dataEntities[0].getMeatUsed()) + " --------------MEAT-------------");
            System.out.println(Float.parseFloat(dataEntities[0].getVegeUsed()) + " -------------VEGE--------------");

            System.out.println("################# " + totalGrams + " #################");

            float totalGrams2 = 0f;
            totalGrams2 = Float.parseFloat(dataEntities[5].getDairyUsed()) + Float.parseFloat(dataEntities[5].getMeatUsed()) + Float.parseFloat(dataEntities[5].getVegeUsed());
            System.out.println("################# " + totalGrams2 + " #################");


            for (int i = 0; i < 15; i++) {

                float totalGrams3 = Float.parseFloat(dataEntities[i].getDairyUsed()) + Float.parseFloat(dataEntities[i].getMeatUsed()) + Float.parseFloat(dataEntities[i].getVegeUsed());
                System.out.println("Total FOOD USED IN GRAMS: " + totalGrams3);
                emissions.add(new BarEntry(i, totalGrams3));

            }

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
            //////////////////     Draw second chart       //////////////////
            /////////////////////////////////////////////////////////////////

            // initialize components
            BarChart barChart2 = root.findViewById(R.id.barChart2);
            ArrayList<BarEntry> foodUsage = new ArrayList<>();

            // get date as number of the month
            Calendar c2 = Calendar.getInstance();
            int wantedDate2 = c2.get(Calendar.DAY_OF_MONTH);
            System.out.println("Wanted date 2: " + wantedDate2);

            //TODO add data inserts from database
            //TODO format is day as integer, food usage as grams

            String testString2 = "321 ";
            // clear old values from arraylist
            foodUsage.clear();
            for (int i = 0; i < 15; i++) {


                System.out.println("Total DAIRY: " + i + "  " + Float.parseFloat(dataEntities[i].getDairyUsed()) + " --------------DAIRY-------------");
                System.out.println("Total MEAT: " + i + "  " + Float.parseFloat(dataEntities[i].getMeatUsed()) + " --------------MEAT-------------");
                System.out.println("Total VEGE: " + i + "  " + Float.parseFloat(dataEntities[i].getVegeUsed()) + " -------------VEGE--------------");

                float totalGrams3 = Float.parseFloat(dataEntities[i].getDairyUsed()) + Float.parseFloat(dataEntities[i].getMeatUsed()) + Float.parseFloat(dataEntities[i].getVegeUsed());
                System.out.println("Total FOOD USED IN GRAMS: " + totalGrams3);

                foodUsage.add(new BarEntry(i, Float.parseFloat(dataEntities[i].getTotalResult())));
            }

            System.out.println("START OF FOODUSAGE ARRAYLIS   -   foodUsage");
            System.out.println(foodUsage);
            System.out.println("END OF FOODUSAGE ARRAYLIS   -   foodUsage");

            //TODO remove these when works

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
        } else {
            Toast.makeText(getContext(), "Insert data first", Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    // Test methods


}