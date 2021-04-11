package com.harkka.livegreen.user;

import java.util.ArrayList;
import java.util.UUID;

public class UserManager {

    //User storage;
    public ArrayList<User> users = new ArrayList<>();
    public User user;
    public UserProfile uProfile;

    public static UserManager userManager = new UserManager(); // Singleton!!!

    public static UserManager getInstance() {
        return userManager;
    } // Singleton!!!

    private UserManager() {}

    //Todo: Necessary methods for Main - to be decided

    // For testing - At least
    // Todo: Return values to be agreed!
    public UUID createUser() {

        user = new User();
        users.add(user);
        System.out.println("UserManager/User Created: " + user);

        return user.userId;
    }

/*
    public UUID createUser( String userName ) {

        user = new User();
        user.userName = userName;
        users.add(user);
        System.out.println("UserManager/User Created: " + userName);

        return user.userId;
    }
*/

    public void createUserProfile( UUID uGuid ) {

        uProfile = new UserProfile(uGuid);

    }



    public void getUser(UUID guid) {

        //Todo: Implement fetch form ArrayList
        //User user = new User();
        //user = user.getUser(guid);

        System.out.println("UserManager/getUser(): " + user.userId);
        System.out.println("UserManager/getUser(): " + user.userName);
        System.out.println("UserManager/getUser(): " + user.userLastName);
        System.out.println("UserManager/getUser(): " + user.userFirstName);
        System.out.println("UserManager/getUser(): " + users.get(0).userName);


    }

    public UserProfile getUserProfile(UUID guid) {

        //Todo: Implement fetch form ArrayList
        //UserProfile uProfile = new UserProfile();

        System.out.println("UserManager/getUserProfile(): " + uProfile.userGuid);
        System.out.println("UserManager/getUserProfile(): " + uProfile.userName);
        System.out.println("UserManager/getUserProfile(): " + uProfile.userLastName);
        System.out.println("UserManager/getUserProfile(): " + uProfile.userFirstName);

        return uProfile;
    }

    // Todo: Proto using user, not userprofile


    public void setUserProfile(UUID uGuid, String uName, String fName, String lName) {
        user.userId = uGuid;
        if (!uName.isEmpty())
            user.userName = uName;
        if (!fName.isEmpty())
            user.userFirstName = fName;
        if (!lName.isEmpty())
            user.userLastName = lName;

        System.out.println("UserManager/setUserProfile: " + user.userName);
        System.out.println("UserManager/setUserProfile: " + user.userFirstName);
        System.out.println("UserManager/setUserProfile: " + user.userLastName);

        UserProfile userProfile = new UserProfile(uGuid, uName, fName, lName);

        // Todo: update ArrayList / DB
    }

}
