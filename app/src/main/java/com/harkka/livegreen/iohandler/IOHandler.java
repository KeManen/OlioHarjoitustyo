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

public class IOHandler {

    private String classString = "IOHandler: ";
    private String userLogFile = "";
    private LocalDateTime dateTime = null;
    OutputStreamWriter osw;

    public static IOHandler ioHandler = new IOHandler(); // Singleton!!!
    public static IOHandler getInstance() {
        return ioHandler;
    } // Singleton!!!

    public void doFileAction(Context context, UUID guid, String userName, int action) {
        int status_ok = 1;

        System.out.println(classString + "File directory is " + context.getFilesDir());

        switch (action) {
            case 0:
                if (userLogFile.isEmpty() && !guid.toString().isEmpty() && !userName.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateTime = LocalDateTime.now();
                    }
                    String created = " Creation, ";
                    String outString = dateTime.toString() + created + guid.toString() + ", " + userName + "\n";
                    try {
                        //TODO This try part  can be replaced by any writer class that developer likes to implement
                        userLogFile = guid.toString() + "_" + userName + ".csv";
                        osw = new OutputStreamWriter(context.openFileOutput(userLogFile, Context.MODE_PRIVATE));
                        System.out.println("Creating user logfile\n" + outString);
                        osw.write(outString);
                        //osw.close();
                    } catch (IOException e) {
                        status_ok = 0;
                        Log.e("IOException", "Error in user logfile creation");
 //                       Toast.makeText(context.getApplicationContext(), "Error in user logfile creation", Toast.LENGTH_SHORT).show();
                    } finally {
//                       if ( status_ok == 1 )
//                            Toast.makeText(context.getApplicationContext(), "User logfile created", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 1:
                if (!guid.toString().isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateTime = LocalDateTime.now();
                    }
                    String login = " Login, ";
                    String outString = dateTime.toString() + login + guid.toString() + ", Username: " + userName + "\n";
                    try {
                        //TODO This try part can be replaced by any writer class that developer likes to implement
                        userLogFile = guid.toString() + "_" + userName + ".csv";

                        String outFileString = createLogFileString(context, userLogFile);

                        osw = new OutputStreamWriter(context.openFileOutput(userLogFile, Context.MODE_PRIVATE));

                        System.out.println("Appending Login in user logfile\n" + outFileString + outString);
                        outFileString = outFileString + outString;
                        osw.write(outFileString);
                        System.out.println("Appended");
                        //osw.close();
                    } catch (IOException e) {
                        status_ok = 0;
                        Log.e("IOException", "Error in user logfile append");
                        Toast.makeText(context.getApplicationContext(), "Error in user logfile append", Toast.LENGTH_SHORT).show();
                    } finally {
//                        if ( status_ok == 1 )
//                            Toast.makeText(context.getApplicationContext(), "User logfile appended", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case 2:
                if (!guid.toString().isEmpty() && !userName.isEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        dateTime = LocalDateTime.now();
                    }
                    String logout = " Logout, ";
                    String outString = dateTime.toString() + logout + guid.toString() + ", Username: " + userName + "\n";
                    try {
                        //TODO This try part can be replaced by any writer class that developer likes to implement
/*                        if ( osw == null ) {
                            userLogFile = guid.toString() + "_" + userName + ".csv";
                            osw = new OutputStreamWriter(context.openFileOutput(userLogFile, Context.MODE_PRIVATE));
                        }
*/                        System.out.println("Appending Logout in user logfile\n" + outString);
                        osw.append(outString);
                        System.out.println("Closing user logfile");
                        osw.close();
                    } catch (IOException e) {
                        status_ok = 0;
                        Log.e("IOException", "Error in user logfile append");
 //                       Toast.makeText(context.getApplicationContext(), "Error in user logfile append", Toast.LENGTH_SHORT).show();
                    } finally {
//                        if (status_ok == 1)
//                            Toast.makeText(context.getApplicationContext(), "User logfile appended", Toast.LENGTH_SHORT).show();
                    }
                    clearUserLogFileFlag();
                }
                break;
            default:
                System.out.println(classString + ": Error in file creation or write");
                break;
        }
    }


    private void clearUserLogFileFlag(){
        this.userLogFile = "";
        this.osw = null;
    }

    private String createLogFileString(Context context, String userLogFile) {
        String outFileString = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dateTime = LocalDateTime.now();
        }
        String login = " Login, ";
        String inputLine = "Start";
        String iswOut = "";

/*
        userLogFile = context.getFilesDir().toString() + guid.toString() + "_" + userName + ".csv";
        System.out.println(userLogFile);

        try {
            BufferedReader in = new BufferedReader(new FileReader(userLogFile));
            while ( inputLine != null ) {
                try {
                    inputLine = in.readLine();
                    outFileString = outFileString + inputLine;
                }
                catch (IOException exception) {
                    Log.e("IOException", "Error in user logfile append");
                    System.out.println("Error in user logfile append");
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            Log.e("FileNotFoundException", "File not found error");
            System.out.println("File not found error");
        }
        outFileString = outFileString + outString;
*/

        try {
            //TODO This try part can be replaced by any writer class that developer likes to implement
            //InputStreamReader isw = new InputStreamReader(context.openFileInput(userLogFile));
            System.out.println("Input file opened: " + userLogFile);

            outFileString = fromLogStream(context.openFileInput(userLogFile));

            //outFileString = outFileString + outString;
            System.out.println("Output string created: " + outFileString);

            //isw.close();
        } catch (IOException e) {
            Log.e("IOException", "Error in user logfile append");
            System.out.println("Error in user logfile append");
        } finally {
            System.out.println("Output log file string created: " + outFileString);
        }

        return outFileString;
    }

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
