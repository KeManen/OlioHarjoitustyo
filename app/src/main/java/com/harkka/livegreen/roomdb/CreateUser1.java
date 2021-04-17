package com.harkka.livegreen.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harkka.livegreen.R;

public class CreateUser1 extends AppCompatActivity {

    // Integrate components
    EditText userId, password, email, password2;
    Button register, toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user1);

        // integrate components
        register = findViewById(R.id.register);
        toLogin = findViewById(R.id.toLogin);
        userId = findViewById(R.id.userId);
        email = findViewById(R.id.email);
        password2 = findViewById(R.id.password2);
        password = findViewById(R.id.password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Check whether the passwords match
                String passwordCheck = password.getText().toString();
                String passwordCheck2 = password2.getText().toString();
                // remove from .xml --> android:inputType="textPassword" to remove hidden password

                if (passwordCheck.equals(passwordCheck2)) {

                    // Initialize the user Entity
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUserId(userId.getText().toString());
                    userEntity.setPassword(password.getText().toString());
                    userEntity.setEmail(email.getText().toString());

                    // check for empty inputs
                    if (validateInput(userEntity)) {
                        //Insert userEntity to database
                        UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                        UserDao userDao = userDatabase.userDao();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // Register the new user to the database
                                userDao.registerUser(userEntity);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Half way done", Toast.LENGTH_SHORT).show();

                                        // Close first CreateUser screen and move to CreateUser2. Send userId to the 2.fragment
                                        startActivity(new Intent(CreateUser1.this, CreateUser2.class)
                                                .putExtra("key", userId.getText().toString()));
                                  //      System.out.println("Kayttajanimi:   "+ userId.getText().toString());
                                    }
                                });
                            }
                        }).start();

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
    private Boolean validateInput(UserEntity userEntity) {

        // add the needed components with -->  || userEntity.get_____().isEmpty())
        if (userEntity.getUserId().isEmpty() || userEntity.getPassword().isEmpty() || userEntity.getEmail().isEmpty()) {
            return false;
        }
        return true;
    }

}
