package com.harkka.livegreen.roomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.harkka.livegreen.R;
import com.harkka.livegreen.user.UserManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser1 extends AppCompatActivity {

    // Variables for user management
    UserManager uManager = UserManager.getInstance(); // Singleton for User class usage
    UserDatabase userDatabase;
    UserDao userDao;
    // Integrate components
    EditText userName, password, email, password2;
    Button register, toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user1);

        userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
        userDao = userDatabase.userDao();

        // integrate components
        register = findViewById(R.id.register);
        toLogin = findViewById(R.id.toLogin);
        userName = findViewById(R.id.userId);
        email = findViewById(R.id.email);
        password2 = findViewById(R.id.password2);
        password = findViewById(R.id.password);


        register.setOnClickListener(v -> {

            if(!arePasswordsMatching()){
                Toast.makeText(getApplicationContext(), "Passwords does not match", Toast.LENGTH_SHORT).show();
                return;
            }
            if(isInputEmpty()){
                Toast.makeText(getApplicationContext(), "Fill needed information", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isEmailFormatted()){
                Toast.makeText(getApplicationContext(), "Check email correctness", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isEmailFree()){
                Toast.makeText(getApplicationContext(), "This email is already in use", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isUsernameFree()){
                Toast.makeText(getApplicationContext(), "This username is already in use", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!isPasswordFormatted()){
                Toast.makeText(getApplicationContext(), "The password doesn't match criteria", Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(new Intent(CreateUser1.this, CreateUser2.class)
                    .putExtra("name", userName.getText().toString())
                    .putExtra("email", email.getText().toString())
                    .putExtra("password", password.getText().toString()));
        });

        // move back to the previous Login screen
        toLogin.setOnClickListener(v -> startActivity(new Intent(CreateUser1.this, LoginActivity.class)));
    }

    private Boolean isUsernameFree(){
        //       usernames = [inputUsername, dbUsername]
        String[] usernames = {userName.getText().toString(), ""};

        new Thread(() -> usernames[1] = userDao.doesContainName(usernames[0])).start();
        return usernames[1] == "";

    }
    private Boolean isEmailFree(){
        //       emails = [inputEmail, dbEmail]
        String[] emails = {email.getText().toString(), ""};

        new Thread(() -> emails[1] = userDao.doesContainName(emails[0])).start();
        return emails[1] == "";
    }
    private Boolean isEmailFormatted(){
        Pattern pattern = Pattern.compile("^(\\w|\\.|_|-)+[@](\\w|_|-|\\.)+[.]\\w{2,3}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email.getText().toString());
        return matcher.find();
    }
    private Boolean arePasswordsMatching(){
        return password.getText().toString().equals(password2.getText().toString());
    }

    //TODO IMPLEMENT PASSWORD reqs
    private Boolean isPasswordFormatted(){
        return true;
    }

    // check given information --> is the input empty?
    private Boolean isInputEmpty() {
        System.out.println("name: " + userName.getText().toString());
        System.out.println("email: " + email.getText().toString());
        System.out.println("pass1: " + password.getText().toString());
        System.out.println("pass2: " + password2.getText().toString());

        //Check if input is empty
        return userName.toString().isEmpty() || password.toString().isEmpty() || password2.toString().isEmpty() || email.toString().isEmpty();


    }

}
