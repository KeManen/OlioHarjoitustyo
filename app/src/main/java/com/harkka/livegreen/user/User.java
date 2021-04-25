package com.harkka.livegreen.user;

import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.roomdb.UserEntity;

import java.util.UUID;

public class User {

    public User user;
    public UUID userId;
    private String userEmail = null;
    public String userName = null;
    public String userPasswd = null;
    private String classString = "User Class: ";
    boolean userLogged = true;


    public User() {
        userId =  getGuid();
        userLogged = true;
        System.out.println(userLogged);
        //UserProfile uProfile = new UserProfile();
    }

    public User getCurrentUser() {
        user.userId = userId;
        user.userEmail = userEmail;
        user.userName = userName;
        user.userPasswd = userPasswd;
        return user;
    }


    public User getUser(UUID guid) {
        User user = new User(); // Todo: Implement fetch from UserDB
        user.userId = userId;
        user.userEmail = userEmail;
        user.userName = userName;
        user.userPasswd = userPasswd;
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

        userEntity.setUserId(userId);
        userEntity.setUserName(userName);
        userEntity.setPassword(userPasswd);
        userEntity.setEmail(userEmail);
    }

}
