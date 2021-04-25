package com.harkka.livegreen.roomdb;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {

    // DAO = Data Access Objects

    // Register (create) user to the database
    @Insert
    void registerUser(UserEntity userEntity);

    // Insert new user
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUserEntity(UserEntity userEntity);

    // Basic fetch methods for User entity

    // Load all
    @Query("SELECT * FROM users")
    public UserEntity[] loadAllUserEntities();

    // Load user by id
    @Query("SELECT * FROM users WHERE userId = :userId")
    public UserEntity loadUserEntityByUserId(String userId);

    // Find user in login by userid and password
    @Query("SELECT * from users where userId=(:userId) and password=(:password)")
    UserEntity login(String userId, String password);

    // Check if user exists
    @Query("SELECT userId from users where userId=(:userId)")
    String doesContainName(String userId);

    // Check if email exists
    @Query("SELECT email from users where email=(:email)")
    String doesContainEmail(String email);

    /*
    @Query("SELECT * from users where userId=(:userId) and password=(:password) and email=(:email)")
    UserEntity login(String userId, String password, String email);
     */

}
