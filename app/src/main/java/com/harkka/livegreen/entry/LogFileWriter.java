package com.harkka.livegreen.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.harkka.livegreen.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

public class LogFileWriter extends AppCompatActivity {

    Button writeFiles;
    Context context = null;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("TEST TEST TEST TEST TEST TEST ");

        context = LogFileWriter.this;
        writeFiles = findViewById(R.id.writeFiles);
        writeFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFile = "UserLog.txt";
                String dataFile = "DataLog.txt";


                // Write userdata to log file
                try {
                    OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(userFile, Context.MODE_PRIVATE));

                    //TODO add what to write into log file here


                    osw.close();
                } catch (IOException e) {
                    Log.e("IOException", "Error in write");
                } finally {
                    Toast.makeText(getApplicationContext(), "First file ready", Toast.LENGTH_SHORT).show();
                }


                // Write inputdata to log file
                try {
                    OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(dataFile, Context.MODE_PRIVATE));

                    //TODO add what to write into log file here


                    osw.close();
                } catch (IOException e) {
                    Log.e("IOException", "Error in write");
                } finally {
                    // Sleep for 1second so user has time to read previous Toast message
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "Files ready", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}