package com.harkka.livegreen.ui.testi;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.harkka.livegreen.MainActivity;
import com.harkka.livegreen.calculable.BMI;
import com.harkka.livegreen.calculable.Calculable;
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
import java.util.UUID;

public class TestFragment extends Fragment {
    //Todo: remove if not really needed
    enum EntryType {WEIGHT, HEIGHT, DAIRY, MEAT, VEGE, OTHER};


    // Variables for user management
    UserManager uManager = UserManager.getInstance(); // Singleton for User class usage

    // Todo: Entrymanager test code 1
    // Variables for entry management
    EntryManager entryManager = EntryManager.getInstance(); // Singleton for Entry class usage

    // TODO: THESE DEFINITIONS ARE NEEDED FOR DB INSERT AND LOAD --->
    // Variables for data entity management
    private UserDatabase userDatabase;
    private UserDao userDao;
    private DataDao dataDao;
    private DataEntity dataEntity = DataEntity.getInstance(); // Singleton for DataEntity class usage
    private UUID auxGuid;
    private DataEntity[] dataEntities;

    // TODO: <--- THESE DEFINITIONS ARE NEEDED FOR DB INSERT AND LOAD

    // Variables for test purposes Todo: Remove these when not needed anymore (jka)
    Button testButton;
    Button testButtonLoadDB;
    int testInt = 0;
    int testInt2 = 0;
    private String testString = "Test Fragment: ";

    private TestViewModel mViewModel;


    public static TestFragment newInstance() {
        return new TestFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.test_fragment, container, false);


        //Todo: THIS IS NEEDED FOR DB INSERT AND LOAD
        // Database and Dao initialization
        Context context = this.getContext();
        userDatabase = UserDatabase.getUserDatabase(context.getApplicationContext());
        userDao = userDatabase.userDao();
        dataDao = userDatabase.dataDao();
        //Todo: THIS IS NEEDED FOR DB INSERT AND LOAD

