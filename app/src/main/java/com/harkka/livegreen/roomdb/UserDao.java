package com.harkka.livegreen.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    // DAO = Data Access Objects

    // create user to the database method
    @Insert
    void registerUser(UserEntity userEntity);

    // adding Login Dao method
    // add what information to get from the user
    @Query("SELECT * from users where userId=(:userId) and password=(:password)")
    UserEntity login(String userId, String password);

    @Query("SELECT userId from users where userId=(:userId)")
    String doesContainName(String userId);

    @Query("SELECT email from users where email=(:email)")
    String doesContainEmail(String email);

    /*
    @Query("SELECT * from users where userId=(:userId) and password=(:password) and email=(:email)")
    UserEntity login(String userId, String password, String email);
     */

}
