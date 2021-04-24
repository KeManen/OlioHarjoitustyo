package com.harkka.livegreen.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harkka.livegreen.MainActivity;
import com.harkka.livegreen.R;

public class LoginActivity extends AppCompatActivity {

    // Integrate components
    EditText userId, password;
    Button login, notYetUser;

    //database for users
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_main);

        //get database
        userDao = UserDatabase.getUserDatabase(getApplicationContext()).userDao();

        // Integrate components
        login = findViewById(R.id.login);
        notYetUser = findViewById(R.id.notYetUser);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);


        login.setOnClickListener(v -> {
            // get inputs
            String userIdText = userId.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            //debug prints
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

                //move to main activity and remove this view from backtrace for ux reasons
                startActivity(new Intent(getApplicationContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                );

            }).start();
        });

        //function to change view to createuser1
        notYetUser.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreateUser1.class)));
    }

    //disable back-button so the user can't just escape
    @Override
    public void onBackPressed() {
    }
}
