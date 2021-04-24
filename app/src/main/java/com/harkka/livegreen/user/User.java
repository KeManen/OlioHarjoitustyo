package com.harkka.livegreen.user;

import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.roomdb.UserEntity;

import java.util.UUID;

public class User {

    public UUID userId;
    private String userEmail = null;
    public String userName = null;
    public String userPasswd = null;

    private String classString = "User Class: ";

    /*
    public String userFirstName= "testF";
    public String userLastName= "testL";
*/
    boolean userLogged = true;

    // For prototyping before sub class UserProfile is implemented


    // Todo: All constructors to be modified to use UserDB/datafile (first ArrayList to get basic functionality to work)

    public User() {
        userId =  getGuid();
        userLogged = true;
        System.out.println(userLogged);
        //UserProfile uProfile = new UserProfile();
    }
/*
    public User(String uName) {
        UserProfile uProfile = new UserProfile();
        uProfile.setUserProfileUName(uName);
        System.out.println("User()/uName: " + uProfile.userName);
    }

    public User(String firstName, String lastName) {
        UserProfile uProfile = new UserProfile();
        uProfile.setUserProfileFName(firstName);
        System.out.println(firstName);
        uProfile.setUserProfileLName(lastName);
        System.out.println(lastName);
    }
*/
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

    /*

    public String getUserFirstName(){ return userFirstName; }

    public String getUserLastName(){
        return userLastName;
    }

     */
    public boolean getUserIsLogged() { return userLogged; }

    public void setUserEmail( String uEmail) {
        userEmail = uEmail;
    }

    public void setUserName( String uName) {
        userName = uName;
    }

    public void setUserPasswd( String uPasswd ) {
        userPasswd = uPasswd;
    }

    /*
    public void setUserFirstName( String fName) {
        userFirstName = fName;
    }

    public void setUserLastName( String lName) {
        userLastName = lName;
    }
*/
    public void setUserIsLogged(boolean logged) {
        userLogged = logged;
    }

   // Internal auxiliary methods

   // Guid generation

    private UUID getGuid() {

        UUID guid = UUID.randomUUID();
        System.out.println(classString + " GetGuid()/Guid: " + guid);

        return guid;
    }

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
