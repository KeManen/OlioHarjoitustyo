package com.harkka.livegreen.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.harkka.livegreen.R;
import com.harkka.livegreen.calculable.BMI;
import com.harkka.livegreen.calculable.Calculable;
import com.harkka.livegreen.entry.Entry;
import com.harkka.livegreen.entry.EntryManager;
import com.harkka.livegreen.iohandler.IOHandler;
import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.ui.login.LoginActivity;
import com.harkka.livegreen.roomdb.UserDao;
import com.harkka.livegreen.roomdb.UserDatabase;
import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserManager;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import com.google.android.material.imageview.ShapeableImageView;
import com.harkka.livegreen.user.UserProfile;

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
    // Variables for data entity management
    // Variables for user management
    private UserManager uManager = UserManager.getInstance(getContext()); // Singleton for User class usage
    // Variables for entry management
    private EntryManager entryManager = EntryManager.getInstance(); // Singleton for Entry class usage
    private UserDatabase userDatabase;
    private UserDao userDao;
    private DataDao dataDao;
    private UUID auxGuid;
    private DataEntity[] dataEntities;

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
        login_button.setOnClickListener(v -> handle_profileview_login_state());

        // Export data files
        exportFiles_Button = root.findViewById(R.id.writeFiles);
        exportFiles_Button.setOnClickListener(v -> exportFiles());

        // Submit data
        submitData_Button = root.findViewById(R.id.submitDataButton);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            submitData_Button.setOnClickListener(v -> submitData());
        }

        //components that change with loginstate
        card = root.findViewById(R.id.profileCardView);
        profilePicture = root.findViewById(R.id.imageViewProfile);
        ecorankPicture = root.findViewById(R.id.imageViewEcorank);
        profileName = root.findViewById(R.id.textViewProfileName);

        userManager = UserManager.getInstance(getContext());

        // Database and Dao initialization
        Context context = this.getContext();
        userDatabase = UserDatabase.getUserDatabase(context.getApplicationContext());
        userDao = userDatabase.userDao();
        dataDao = userDatabase.dataDao();
        UUID uGuid = null;
        uGuid = userManager.getCurrentUserUUID();

        // Initialize entry
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

        handle_profileview_login_state();
        return root;
    }

    // void handle_profileview_login_state()
    // handles profileview based on login state
    public void handle_profileview_login_state(){
        Context context = getContext();

        //checks if the user is logged
        if(userManager.getCurrentUser() != null){

            System.out.println("****************"+ userManager.getCurrentUser().getUserId() + "**************");
            System.out.println("****************"+ userManager.getCurrentUserUUID() + "**************");

            //Change button to user logged in state
            login_button.setText(getText(R.string.log_out));
            login_button.setBackgroundColor(login_button.getContext().getResources().getColor(R.color.red));
            login_button.setOnClickListener(v -> {

                // Write user logout into user log
                // Wake IOHandler
                System.out.println("Logout IOHandler start");
                IOHandler ioHandler = IOHandler.getInstance();
                int logout = 2;
                System.out.println("****************"+ userManager.getCurrentUserUUID() + "**************");
                ioHandler.doFileAction(context, userManager.getCurrentUserUUID(), userManager.getCurrentUser().getUserName(), logout);

                userManager.noCurrentUser();
                handle_profileview_login_state();
            });

            //show profileinput card
            card.setVisibility(View.VISIBLE);

            //get users rank and assigning correct rank icon
            User user = userManager.getCurrentUser();
            switch(user.getRank()){
                case 1: { ecorankPicture.setImageResource(R.drawable.eco_1); break; }
                case 2: { ecorankPicture.setImageResource(R.drawable.eco_2); break; }
                case 3: { ecorankPicture.setImageResource(R.drawable.eco_3); break; }
                case 4: { ecorankPicture.setImageResource(R.drawable.eco_4); break; }
                case 5: { ecorankPicture.setImageResource(R.drawable.eco_5); break; }
                default:{ ecorankPicture.setImageResource(R.drawable.eco_0); break; }
            }

            //set username
            profileName.setText(user.getUserName());

            //TODO set userpicture
            //profilePicture.setImageResource();

        } else {
            //change loginbutton to user logged out state
            login_button.setText(getText(R.string.log_in));
            login_button.setBackgroundColor(login_button.getContext().getResources().getColor(R.color.green_dark));
            login_button.setOnClickListener(v -> startActivity(new Intent(context, LoginActivity.class)));

            //hide profileinput card
            card.setVisibility(View.GONE);

            // set rank, name and profilepicture to defaults
            ecorankPicture.setImageResource(R.drawable.eco_0);
            profilePicture.setImageResource(R.drawable.unlogged_profilepicture);
            profileName.setText(R.string.profilename);

        }
    }

    // Update BMI when Height and Weight sunbmitted
    public void handle_profileview_datachanges(String s) {
        float height = 0f;
        float weight = 0f;

        System.out.println("In OnChanged handler...");

        if (!editTextHeight.getText().toString().isEmpty())
            height = Float.parseFloat(editTextHeight.getText().toString());
        if(!editTextWeight.getText().toString().isEmpty())
            weight = Float.parseFloat(editTextWeight.getText().toString());
        if (calculateBMI(height, weight)){
            textView.setText(R.string.bmi);
        }
        else {
            textView.setText(s);
        }
    }

    // User and Data export to file when Export button is pushed
    public void exportFiles() {
        String dataFile = "DataLog.txt";

        Context context = getContext();
        // Write inputdata to log file
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(dataFile, Context.MODE_PRIVATE));

            int fileNum = 0;
            for (int i = 0; i < dataEntities.length; i++) {

                osw.write("Input"+ fileNum + "DAIRY: " + Float.parseFloat(dataEntities[i].getDairyUsed()) + "g\n");
                osw.write("Input " + fileNum + " MEAT: " + Float.parseFloat(dataEntities[i].getMeatUsed()) + "g\n");
                osw.write("Input " + fileNum + " VEGE: " + Float.parseFloat(dataEntities[i].getVegeUsed()) + "g\n");
                float totalGrams3 = Float.parseFloat(dataEntities[i].getDairyUsed()) + Float.parseFloat(dataEntities[i]
                        .getMeatUsed()) + Float.parseFloat(dataEntities[i].getVegeUsed());
                osw.write("Input " + fileNum + " Total food usage in grams: " + totalGrams3 + "\n");
                osw.write("Input " + fileNum + " Generated " + Float.parseFloat(dataEntities[i].getTotalResult()) + "kg's of CO2 \n");
                osw.write("\n");
                fileNum ++;
                System.out.println("DATA EXPORT FILE " + fileNum + ". DONE ");
            }

            System.out.println("Datafile write ok...");
            osw.close();
        } catch (IOException e) {
            Log.e("IOException", "Error in write");
        } finally {
            // Wait for files to write
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Toast.makeText(context.getApplicationContext(), "Files ready", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitData() {
        Context context = getContext();
        UserManager um = UserManager.getInstance(getContext());
        EntryManager em = EntryManager.getInstance();
        DataEntity dataEntity = DataEntity.getInstance();
        UserEntity userEntity = UserEntity.getInstance();

        // Todo: This is for prototyping, user is not initialized so do it here. Initialization missing in app start! TO BE FIXED AND REMOVED
        //UUID uGuid = um.createUser().getUserId();
        User user = um.getCurrentUser();
        UUID uGuid = um.getCurrentUserUUID();
        UserProfile userProfile = um.createUserProfile(uGuid);

        // New entry object for data transfer and insert to DB
        Entry entry = em.createEntry(uGuid);
        int age = 0;
        float value0 = 0;
        float value1 = 0;
        String textValue = null;

        // Reading the values and transferring them to the objects
        if (!editTextAge.getText().toString().isEmpty()) {
            age = Integer.parseInt(editTextAge.getText().toString());
            userProfile.setUserProfileAge(age);
            System.out.println("Data submit Age ok: " + editTextAge.getText().toString());
        }

        if (!editTextLocation.getText().toString().isEmpty()) {
            textValue = editTextLocation.getText().toString();
            userProfile.setUserProfileLocation(textValue);
            System.out.println("Data submit Location ok: " + editTextLocation.getText().toString());
        }

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

        //BMI calculation
        Boolean calculated = calculateBMI(value1, value0);

        // Inserts into database

        // Entry data insert
        entry.insertDBEntry(); // Prepare Entry object for db insert, copy data to DataEntity
        new Thread(() -> {
            System.out.println("IN DB Entry***************" + dataEntity.getEntryId().toString() + "************");
            dataDao.insertDataEntity(dataEntity); // Do the thing
        }).start();

        // User data insert
        user.insertDBUser(); // Prepare User object for db insert, copy data to DataEntity
        userProfile.insertDBUserProfile(); // Prepare UserProfile object for db insert, copy data to DataEntity
        new Thread(() -> {
            System.out.println("IN DB User ***************" + dataEntity.getUserId().toString() + "************");
            // User and UserProfile are both included in UserEntity
            userDao.insertUserEntity(userEntity);
        }).start();

        // Sleep for 1second so user has time to read previous Toast message
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Toast.makeText(context.getApplicationContext(), "Data submitted", Toast.LENGTH_SHORT).show();
    }

    public Boolean calculateBMI(float height, float weight) {
        Boolean ret = false;
        float value = 0f;
        //BMI calculation
        if ( height > 0f && weight > 0f) {
            //Calculable bmi = new BMI();
            BMI bmi = new BMI();
            value = bmi.calculateBMI(height, weight);
            editTextBMI.setText(Float.toString(value));
            System.out.println("Profile Fragment - BMI: " + value);
            ret = true;
        }
        return ret;
    }
}