package com.harkka.livegreen.ui.dataview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.contentcapture.DataShareWriteAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import com.harkka.livegreen.entry.Entry;
import com.harkka.livegreen.entry.EntryManager;
import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.roomdb.LoginActivity;
import com.harkka.livegreen.roomdb.UserDao;
import com.harkka.livegreen.roomdb.UserDatabase;
import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.ui.home.HomeFragment;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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

        if(dataEntity.getTotalResult() != null) {
            // clear old values from arraylist
            emissions.clear();


            // TODO remove when works

        //System.out.println(dataEntity.getUserId() + " EntryId: " + dataEntity.getEntryId());
        Entry entry = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            entry = entryManager.createEntry(uGuid);
        }
        System.out.println(testString + "***" + entry.getWeight() + "****");
        entryManager.setEntry(entry);
        entry = entryManager.getEntry();

        System.out.println(testString + " " + entry.getWeight() + "************");

        //TODO: Here a test insert method void testInsertData(int type)
        //testInsertTestData(0, uGuid);
        testInsertTestData(1, uGuid);

        auxGuid = uGuid;

        new Thread(() -> {
            //userDatabase.userDao().loadUserEntityByUserId(String.valueOf(uGuid));
            String testString2 = "12321";
            System.out.println("In new Thread - Load entities ******************" + auxGuid.toString() + "******************");
            dataEntities = dataDao.loadAllDataEntitiesByUserId(auxGuid.toString());
            //dataEntity = dataDao.loadDataEntityByEntryId(auxGuid.toString());
            //System.out.println(testString2 + " " + dataEntity);
            //System.out.println(testString2 + dataEntity.getTotalResult() + dataEntity.getDateTime());
 //           if (!dataEntities.equals(null)) {
                System.out.println(testString2 + " " + dataEntities);
                System.out.println(testString2 + " " + dataEntities[0].getMeatUsed());
                System.out.println(testString2 + " " + dataEntities[0].getTotalResult() + " " + dataEntities[0].getDateTime());
 //           }
 //           else{
 //               System.out.println("DataEntities says: null!");
 //           }
        }).start();

            // Sleep for 1second so db thread has time to finalize load
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

         // TODO ends here


            for (int i = 0; i < 15; i++) {
                System.out.println(testString);
            }

            // test emissions for a week
            //TODO remove these when works

            //emissions.add(new BarEntry(1, Float.parseFloat(dataEntities[0].getDairyUsed())));
            //emissions.add(new BarEntry(2, Float.parseFloat(dataEntities[0].getMeatUsed())));
            //emissions.add(new BarEntry(3, Float.parseFloat(dataEntities[0].getVegeUsed())));
            emissions.add(new BarEntry(1, Float.parseFloat(dataEntity.getDairyUsed())));
            emissions.add(new BarEntry(2, Float.parseFloat(dataEntity.getMeatUsed())));
            emissions.add(new BarEntry(3, Float.parseFloat(dataEntity.getVegeUsed())));
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
                String totalFoodUsage;
                totalFoodUsage = DataEntity.getInstance().getVegeUsed() + DataEntity.getInstance().getMeatUsed() + DataEntity.getInstance().getDairyUsed();
                System.out.println("Total foodusage: " + totalFoodUsage);

                //     foodUsage.add(new BarEntry(totalFoodUsage , dateTime));
                System.out.println(testString2);
            }


            //TODO remove these when works
            //test CO2/kg per day for 2 weeks

            //foodUsage.add(new BarEntry(1, 5));
            foodUsage.add(new BarEntry(1, Float.parseFloat(dataEntities[0].getTotalResult())));
            foodUsage.add(new BarEntry(2, 9));
            foodUsage.add(new BarEntry(3, 5));
            foodUsage.add(new BarEntry(4, 4));
            foodUsage.add(new BarEntry(5, 5));
            foodUsage.add(new BarEntry(6, 9));
            foodUsage.add(new BarEntry(7, 5));
            foodUsage.add(new BarEntry(8, 4));
            foodUsage.add(new BarEntry(9, 3));
            foodUsage.add(new BarEntry(10, 4));
            foodUsage.add(new BarEntry(11, 3));
            foodUsage.add(new BarEntry(12, 4));
            foodUsage.add(new BarEntry(13, 5));
            foodUsage.add(new BarEntry(14, 9));


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

    private void testInsertTestData(int type, UUID uGuid) {
        UUID entryGuid;
        switch (type) {
            case 0:
                System.out.println("THIS IS TEST SECTION FOR USER INSERT");

                break;
            case 1:
                System.out.println("THIS IS TEST SECTION FOR ENTRY DATA INSERT");
                // TODO: THIS SECTION HANDLES DATA ENTRY IN DB --->

                Entry entry = entryManager.createEntry(uGuid);
                entryGuid = entry.getEntryGuid();
                entry.setUserGuid(uManager.getCurrentUserUUID());;
                entry.setEntryGuid(entryGuid);
                entry.setDateTime(LocalDateTime.now());
                entry.setWeight(Float.parseFloat("80"));
                entry.setHeight(Float.parseFloat("1.8"));
                entry.setDairyConsumption(Float.parseFloat("100"));
                entry.setMeatConsumption(Float.parseFloat("150"));
                entry.setVegeConsumption(Float.parseFloat("80"));
                entry.setTotalResult(Float.parseFloat("13"));
                entry.insertDBEntry();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("IN TEST ***************" + DataEntity.getInstance().getEntryId().toString() + "************");
                        dataDao.insertDataEntity(dataEntity);
                        System.out.println("IN TEST ***************" + dataEntity.getEntryId().toString() + "************");
                        System.out.println("IN TEST ***************" + dataEntity.getTotalResult() + "************");
                        System.out.println("IN TEST ***************" + dataEntity.getDateTime().toString() + "************");
                    }
                }).start();

                // TODO: <--- ENDS HERE
                break;
            default:
                break;
        }

    }

}