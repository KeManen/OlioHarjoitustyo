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

        System.out.println("UserManager/getUser()Guid: " + user.userId);
        System.out.println("UserManager/getUser()Email: " + user.getUserEmail());
        System.out.println("UserManager/getUser()Uname: " + user.userName);
        //System.out.println("UserManager/getUser(): " + user.userLastName);
        //System.out.println("UserManager/getUser(): " + user.userFirstName);
        System.out.println("UserManager/getUser()Uname2: " + users.get(0).userName);


    }

    public UserProfile getUserProfile(UUID guid) {

        //Todo: Implement fetch form ArrayList
        //UserProfile uProfile = new UserProfile();

        System.out.println("UserManager/getUserProfile()Guid: " + uProfile.userGuid);
        System.out.println("UserManager/getUserProfile()Lname: " + uProfile.userLastName);
        System.out.println("UserManager/getUserProfile()Fname: " + uProfile.userFirstName);
        System.out.println("UserManager/getUserProfile()Age: " + uProfile.userAge);
        System.out.println("UserManager/getUserProfile()Loc: " + uProfile.userLocation);

        return uProfile;
    }

    // Todo: Proto using user, not userprofile


    public void setUserProfile(UUID uGuid, String fName, String lName, int age, String location) {
        user.userId = uGuid;
        if (!fName.isEmpty())
            user.userFirstName = fName;
        if (!lName.isEmpty())
            user.userLastName = lName;

        System.out.println("UserManager/setUserProfile/Fname: " + user.userFirstName);
        System.out.println("UserManager/setUserProfile/Lname: " + user.userLastName);
        System.out.println("UserManager/setUserProfile/Age: " + age);
        System.out.println("UserManager/setUserProfile/Loc: " + location);

        UserProfile userProfile = new UserProfile(uGuid, fName, lName, age, location);

        // Todo: update ArrayList / DB
    }

}
