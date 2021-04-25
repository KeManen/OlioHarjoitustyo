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

import java.util.concurrent.TimeUnit;

public class CreateUser2 extends AppCompatActivity {

    //database for users
    UserDao userDao;

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

        // Initialize new database object and insert info to the object as strings
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(username);
        userEntity.setEmail(email);
        userEntity.setPassword(password);
        userEntity.setFirstName(firstName.getText().toString());
        userEntity.setLastName(lastName.getText().toString());
        userEntity.setAge(age.getText().toString());
        userEntity.setLocation(location.getText().toString());

        // Insert userEntity to db
        new Thread(() -> userDao.registerUser(userEntity)).start();

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
