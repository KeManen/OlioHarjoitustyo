package com.harkka.livegreen.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harkka.livegreen.R;
import com.harkka.livegreen.roomdb.UserDao;
import com.harkka.livegreen.roomdb.UserDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser1 extends AppCompatActivity {

    //database
    UserDao userDao;
    // Integrate components
    EditText userName, password, email, password2;
    Button register, toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user1);

        userDao = UserDatabase.getUserDatabase(getApplicationContext()).userDao();

        // integrate components
        register = findViewById(R.id.register);
        toLogin = findViewById(R.id.toLogin);
        userName = findViewById(R.id.userId);
        email = findViewById(R.id.email);
        password2 = findViewById(R.id.password2);
        password = findViewById(R.id.password);


        register.setOnClickListener(v -> {
            //validating input
            if(!validateInput()){
                return;
            }

            //move to second step and pass inputs
            startActivity(new Intent(CreateUser1.this, CreateUser2.class)
                    .putExtra("name", userName.getText().toString().trim())
                    .putExtra("email", email.getText().toString().trim())
                    .putExtra("password", password.getText().toString().trim())
            );
        });

        // move back to the previous Login screen
        toLogin.setOnClickListener(v -> startActivity(new Intent(CreateUser1.this, LoginActivity.class)));
    }

    // validateInput
    // Returns true if all checks are good
    // checks for password-match, empty fields, email format, password format,
    // and availability of username and email
    private Boolean validateInput(){
        if(!arePasswordsMatching()){
            System.out.println("Password mismatch");
            Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(isInputEmpty()){
            System.out.println("Fill all fields");
            Toast.makeText(getApplicationContext(), "Fill needed information", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isEmailFormatted()){
            System.out.println("Email is not formatted");
            Toast.makeText(getApplicationContext(), "Check email correctness", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isEmailFree()){
            System.out.println("Email is already in use");
            Toast.makeText(getApplicationContext(), "This email is already in use", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isUsernameFree()){
            System.out.println("Username is already in use");
            Toast.makeText(getApplicationContext(), "This username is already in use", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!isPasswordFormatted()){
            System.out.println("Password doesn't match criteria");
            Toast.makeText(getApplicationContext(), "The password doesn't match criteria", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // isUsernameFree
    // returns True if username is available
    private Boolean isUsernameFree(){
        //       usernames = [inputUsername, dbUsername]
        String[] usernames = {userName.getText().toString(), ""};

        //if sql search comes back with null it doesn't change the string
        new Thread(() -> usernames[1] = userDao.doesContainName(usernames[0])).start();
        return usernames[1] == "";

    }

    // isEmailFree
    // returns True if username is available
    private Boolean isEmailFree(){
        //       emails = [inputEmail, dbEmail]
        String[] emails = {email.getText().toString(), ""};

        //if sql search comes back with null it doesn't change the string
        new Thread(() -> emails[1] = userDao.doesContainName(emails[0])).start();
        return emails[1] == "";
    }

    // isEmailFormatted
    // returns true if email is acceptable by regex
    // accepted input: Text.text_text-texT@Text.text_text-texT.wWw
    private Boolean isEmailFormatted(){
        Pattern pattern = Pattern.compile("^(\\w|\\.|_|-)+[@](\\w|_|-|\\.)+[.]\\w{2,3}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email.getText().toString());
        return matcher.find();
    }

    // arePasswordsMatching
    // returns true if the passwords match
    private Boolean arePasswordsMatching(){
        return password.getText().toString().equals(password2.getText().toString());
    }

    // isPasswordFormatted()
    // checks if the password has at least
    // one number, special, lower, upper character and length of >12
    private Boolean isPasswordFormatted(){
        /*
        String passwordText = password.getText().toString().trim();
        Pattern textPattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)[^\\s]{12,}$");
        return textPattern.matcher(passwordText).matches();
        */
        return true;
    }

    // isEmpty
    // returns True if any input field is empty
    private Boolean isInputEmpty() {
        System.out.println("name: " + userName.getText().toString());
        System.out.println("email: " + email.getText().toString());
        System.out.println("pass1: " + password.getText().toString());
        System.out.println("pass2: " + password2.getText().toString());

        //Check if input is empty
        return userName.toString().trim().isEmpty() || password.toString().trim().isEmpty() || password2.toString().trim().isEmpty() || email.toString().trim().isEmpty();


    }

}
