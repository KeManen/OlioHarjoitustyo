package com.harkka.livegreen.user;

import java.util.UUID;

public class UserManager {

    UUID uGuid;

    public static UserManager userManager = new UserManager(); // Singleton!!!

    public static UserManager getInstance() {
        return userManager;
    } // Singleton!!!

    private UserManager() {}

    //Todo: Necessary methods for Main - to be decided

    // For testing - At least
    // Todo: Return values to be agreed!
    public void createUser( String userName ) {

        User user = new User(userName);
        System.out.println("UserManager/Created: " + userName);

    }

    public void showUser(UUID guid) {
        User user = null;

        user = user.getUser(guid);

        System.out.println("UserManager: " + user.getUser(guid));

    }

}