        Button testButton = (Button) root.findViewById(R.id.buttonTest);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root){
                pushTestButton(root);
            }
        });
        // Todo: remove after test use
        Button testButtonLoadDB = (Button) root.findViewById(R.id.buttonTestLoadDB);
        testButtonLoadDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View root){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    pushTestButtonLoadDB(root);
                }
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TestViewModel.class);
        // TODO: Use the ViewModel
    }

    // Todo: remove after test use
    public void pushTestButton(View v) {
        UUID uGuid = null;

        TextView textViewTest = v.findViewById(R.id.textViewTest);
        EditText editTextTest = v.findViewById(R.id.editTextTextMultiLineTest);

        testButton = v.findViewById(R.id.buttonTest);
        testInt++;
        if (testInt % 2 == 0) {
            testButton.setText("Test Button");
        } else
            testButton.setText("Clicked");


        uGuid = uManager.createUser(); // New user creation
        System.out.println(testString + ": " + uGuid);

        if (uManager.user.getUserIsLogged()) {
            // To be used for fetching existing user by guid
            //uManager.getUser(uGuid);

            //uManager.setUserProfile(uGuid, "TestFname", "testLname", testInt, "Stadi"); // Set user profile data by guid

            //uManager.createUserProfile(uGuid); // Empty

            //uManager.getUserProfile(uGuid);
 // // Todo: Entrymanager test code 2
            Entry entry = entryManager.createEntry(uGuid);

            String output = testString + entry.toString() + " " + entry.getDateTime();

            System.out.println(output);

            //System.out.println(editTextTest.getText().toString());
            //editTextTest.setText(output);

            float value = (float).34566;
            for (int i = 0; i < 5; i++)
                entryManager.setEntryValue(i, value);


            entryManager.setEntryValue(0, 1.8f);
            float value1 = entryManager.getEntryValue(0);
            System.out.println("Weight: " + value1);
            entryManager.setEntryValue(1, 80f);
            float value2 = entryManager.getEntryValue(1);
            System.out.println("Height: " + value2);

           Calculable bmi = new BMI();
           value = bmi.calculateBMI(value1, value2);
           System.out.println("BMI: " + value );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pushTestButtonLoadDB(View v) {
        UUID uGuid = null;

        TextView textViewTest = v.findViewById(R.id.textViewTest);
        EditText editTextTest = v.findViewById(R.id.editTextTextMultiLineTest);

        testButton = v.findViewById(R.id.buttonTestLoadDB);
        testInt2++;
        if (testInt2 % 2 == 0) {
            testButton.setText("Load DB");
        } else
            testButton.setText("LDB Clicked");


        uGuid = uManager.createUser(); // New user creation
        //uGuid = uManager.getCurrentUserUUID();
        System.out.println(testString + ": " + uGuid);

        if (uManager.user.getUserIsLogged()) {
            // To be used for fetching existing user by guid
            //uManager.getUser(uGuid);

            //uManager.setUserProfile(uGuid, "TestFname", "testLname", testInt, "Stadi"); // Set user profile data by guid

            //uManager.createUserProfile(uGuid); // Empty

            //uManager.getUserProfile(uGuid);
            // // Todo: Entrymanager test code db write + read

            // TODO: THIS SECTION HANDLES DATA ENTRY IN DB --->
            // TODO: NOTICE:

            Entry entry = entryManager.createEntry(uGuid);
            auxGuid = entry.getEntryGuid();

            entry.setWeight(Float.parseFloat("80"));
            entry.setHeight(Float.parseFloat("80"));
            entry.setDairyConsumption(Float.parseFloat("80"));
            entry.setMeatConsumption(Float.parseFloat("80"));
            entry.setVegeConsumption(Float.parseFloat("80"));
            entry.setTotalResult(Float.parseFloat("80"));
            entry.insertDBEntry();

        new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(testString + "IN ***************" + dataEntity.getEntryId().toString() + "************");
                    dataDao.insertDataEntity(dataEntity);
                }
            }).start();

            // TODO: <--- ENDS HERE

            // TODO: THIS SECTION HANDLES DATA LOAD FROM DB --->

            new Thread(new Runnable() {
                @Override
                public void run() {
                    DataEntity dataEntity = DataEntity.getInstance();
                    System.out.println("******************" + auxGuid.toString() + "******************");
                    dataEntity = dataDao.loadDataEntityByEntryId(auxGuid.toString());
                    System.out.println(testString + " " + dataEntity);
                    System.out.println(testString + " Height: " + dataEntity.getHeight());
                   // System.out.println(testString + dataEntity.getUserId() + " EntryId: " + dataEntity.getEntryId());
                }
            }).start();


            // TODO: <--- ENDS HERE
            System.out.println(testString + "***" + entry.getWeight() + "****");
            entryManager.setEntry(entry);
            entry = entryManager.getEntry();

            System.out.println(testString + " " + entry.getWeight() + "************");

            /*

            System.out.println("********************* Insert *********************");

            float value = (float).34566;
            for (int i = 0; i < 5; i++) {
                entryManager.setEntryValue(i, value);
                System.out.println(testString + " Insert: GUID: " + uGuid + ", Type: " + i + ", Value: " + value );
                value = value+i;
            }

            System.out.println("********************* Load *********************");

            for (int i = 0; i < 5; i++) {
                value = entryManager.getEntryValue(i);
                System.out.println(testString + " Select: GUID: " + uGuid + ", Type: " + i + ", Value: " + value );
            }
*/
/*
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserEntity userEntity = userDao.login(userIdText, passwordText);

                    if (userEntity == null) {

                        // In case of user not found:
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("Invalid credentials");
                                Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        });

                        return;
                    }
                    System.out.println("login successful");
                    // move to mainactivity fragment here and send username
                    String name = userEntity.userId;
                    //Toast.makeText(getApplicationContext(), "Welcome "+name+"!", Toast.LENGTH_SHORT).show(); crashes
                    startActivity(new Intent(getApplicationContext(), MainActivity.class).putExtra("name", name));

                }
            }).start();
            */
        }
    }
}