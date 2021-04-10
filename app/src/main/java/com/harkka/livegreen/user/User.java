package com.harkka.livegreen.user;

import java.util.UUID;

public class User {

    private UUID userId;

    // For prototyping before sub class UserProfile is implemented
    private String userName;
    private String userFirstName;
    private String userLastName;
    // For prototyping before sub class UserProfile is implemented


    // Todo: All constructors to be modified to use UserDB/datafile (first ArrayList to get basic functionality to work)

    public User() {
        userId =  getGuid();
        userName = "TestUser";
        userFirstName = "TestFirst";
        userLastName = "TestLast";
    }

    public User(String uName) {
        userId = getGuid();
        userName = uName;
        // userProfile.setUserName(uName);
        userFirstName = "TestFirst";
        userLastName = "TestLast";
    }

    public User(String firstName, String lastName) {
        userId = getGuid();
        String userName = "TestUser";
        // userProfile.setFirstName(firstName);
        userFirstName = firstName;
        System.out.println(userFirstName);
        // userProfile.setLastName(lastName);
        userLastName = lastName;
        System.out.println(userLastName);
    }

    public User getUser(UUID guid) {

        User user = new User(); // Todo: Implement fetch from UserDB/file

        user.userId = userId;
        user.userName = userName;
        user.userFirstName = userFirstName;
        user.userLastName = userLastName;

        return user;
    }

   // Internal auxiliary methods

   // Guid generation

    private UUID getGuid() {

        UUID guid = UUID.randomUUID();
        System.out.println("Guid: " + guid);

        return guid;
    }
}
