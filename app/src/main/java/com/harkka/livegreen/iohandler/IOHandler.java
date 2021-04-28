package com.harkka.livegreen.iohandler;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// IOHandler class to be used as singleton class for IO write and read operations
// IO operations can be implemented in separate classes, or as in this example implementation (.csv) inside the IOHander

public class IOHandler {

    private String classString = "IOHandler: ";
    private String userLogFile = "";
    private LocalDateTime dateTime = null;
    OutputStreamWriter osw;

    public static IOHandler ioHandler = new IOHandler(); // Singleton!!!
    public static IOHandler getInstance() {
        return ioHandler;
    } // Singleton!!!

    // Method to manage user logs in user creation, login and logout
    // Action selects the right operation: Create = 0, Login = 1, Logout = 2
    public void doFileAction(Context context, UUID guid, String userName, int action) {

        System.out.println(classString + "File directory is " + context.getFilesDir());

        switch (action) {
            case 0:
                if (userLogFile.isEmpty() && !guid.toString().isEmpty() && !userName.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateTime = LocalDateTime.now();
                    }
                    String created = ", Creation, Guid, ";
                    String outString = dateTime.toString() + created + guid.toString() + ", Username, " + userName + "\n";
                    try {
                        // This try part can be replaced by any writer class that developer likes to implement, now -> .csv
                        userLogFile = guid.toString() + "_" + userName + ".csv";
                        osw = new OutputStreamWriter(context.openFileOutput(userLogFile, Context.MODE_PRIVATE));
                        System.out.println("Creating user logfile\n" + outString);
                        osw.write(outString);
                        //osw.close();
                    } catch (IOException e) {
                        Log.e("IOException", "Error in user logfile creation");
                    } finally {
                    }
                }
                break;
            case 1:
                if (!guid.toString().isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateTime = LocalDateTime.now();
                    }
                    String login = ", Login, Guid, ";
                    String outString = dateTime.toString() + login + guid.toString() + ", Username, " + userName + "\n";
                    try {
                        // This try part can be replaced by any writer class that developer likes to implement, now -> .csv
                        userLogFile = guid.toString() + "_" + userName + ".csv";
                        String outFileString = createLogFileString(context, userLogFile);
                        osw = new OutputStreamWriter(context.openFileOutput(userLogFile, Context.MODE_PRIVATE));
                        System.out.println("Appending Login in user logfile\n" + outFileString + outString);
                        outFileString = outFileString + outString;
                        osw.write(outFileString);
                     } catch (IOException e) {
                        Log.e("IOException", "Error in user logfile append");
                        Toast.makeText(context.getApplicationContext(), "Error in user logfile append", Toast.LENGTH_SHORT).show();
                    } finally {
                    }
                }
                break;
            case 2:
                if (!guid.toString().isEmpty() && !userName.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateTime = LocalDateTime.now();
                    }
                    String logout = ", Logout, Guid, ";
                    String outString = dateTime.toString() + logout + guid.toString() + ", Username, " + userName + "\n";
                    try {
                        // This try part can be replaced by any writer class that developer likes to implement, now -> .csv
                        osw.append(outString);
                        System.out.println("Closing user logfile");
                        osw.close();
                    } catch (IOException e) {
                        Log.e("IOException", "Error in user logfile append");
                    } finally {
                    }
                    clearUserLogFileFlag();
                }
                break;
            default:
                System.out.println(classString + ": Error in file creation or write");
                break;
        }
    }

    // Clear userLogFile name for next login
    private void clearUserLogFileFlag(){
        this.userLogFile = "";
        this.osw = null;
    }

    // Read old logs in a string and return it to the write function
    private String createLogFileString(Context context, String userLogFile) {
        String outFileString = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.now();
        }
        try {
            System.out.println("Input file opened: " + userLogFile);
            outFileString = fromLogStream(context.openFileInput(userLogFile));
        } catch (IOException e) {
            Log.e("IOException", "Error in user logfile append");
            System.out.println("Error in user logfile append");
        } finally {
            System.out.println("Output log file string created: " + outFileString);
        }
        return outFileString;
    }

    //Read log file i a string for appending next log value
    public static String fromLogStream(InputStream in) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append(newLine);
        }
        reader.close();
        return out.toString();
    }

}
