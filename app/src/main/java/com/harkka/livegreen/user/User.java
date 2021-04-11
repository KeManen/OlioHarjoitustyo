package com.harkka.livegreen.user;

import java.util.UUID;

public class User {

    public UUID userId;

    // For prototyping before sub class UserProfile is implemented
    public String userName = "testU";
    public String userFirstName= "testF";
    public String userLastName= "testL";
    // For prototyping before sub class UserProfile is implemented


    // Todo: All constructors to be modified to use UserDB/datafile (first ArrayList to get basic functionality to work)

    public User() {
        userId =  getGuid();
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

        User user = new User(); // Todo: Implement fetch from UserDB/file

        user.userId = userId;
        user.userName = userName;
        user.userFirstName = userFirstName;
        user.userLastName = userLastName;

        return user;
    }

    public UUID getUserId() { return userId; }

    public String getUserName(){
        return userName;
    }

    public String getUserFirstName(){
        return userFirstName;
    }

    public String getUserLastName(){
        return userLastName;
    }

    public void setUserName( String uName) {
        userName = uName;
    }

    public void setUserFirstName( String fName) {
        userName = fName;
    }

    public void setUserLastName( String lName) {
        userName = lName;
    }


   // Internal auxiliary methods

   // Guid generation

    private UUID getGuid() {

        UUID guid = UUID.randomUUID();
        System.out.println("GetGuid()/Guid: " + guid);

        return guid;
    }
}
