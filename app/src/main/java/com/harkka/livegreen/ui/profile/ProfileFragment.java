package com.harkka.livegreen.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.harkka.livegreen.MainActivity;
import com.harkka.livegreen.R;
import com.harkka.livegreen.calculable.BMI;
import com.harkka.livegreen.calculable.Calculable;
import com.harkka.livegreen.entry.Entry;
import com.harkka.livegreen.entry.EntryManager;
import com.harkka.livegreen.roomdb.LoginActivity;
import com.harkka.livegreen.user.UserManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private Button login_button;
    private Button exportFiles_Button;
    private Button submitData_Button;
    private CardView card;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextBMI;
    private EditText editTextAge;
    private EditText editTextLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);

        // Initializing data field variables
        editTextHeight = root.findViewById(R.id.editTextHeight);
        editTextWeight = root.findViewById(R.id.editTextWeight);
        editTextBMI = root.findViewById(R.id.editTextBMI);
        editTextAge = root.findViewById(R.id.editTextAge);
        editTextLocation = root.findViewById(R.id.editTextLocation);

        login_button = root.findViewById(R.id.buttonProfileViewLogout);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { handle_profileview_loginbutton(); }
        });

        // Export data files
        exportFiles_Button = root.findViewById(R.id.writeFiles);
        exportFiles_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { exportFiles(); }
        });

        // Submit data
        submitData_Button = root.findViewById(R.id.submitDataButton);
        submitData_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { submitData(); }
        });

        card = root.findViewById(R.id.profileCardView);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) { textView.setText(s); }
        });
        return root;
    }

    public void handle_profileview_loginbutton(){
        // TODO toggle current login position
        handle_profileview_login_state(false);
    }

    // Change profileview according to loginstate
    public void handle_profileview_login_state(boolean is_logged){
        //login_button = v.findViewById(R.id.buttonProfileViewLogout);
        //card = v.findViewById(R.id.profileCardView);
        Context context = getContext();
        UserManager um = UserManager.getInstance();

        if(um.isAnyoneLogged()){
            login_button.setText(getText(R.string.log_out));
            login_button.setBackgroundColor(login_button.getContext().getResources().getColor(R.color.red));

            card.setVisibility(View.VISIBLE);
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //TODO change rank, profile picture and profile name with stockvalues

        } else {
            login_button.setText(getText(R.string.log_in));
            login_button.setBackgroundColor(login_button.getContext().getResources().getColor(R.color.green_dark));

            card.setVisibility(View.GONE);
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(context, LoginActivity.class));
                }
            });

            //TODO change rank, profile picture and profile name with stockvalues
        }
    }

    public void exportFiles() {
        System.out.println("Toistaiseksi ok...");
        String userFile = "UserLog.txt";
        String dataFile = "DataLog.txt";

        Context context = getContext();

        // Write userdata to log file
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(userFile, Context.MODE_PRIVATE));

            //TODO add what to write into log file here
            System.out.println("Userfile write ok...");


            osw.close();
        } catch (IOException e) {
            Log.e("IOException", "Error in write");
        } finally {
            Toast.makeText(context.getApplicationContext(), "First file ready", Toast.LENGTH_SHORT).show();
        }


        // Write inputdata to log file
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(dataFile, Context.MODE_PRIVATE));

            //TODO add what to write into log file here
            System.out.println("Datafile write ok...");

            osw.close();
        } catch (IOException e) {
            Log.e("IOException", "Error in write");
        } finally {
            // Sleep for 1second so user has time to read previous Toast message
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(context.getApplicationContext(), "Files ready", Toast.LENGTH_SHORT).show();
        }
    }

    public void submitData() {
        Context context = getContext();
        UserManager um = UserManager.getInstance();
        EntryManager em = EntryManager.getInstance();

        // Todo: This is for prototyping, user is not initialized so do it here. Initialization missing in app start! TO BE FIXED AND REMOVED
        UUID uGuid = um.createUser();
        //UUID uGuid = um.getCurrentUserUUID();

        Entry entry = em.createEntry(uGuid);
        float value = 0;
        float value0 = 0;
        float value1 = 0;

        //TODO Enter data into user profile instance TO BE CHECKED, now age and location in USer Profile entity!!!
        if (!editTextAge.getText().toString().isEmpty()) {
            value = Float.parseFloat(editTextAge.getText().toString());

            System.out.println("Data submit Age ok: " + editTextAge.getText().toString());
        }

        if (!editTextLocation.getText().toString().isEmpty()) {
            value = Float.parseFloat(editTextLocation.getText().toString());

            System.out.println("Data submit Age ok: " + editTextLocation.getText().toString());
        }

        //TODO Enter data into entry instance
        if (!editTextWeight.getText().toString().isEmpty()) {
            value0 = Float.parseFloat(editTextWeight.getText().toString());
            em.setEntryValue(0, value0);

            System.out.println("Data submit Weight ok...");
        }

        if (!editTextHeight.getText().toString().isEmpty()) {
            value1 = Float.parseFloat(editTextHeight.getText().toString());
            em.setEntryValue(1, value1);

            System.out.println("Data submit Height ok...");
        }

        if ( value0 > 0f && value1 > 0f) {
            Calculable bmi = new BMI();
            value = bmi.calculateBMI(value1, value0);
            editTextBMI.setText(Float.toString(value));
            System.out.println("Profile Fragment - BMI: " + value);
        }

        // Sleep for 1second so user has time to read previous Toast message
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(context.getApplicationContext(), "Data submitted", Toast.LENGTH_SHORT).show();
    }
}