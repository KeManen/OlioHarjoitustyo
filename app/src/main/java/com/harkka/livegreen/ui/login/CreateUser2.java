package com.harkka.livegreen.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harkka.livegreen.MainActivity;
import com.harkka.livegreen.R;
import com.harkka.livegreen.iohandler.IOHandler;
import com.harkka.livegreen.logic.Scrambler;
import com.harkka.livegreen.roomdb.UserDao;
import com.harkka.livegreen.roomdb.UserDatabase;
import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserProfile;
import com.harkka.livegreen.user.UserManager;

import java.util.concurrent.TimeUnit;

public class CreateUser2 extends AppCompatActivity {

    //database for users
    UserDao userDao;
    UserManager userManager;

    // Integrate components
    EditText firstName, lastName, age, location;
    Button create, goBack;

    //earlier inputs
    String username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user2);
        userDao = UserDatabase.getUserDatabase(getApplicationContext()).userDao();
        userManager = UserManager.getInstance(getApplicationContext());

        // Integrate components
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        location = findViewById(R.id.location);
        create = findViewById(R.id.create);
        goBack = findViewById(R.id.goBack);

        //get earlier inputs
        this.username = getIntent().getStringExtra("name");
        this.email = getIntent().getStringExtra("email");
        this.password = getIntent().getStringExtra("password");

        //check input, insert data and transition to main activity
        create.setOnClickListener(this::onClick);

        // move back to the previous screen
        goBack.setOnClickListener(v -> startActivity(new Intent(CreateUser2.this, CreateUser1.class)));

    }
    //TODO add other checks?

    // validateInput
    // returns false if any input field is empty
    private Boolean validateInput() {
        //debugs
        System.out.println(firstName.getText());
        System.out.println(lastName.getText());
        System.out.println(age.getText());
        System.out.println(location.getText());

        //checks if any fields are empty
        return !firstName.getText().toString().trim().isEmpty() &&
                !lastName.getText().toString().trim().isEmpty() &&
                     !age.getText().toString().trim().isEmpty() &&
                !location.getText().toString().trim().isEmpty();
    }

    // onClick
    // gets earlier and current inputs validates them and puts them in the userDB
    // finally changes view to main Activity
    private void onClick(View v) {
        // validate inputs
        if (!validateInput()) {
            System.out.println("Fill all fields");
            Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String SFirstName = firstName.getText().toString().trim();
        String SLastName = lastName.getText().toString().trim();
        int iAge = Integer.parseInt(age.getText().toString().trim());
        String SLocation = location.getText().toString().trim();

        // Create new user and profile and insert data
        User user = userManager.createUser();
        user.setUserName(username);
        user.setUserEmail(email);
        user.setUserPasswd(Scrambler.scrambledPassword(password, username));

        UserProfile userProfile = userManager.createUserProfile(user.getUserId());
        userProfile.setUserProfile(user.getUserId(), SFirstName, SLastName, iAge, SLocation);

        // insert userdata to userEntity
        user.insertDBUser();
        userProfile.insertDBUserProfile();

        // Insert userEntity to db
        new Thread(() -> {
            UserEntity userEntity = UserEntity.getInstance();
            System.out.println("#######################################");
            System.out.println("CreateUser2/userEntity/userid: "+userEntity.getUserId());
            System.out.println("CreateUser2/userEntity/username: "+userEntity.getUserName());
            System.out.println("CreateUser2/userEntity/email: "+userEntity.getEmail());
            System.out.println("CreateUser2/userEntity/password: "+userEntity.getPassword());
            System.out.println("CreateUser2/userEntity/firstname: "+userEntity.getFirstName());
            System.out.println("CreateUser2/userEntity/lastname: "+userEntity.getLastName());
            System.out.println("CreateUser2/userEntity/age: "+userEntity.getAge());
            System.out.println("CreateUser2/userEntity/location: "+userEntity.getLocation());

            userDao.insertUserEntity(userEntity);
            /*
            UserEntity getter = userDao.loadUserEntityByUserId(userEntity.getUserId().toString());
            System.out.println("#######################################");
            System.out.println("CreateUser2/getter/userid: "+getter.getUserId());
            System.out.println("CreateUser2/getter/username: "+getter.getUserName());
            System.out.println("CreateUser2/getter/email: "+getter.getEmail());
            System.out.println("CreateUser2/getter/password: "+getter.getPassword());
            System.out.println("CreateUser2/getter/firstname: "+getter.getFirstName());
            System.out.println("CreateUser2/getter/lastname: "+getter.getLastName());
            System.out.println("CreateUser2/getter/age: "+getter.getAge());
            System.out.println("CreateUser2/getter/location: "+getter.getLocation());

            UserEntity ueFromDB = userDao.login(userEntity.getUserName(), userEntity.getPassword());
            System.out.println("#######################################");
            System.out.println("CreateUser2/fromDB/userid: "+ueFromDB.getUserId());
            System.out.println("CreateUser2/fromDB/username: "+ueFromDB.getUserName());
            System.out.println("CreateUser2/fromDB/email: "+ueFromDB.getEmail());
            System.out.println("CreateUser2/fromDB/password: "+ueFromDB.getPassword());
            System.out.println("CreateUser2/fromDB/firstname: "+ueFromDB.getFirstName());
            System.out.println("CreateUser2/fromDB/lastname: "+ueFromDB.getLastName());
            System.out.println("CreateUser2/fromDB/age: "+ueFromDB.getAge());
            System.out.println("CreateUser2/fromDB/location: "+ueFromDB.getLocation());
            */
        }).start();

        // Write user login into user log
        // Wake IOHandler
        System.out.println("Create User IOHandler start");
        IOHandler ioHandler = IOHandler.getInstance();
        int create = 0;
        ioHandler.doFileAction(getApplicationContext(), user.getUserId(), user.getUserName(), create);

        // Tell the user account was created successfully
        Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();

        // Sleep for 1second so user has time to read previous Toast message
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // User is created. Move to Login fragment here and close current fragment
        startActivity(new Intent(CreateUser2.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }
}
