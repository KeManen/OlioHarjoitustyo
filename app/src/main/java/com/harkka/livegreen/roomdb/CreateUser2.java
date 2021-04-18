package com.harkka.livegreen.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harkka.livegreen.MainActivity;
import com.harkka.livegreen.R;
import com.harkka.livegreen.ui.home.HomeViewModel;

import java.util.concurrent.TimeUnit;

public class CreateUser2 extends AppCompatActivity {

    // Integrate components
    EditText firstName, lastName, age, location;
    Button create, goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user2);

        // Integrate components
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        age = findViewById(R.id.age);
        location = findViewById(R.id.location);
        create = findViewById(R.id.create);
        goBack = findViewById(R.id.goBack);

        // userId comes from first CreateUser screen
        // used to store both credentials to same ID/username
        String userName = getIntent().getStringExtra("key");
        System.out.println("On the other side:   " + userName);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialize new database object and insert info to the object as strings
                UserEntity userEntity = new UserEntity();
                userEntity.setUserName(userName);
                userEntity.setFirstName(firstName.getText().toString());
                userEntity.setLastName(lastName.getText().toString());
                userEntity.setAge(age.getText().toString());
                userEntity.setLocation(location.getText().toString());

                // check whether given information is given correctly
                if (validateInput(userEntity)) {

                    // Insert additional information to an existing object
                    // perform Query command at UserDao
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // Add user to the database
                            userDao.registerUser(userEntity);

                            // check whether the given credentials exist in the database
                            if (userEntity == null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // Tell the user account was created successfully
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                // Sleep for 1second so user has time to read previous Toast message
                                try {
                                    TimeUnit.SECONDS.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                // User is created. Move to Login fragment here and close current fragment
                                startActivity(new Intent(CreateUser2.this, MainActivity.class)); // Was LoginActivity
                            }
                        }
                    }).start();

                } else {
                    Toast.makeText(getApplicationContext(), "Fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // move back to the previous screen
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateUser2.this, CreateUser1.class));
            }
        });

    }

/*
    // check given information --> is the input empty?
    private Boolean validateInput(UserEntity userEntity) {

        // add the needed components with -->  || userEntity.get_____().isEmpty())
        if (userEntity.getUserName().isEmpty() ||userEntity.getFirstName().isEmpty() || userEntity.getLastName()
                .isEmpty() || userEntity.getAge().isEmpty() || userEntity.getLocation().isEmpty()) {
            return false;
        }
        return true;
    }
  */
    // For testing

    private Boolean validateInput(UserEntity userEntity) {
        String userName = getIntent().getStringExtra("key");
        firstName = findViewById(R.id.firstName);
        System.out.println(firstName.getText());
        lastName = findViewById(R.id.lastName);
        System.out.println(lastName.getText());
        age = findViewById(R.id.age);
        System.out.println(age.getText());
        location = findViewById(R.id.location);
        System.out.println(location.getText());
        // add the needed components with -->  || userEntity.get_____().isEmpty())
        if (userName.toString().isEmpty() ||firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || age.getText().toString().isEmpty() || location.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }




}
