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

public class LoginActivity extends AppCompatActivity {

    // Integrate components
    EditText userId, password;
    Button login, notYetUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);

        // Integrate components
        login = findViewById(R.id.login);
        notYetUser = findViewById(R.id.notYetUser);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);


        login.setOnClickListener(v -> {
            String userIdText = userId.getText().toString();
            String passwordText = password.getText().toString();
            System.out.println("################################################");
            System.out.println("id and pass");
            System.out.println(userIdText.isEmpty());
            System.out.println(passwordText.isEmpty());
            // check for empty inputs
            if (userIdText.isEmpty() || passwordText.isEmpty()) {
                System.out.println("fill fields");
                Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            // perform Query command at UserDao
            UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
            UserDao userDao = userDatabase.userDao();

            // Check database for existing user
            new Thread(() -> {
                UserEntity userEntity = userDao.login(userIdText, passwordText);

                if (userEntity == null) {

                    // In case of user not found:
                    runOnUiThread(() -> {
                        System.out.println("Invalid credentials");
                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    });

                    return;
                }
                System.out.println("login successful");
                // move to mainactivity fragment here and send username
                String name = userEntity.userId;

                //Toast.makeText(getApplicationContext(), "Welcome "+name+"!", Toast.LENGTH_SHORT).show(); crashes
                startActivity(new Intent(getApplicationContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("name", name)
                );

            }).start();
        });

        notYetUser.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateUser1.class)));
    }

    @Override
    public void onBackPressed() {
    }
}
