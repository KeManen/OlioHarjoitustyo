package com.harkka.livegreen.user;

import android.content.Context;

import com.harkka.livegreen.roomdb.UserDatabase;
import com.harkka.livegreen.roomdb.UserEntity;


import java.util.UUID;

// Handler class for User management

public class UserManager {

    //User storage;
    private User user;
    private UserProfile userProfile;
    private final UserDatabase userDatabase;
    private final UserEntity userEntity;

    private static UserManager userManager; // Singleton!!!

    public static UserManager getInstance(Context context) {
        if (userManager == null){
            userManager = new UserManager(context);
        }
        return userManager;
    } // Singleton!!!

    private UserManager(Context context) {
        userEntity = UserEntity.getInstance();
        userDatabase = UserDatabase.getUserDatabase(context);
    }

    public User createUser() {
        user = new User();
        user.createUser();
        System.out.println("UserManager/User Created: " + user + " Guid: " + user.getUserId());

        return user;
    }

    public UserProfile createUserProfile( UUID uGuid ) {
        userProfile = new UserProfile(uGuid);

        return userProfile;
    }

    public UUID getCurrentUserUUID() { return user.getUserId(); }

    public User getCurrentUser(){
        return user;
    }

    public void setCurrentUser(UUID uuid){
        this.user = this.getUser(uuid);
    }

    public void setCurrentUser(UserEntity userEntity){
        User newUser = new User();
        newUser.setUserId(userEntity.getUserId());
        newUser.setUserName(userEntity.getUserName());
        newUser.setUserPasswd(userEntity.getPassword());
        newUser.setUserEmail(userEntity.getEmail());

        this.user = newUser;
        this.userProfile = getUserProfile(user.getUserId());
    }

    public void noCurrentUser(){
        this.user = null;
        this.userProfile = null;
    }

    public User getUser(UUID guid) {
        UserEntity tempUserEntity = userDatabase.userDao().loadUserEntityByUserId(guid.toString());

        if(tempUserEntity==null){
            return null;
        }

        User newUser = new User();
        newUser.setUserId(tempUserEntity.getUserId());
        newUser.setUserName(tempUserEntity.getUserName());
        newUser.setUserPasswd(tempUserEntity.getPassword());
        newUser.setUserEmail(tempUserEntity.getEmail());
        return newUser;
    }

    public UserProfile getCurrentUserProfile() {
        System.out.println("UserManager/getCurrentUserProfile()Guid: " + userProfile.getUserId());
        System.out.println("UserManager/getCurrentUserProfile()Lname: " + userProfile.getUserLastName());
        System.out.println("UserManager/getCurrentUserProfile()Fname: " + userProfile.getUserFirstName());
        System.out.println("UserManager/getCurrentUserProfile()Age: " + userProfile.getUserAge());
        System.out.println("UserManager/getCurrentUserProfile()Loc: " + userProfile.getUserLocation());

        return userProfile;
    }


    public UserProfile getUserProfile(UUID guid) {
        UserEntity tempUserEntity = userDatabase.userDao().loadUserEntityByUserId(guid.toString());

        if(tempUserEntity==null){
            return null;
        }
        UserProfile newUserProfile = new UserProfile();
        newUserProfile.setUserProfile(tempUserEntity.getUserId(),
                tempUserEntity.getFirstName(),
                tempUserEntity.getLastName(),
                Integer.parseInt(tempUserEntity.getAge()),
                tempUserEntity.getLocation());


        System.out.println("UserManager/getUserProfile()Guid: " + newUserProfile.getUserId());
        System.out.println("UserManager/getUserProfile()Lname: " + newUserProfile.getUserLastName());
        System.out.println("UserManager/getUserProfile()Fname: " + newUserProfile.getUserFirstName());
        System.out.println("UserManager/getUserProfile()Age: " + newUserProfile.getUserAge());
        System.out.println("UserManager/getUserProfile()Loc: " + newUserProfile.getUserLocation());

        return newUserProfile;
    }
}
