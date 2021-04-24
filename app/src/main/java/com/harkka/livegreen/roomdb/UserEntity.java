package com.harkka.livegreen.roomdb;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "users")
public class UserEntity {

    public static UserEntity userEntity = new UserEntity(); // Singleton!!!

    public static UserEntity getInstance() {
        return userEntity;
    } // Singleton!!!

    // Needed information
    // getters + setters

    // login and create new user fragment 1
    // Todo: updated to version 2, to be fixed in UserDatabase

    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name = "userId")
    String userId;

    @ColumnInfo(name = "userName")
    String userName;

    @ColumnInfo(name = "password")
    String password;

    @ColumnInfo(name = "email")
    String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Room cannot handle UUID -> conversions to String and back
    public UUID getUserId() {
        return UUID.fromString(userId);
    }

    public void setUserId(UUID userId) {
        this.userId = userId.toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userId = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }


    // create new user fragment 2
    // getters and setter

    @ColumnInfo(name = "firstName")
    String firstName;

    @ColumnInfo(name = "lastName")
    String lastName;

    @ColumnInfo(name = "age")
    String age;

    @ColumnInfo(name = "location")
    String location;

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName){ this.lastName = lastName; }

    public String getAge() { return age; }

    public void setAge(String age) { this.age = age; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

}
