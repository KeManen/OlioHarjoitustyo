package com.harkka.livegreen.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {

    private TextView textView;
    private Button login_button;
    private ShapeableImageView profilePicture;
    private ShapeableImageView ecorankPicture;
    private TextView profileName;
    private Button exportFiles_Button;
    private Button submitData_Button;
    private CardView card;
    private UserManager userManager;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextBMI;
    private EditText editTextAge;
    private EditText editTextLocation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initializing data field variables
        editTextHeight = root.findViewById(R.id.editTextHeight);
        editTextWeight = root.findViewById(R.id.editTextWeight);
        editTextBMI = root.findViewById(R.id.editTextBMI);
        editTextAge = root.findViewById(R.id.editTextAge);
        editTextLocation = root.findViewById(R.id.editTextLocation);

        // Logout handler
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
        profilePicture = root.findViewById(R.id.imageViewProfile);
        ecorankPicture = root.findViewById(R.id.imageViewEcorank);
        profileName = root.findViewById(R.id.textViewProfileName);
        userManager = UserManager.getInstance();

        //TODO enable userManager works correctly
        //handle_profileview_login_state(userManager.isAnyoneLogged());
        return root;
    }

    public void handle_profileview_loginbutton(){
        handle_profileview_login_state(userManager.isAnyoneLogged());
    }

    // Change profileview according to loginstate
    public void handle_profileview_login_state(boolean is_logged){
        //login_button = v.findViewById(R.id.buttonProfileViewLogout);
        //card = v.findViewById(R.id.profileCardView);
        Context context = getContext();


        if(userManager.isAnyoneLogged()){
            login_button.setText(getText(R.string.log_out));
            login_button.setBackgroundColor(login_button.getContext().getResources().getColor(R.color.red));

            card.setVisibility(View.VISIBLE);
            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            User user = userManager.getCurrentUser();
            switch(user.getRank()){
                case 1:
                    ecorankPicture.setImageResource(R.drawable.eco_1);
                    break;
                case 2:
                    ecorankPicture.setImageResource(R.drawable.eco_2);
                    break;
                case 3:
                    ecorankPicture.setImageResource(R.drawable.eco_3);
                    break;
                case 4:
                    ecorankPicture.setImageResource(R.drawable.eco_4);
                    break;
                case 5:
                    ecorankPicture.setImageResource(R.drawable.eco_5);
                    break;
                default:
                    ecorankPicture.setImageResource(R.drawable.eco_0);
                    break;
            }

            profileName.setText(user.userName);

            //TODO change to userpicture
            //profilePicture.setImageResource();

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

            ecorankPicture.setImageResource(R.drawable.eco_0);
            profilePicture.setImageResource(R.drawable.unlogged_profilepicture);


            profileName.setText("Profilename");
        }
    }

    //
    public void handle_profileview_datachanges(String s) {
        float height = 0f;
        float weight = 0f;

        System.out.println("In OnChanged handler...");

        if (!editTextHeight.getText().toString().isEmpty())
            height = Float.parseFloat(editTextHeight.getText().toString());
        if(!editTextWeight.getText().toString().isEmpty())
            weight = Float.parseFloat(editTextWeight.getText().toString());
        if (BMI(height, weight)){
            textView.setText("BMI");
        }
        else {
            textView.setText(s);
        }
    }

    // User and Data export to file when Export button is pushed
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

        // Todo: This should be moved into code where values are entered, calculate at the same time
        //BMI calculation
        Boolean calculated = BMI(value1, value0);

        // Sleep for 1second so user has time to read previous Toast message
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(context.getApplicationContext(), "Data submitted", Toast.LENGTH_SHORT).show();
    }

    public Boolean BMI(float height, float weight) {
        Boolean ret = false;
        float value = 0f;
        //BMI calculation
        if ( height > 0f && weight > 0f) {
            Calculable bmi = new BMI();
            value = bmi.calculateBMI(height, weight);
            editTextBMI.setText(Float.toString(value));
            System.out.println("Profile Fragment - BMI: " + value);
            ret = true;
        }
        return ret;
    }
}