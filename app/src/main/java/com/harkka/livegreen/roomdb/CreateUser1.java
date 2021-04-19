package com.harkka.livegreen.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harkka.livegreen.R;
import com.harkka.livegreen.user.UserManager;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser1 extends AppCompatActivity {

    // Variables for user management
    UserManager uManager = UserManager.getInstance(); // Singleton for User class usage

    // Integrate components
    EditText userName, password, email, password2;
    Button register, toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user1);

        // integrate components
        register = findViewById(R.id.register);
        toLogin = findViewById(R.id.toLogin);
        userName = findViewById(R.id.userId);
        email = findViewById(R.id.email);
        password2 = findViewById(R.id.password2);
        password = findViewById(R.id.password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check whether the passwords match
                if (password.getText().toString().equals(password2.getText().toString())) {

                    // check for empty inputs
                    if (validateInput()) {

                        //switch to activity 2
                        startActivity(new Intent(CreateUser1.this, CreateUser2.class)
                                .putExtra("name", userName.getText().toString())
                                .putExtra("email", email.getText().toString())
                                .putExtra("password", password.getText().toString()));

                    } else {
                        Toast.makeText(getApplicationContext(), "Fill needed information", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // move back to the previous Login screen
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateUser1.this, LoginActivity.class));
            }
        });
    }

    // check given information --> is the input empty?
    private Boolean validateInput() {
        System.out.println("name: " + userName.getText().toString());
        System.out.println("email: " + email.getText().toString());
        System.out.println("pass1: " + password.getText().toString());
        System.out.println("pass2: " + password2.getText().toString());
        // add the needed components with -->  || userEntity.get_____().isEmpty())
        //Check if input is empty
        if (userName.toString().isEmpty() || password.toString().isEmpty() ||  password2.toString().isEmpty() || email.toString().isEmpty()) {
            return false;
        }
        //check if email is a valid syntax
        Pattern pattern = Pattern.compile("^(\\w|\\.|\\_|\\-)+[@](\\w|\\_|\\-|\\.)+[.]\\w{2,3}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email.getText().toString());
        if(!matcher.find()){
            return false;
        }
        //check if password is valid syntax
        //TODO add password reqs
        if(false){
            return false;
        }
        return true;
    }

}
