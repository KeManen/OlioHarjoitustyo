package com.example.authhandler;

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

    /*
    @Query("SELECT * from users where userId=(:userId) and password=(:password) and email=(:email)")
    UserEntity login(String userId, String password, String email);
     */

}
