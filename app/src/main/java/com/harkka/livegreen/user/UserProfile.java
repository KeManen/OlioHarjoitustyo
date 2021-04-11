package com.harkka.livegreen.user;

import java.util.UUID;

public class UserProfile{
    public UUID userGuid;
    public String userName = "";
    public String userFirstName = "";
    public String userLastName = "";

    public UserProfile() {}

    public UserProfile(UUID uGuid) { userGuid = uGuid;}

    public UserProfile(UUID uGuid, String uName, String fName, String lName) {
        userGuid = uGuid;
        userName = uName;
        userFirstName = fName;
        userLastName = lName;

        System.out.println("UserProfile/UserProfile(): " + userGuid);
        System.out.println("UserProfile/UserProfile(): " + userName);
        System.out.println("UserProfile/UserProfile(): " + userFirstName);
        System.out.println("UserProfile/UserProfile(): " + userLastName);
    }

    public void setUserProfile(UUID uGuid, String uName, String fName, String lName) {
        userName = uName;
        userFirstName = fName;
        userLastName = lName;
    }

    public void setUserProfileUName(UUID uGuid, String uName) {
        userName = uName;
        System.out.println(uName);
        System.out.println("In UserProfile: "+userName);
    }

    public void setUserProfileFName(UUID uGuid, String fName) {
        userFirstName = fName;
    }

    public void setUserProfileLName(UUID uGuid, String lName) {
        userLastName = lName;
    }

    public UserProfile getUserProfile(UUID uGuid) {
        UserProfile uProfile = null;

        uProfile.userGuid = uGuid;
        uProfile.userName = userName;
        uProfile.userFirstName = userFirstName;
        uProfile.userLastName =  userLastName;

        return uProfile;
    }

}
