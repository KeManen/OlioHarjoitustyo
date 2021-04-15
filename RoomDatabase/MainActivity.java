package com.example.authhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Integrate components
    EditText userId, password;
    Button login, notYetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Integrate components
        login = findViewById(R.id.login);
        notYetUser = findViewById(R.id.notYetUser);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userIdText = userId.getText().toString();
                String passwordText = password.getText().toString();

                // check for empty inputs
                if (userIdText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // perform Query command at UserDao
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();

                    // Check database for existing user
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userEntity = userDao.login(userIdText, passwordText);
                            if (userEntity == null) {

                                // In case of user not found:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // move to homescreen fragment here and send username
                                String name = userEntity.userId;
                                startActivity(new Intent(MainActivity.this, HomeScreen.class).putExtra("name", name));

                            }
                        }
                    }).start();
                }

            }
        });

        notYetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateUser1.class));
            }
        });
    }
}
