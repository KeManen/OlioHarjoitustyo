package com.example.authhandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {

    // Test screen where succesfull login moves to

    // Integrate components
    TextView tName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Integrate components
        setContentView(R.layout.activity_home_screen);

        // set userId to textview in homescreen
        tName = findViewById(R.id.name);
        String name = getIntent().getStringExtra("name");
        tName.setText(name);

    }
}
