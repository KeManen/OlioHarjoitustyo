package com.harkka.livegreen.roomdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {UserEntity.class}, version = 1) // todo This should be changed to 2 + some fixes, currently change causes issues with login/user creation
public abstract class UserDatabase extends RoomDatabase {

    // creating the user database

    private static final String dbName = "user";
    private static UserDatabase userDatabase;

    // TODo: to be checked if works correctly, looks like it

    // Migration from 1 to 2, Room 2.2.0
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE new_users (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "userId TEXT," +
                    "userName TEXT," +
                    "userPassword TEXT," +
                    "userEmail TEXT)");

            database.execSQL("INSERT INTO new_users (id, userName, userPassword, userEmail) " +
                    "SELECT id, userId, password, email FROM users");

            database.execSQL("DROP TABLE users");

            database.execSQL("ALTER TABLE new_users RENAME TO users");
        }
    };




    public static synchronized UserDatabase getUserDatabase(Context context) {

        if (userDatabase == null) {
// TODO: Migration build
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, dbName)
                    .addMigrations(MIGRATION_1_2).build();
/* Original build
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, dbName)
                    .fallbackToDestructiveMigration().build();
*/
        }
        return userDatabase;
    }

    public abstract UserDao userDao();

}
