package com.harkka.livegreen.iohandler;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
                        osw = new OutputStreamWriter(context.openFileOutput(userLogFile, Context.MODE_PRIVATE));
                        System.out.println("Appending Login in user logfile\n" + outString);
                        osw.append(outString);
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


    public void clearUserLogFileFlag(){
        this.userLogFile = "";
        this.osw = null;
    }

}
