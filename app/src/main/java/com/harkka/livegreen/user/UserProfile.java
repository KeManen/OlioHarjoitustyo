package com.harkka.livegreen.user;

import com.harkka.livegreen.roomdb.UserEntity;

import java.util.UUID;

// Class for user profile management

public class UserProfile{
    private UUID userGuid;
    private String userFirstName = "";
    private String userLastName = "";
    private int userAge;
    private String userLocation = "";

    public UserProfile() {}

    public UserProfile(UUID uGuid) { userGuid = uGuid;}

    public UserProfile(UUID uGuid, String fName, String lName, int age, String location) {
        userGuid = uGuid;
        userFirstName = fName;
        userLastName = lName;
        userAge = age;
        userLocation = location;

        System.out.println("UserProfile/UserProfile()Guid: " + userGuid);
        System.out.println("UserProfile/UserProfile()Fname: " + userFirstName);
        System.out.println("UserProfile/UserProfile()Lname: " + userLastName);
        System.out.println("UserProfile/UserProfile()Age: " + userAge);
        System.out.println("UserProfile/UserProfile()Loc: " + userLocation);
    }

    public void setUserProfile(UUID uGuid, String fName, String lName, int age, String location) {
        userGuid = uGuid;
        userFirstName = fName;
        userLastName = lName;
        userAge = age;
        userLocation = location;
    }

    public void setUserProfileFName(UUID uGuid, String fName) {
        userFirstName = fName;
    }

    public void setUserProfileFName( String fName) {
        userFirstName = fName;
    }

    public void setUserProfileLName(UUID uGuid, String lName) {
        userLastName = lName;
    }

    public void setUserProfileLName(String lName) {
        userLastName = lName;
    }

    /*public void setUserProfileAge(UUID uGuid, int age) {
        userAge = age;
    }
*/
    public void setUserProfileAge(int age) {
        userAge = age;
    }

    public void setUserProfileLocation(UUID uGuid, String location) {
        userLocation = location;
    }

    public void setUserProfileLocation(String location) {
        userLocation = location;
    }

    public UserProfile getUserProfile(UUID uGuid) {
        UserProfile uProfile = null;

        uProfile.userGuid = uGuid; // Not to be set in final -> used for fetching the profile
        uProfile.userFirstName = userFirstName;
        uProfile.userLastName =  userLastName;
        uProfile.userAge = userAge;
        uProfile.userLocation = userLocation;

        return uProfile;
    }

    public UUID getUserId() {
        return userGuid;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public int getUserAge() {
        return userAge;
    }

    public String getUserLocation() {
        return userLocation;
    }

    // Makes db insert of user
    public void insertDBUserProfile() {
        UserEntity userEntity = UserEntity.getInstance();
        System.out.println("User/insertDBUserProfile/userFName: "+ userFirstName);
        System.out.println("User/insertDBUserProfile/userLName: "+ userLastName);
        System.out.println("User/insertDBUserProfile/userAge: "+ userAge);
        System.out.println("User/insertDBUserProfile/userLocation: "+ userLocation);

        userEntity.setFirstName(userFirstName);
        userEntity.setLastName(userLastName);
        userEntity.setAge(Integer.toString(userAge));
        userEntity.setLocation(userLocation);
    }

}
