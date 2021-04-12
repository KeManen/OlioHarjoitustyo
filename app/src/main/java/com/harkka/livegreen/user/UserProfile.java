package com.harkka.livegreen.user;

import java.util.UUID;

public class UserProfile{
    public UUID userGuid;
    public String userFirstName = "";
    public String userLastName = "";
    public int userAge;
    public String userLocation = "";

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
        UUID guid = uGuid;

        // Todo: save in Arraylist/permanent DB

        userFirstName = fName;
        userLastName = lName;
        userAge = age;
        userLocation = location;
    }

    public void setUserProfileFName(UUID uGuid, String fName) {
        userFirstName = fName;
    }

    public void setUserProfileLName(UUID uGuid, String lName) {
        userLastName = lName;
    }

    public void setUserProfileAge(UUID uGuid, int age) {
        userAge = age;
    }

    public void setUserProfileLocation(UUID uGuid, String location) {
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

}
