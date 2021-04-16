package com.example.barchart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonBarChart).setOnClickListener(new View.OnClickListener() {
            // on button click move to BarChartActivity.java
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BarChartActivity.class));
            }
        });

    }
}
