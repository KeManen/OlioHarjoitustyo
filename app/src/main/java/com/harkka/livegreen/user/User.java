package com.harkka.livegreen.user;

import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.roomdb.UserDao;
import com.harkka.livegreen.roomdb.UserDatabase;
import com.harkka.livegreen.roomdb.UserEntity;

import java.util.UUID;

public class User {

//    public User user;
    public UUID userId;
    public String userEmail = null;
    public String userName = null;
    public String userPasswd = null;
    private String classString = "User Class: ";
    boolean userLogged = true;

    // Variables for data entity management
    private UserDatabase userDatabase;
    private UserDao userDao;
    private UserEntity userEntity = UserEntity.getInstance(); // Singleton for UserEntity class usage

    public User(){};

    //public User() { createUser(); } //TODO: Original, remove when not needed

    // Method to create a new user and initialize user Id
    public void createUser() {
        userId =  getGuid();
        userLogged = true;
        //UserProfile uProfile = new UserProfile();
    }

    public User getCurrentUser() {
        User user = new User();
        user.userId = userId;
        user.userEmail = userEmail;
        user.userName = userName;
        user.userPasswd = userPasswd;
        return user;
    }


    public User getUser(UUID guid) {
        User user = new User();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("****************** User fetch by id " + guid + "******************");
                userEntity = userDao.loadUserEntityByUserId(guid.toString());
             }
        }).start();
        user.userId = userEntity.getUserId();
        user.userEmail = userEntity.getEmail();
        user.userName = userEntity.getUserName();
        user.userPasswd = userEntity.getPassword();

        return user;
    }

    public UUID getUserId() {
        // Todo: For cases User is not created or loaded
        if (userId == null)
            userId = getGuid();
        return userId;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserPasswd(){
        return userPasswd;
    }

   public void setUserId ( UUID uuid ) { userId = uuid; }

    public void setUserEmail( String uEmail) {
        userEmail = uEmail;
    }

    public void setUserName( String uName) {
        userName = uName;
    }

    public void setUserPasswd( String uPasswd ) {
        userPasswd = uPasswd;
    }

   // Internal auxiliary methods

    // Create a new guid value
    private UUID getGuid() {

        UUID guid = UUID.randomUUID();
        System.out.println(classString + " GetGuid()/Guid: " + guid);

        return guid;
    }

    // User log in status
    public boolean getUserIsLogged() { return userLogged; }

    public void setUserIsLogged(boolean logged) {
        userLogged = logged;
    }

    // Get user rank for gamification purposes
    //TODO implement full logic
    public int getRank(){
        return (int) Math.round(Math.random()*5);
    }

    // Entry management

    // TODO: This is db interface method
    // Load all users
    public void loadUsers() {}

    // TODO: This is db interface method
    // Makes db insert of user
    public void insertDBUser() {
        UserEntity userEntity = UserEntity.getInstance();
        System.out.println("User/insertDBUser/userId: "+ userId);
        System.out.println("User/insertDBUser/userName: "+ userName);
        System.out.println("User/insertDBUser/userPass: "+ userPasswd);
        System.out.println("User/insertDBUser/userEmail: "+ userEmail);

        userEntity.setUserId(userId);
        userEntity.setUserName(userName);
        userEntity.setPassword(userPasswd);
        userEntity.setEmail(userEmail);
    }

}
