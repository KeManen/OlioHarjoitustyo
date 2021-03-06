package com.harkka.livegreen.roomdb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// RoomDB management
// Creation of the database
// Migration examples for possible future use

@Database(entities = {UserEntity.class, DataEntity.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    // creating the user database

    private static final String dbName = "user";
    private static UserDatabase userDatabase;

/*

    // Migration from 1 to 2, Room 2.2.0, User Entity changes, userId and userName changed
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

    // Migration from 2 to 3, Room 2.2.0, Data entity changes, userID, entryId added
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE new_data (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "userId TEXT," +
                    "entryId TEXT," +
                    "dateTime TEXT," +
                    "weight TEXT," +
                    "height TEXT," +
                    "dairyUsed TEXT," +
                    "meatUsed TEXT," +
                    "vegeUsed TEXT)");

            database.execSQL("INSERT INTO new_data (id, weight, height, dairyUsed, meatUsed, vegeUsed) " +
                    "SELECT id, weight, height, dairyUsed, meatUsed, vegeUsed FROM data");

            database.execSQL("DROP TABLE data");

            database.execSQL("ALTER TABLE new_data RENAME TO data");
        }
    };

    // Migration from 3 to 4, Room 2.2.0, Data entity changes, firstname, lastname, age, location added
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE new_users (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "userId TEXT," +
                    "userName TEXT," +
                    "password TEXT," +
                    "email TEXT, " +
                    "firstname TEXT, " +
                    "lastname TEXT, " +
                    "age TEXT, " +
                    "location TEXT)");

            database.execSQL("INSERT INTO new_users (id, userId, userName, password, email) " +
                    "SELECT id, userId, userName, password, email FROM users");

            database.execSQL("DROP TABLE users");

            database.execSQL("ALTER TABLE new_users RENAME TO users");
        }
    };

    // Migration from 4 to 5, Room 2.2.0, Data entity changes, totalResult added
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE new_data (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "userId TEXT," +
                    "entryId TEXT," +
                    "dateTime TEXT," +
                    "weight TEXT," +
                    "height TEXT," +
                    "dairyUsed TEXT," +
                    "meatUsed TEXT," +
                    "vegeUsed TEXT," +
                    "totalResult TEXT)");

            database.execSQL("INSERT INTO new_data (id, userId, entryId, weight, height, dairyUsed, meatUsed, vegeUsed) " +
                    "SELECT id, userId, entryId, weight, height, dairyUsed, meatUsed, vegeUsed FROM data");

            database.execSQL("DROP TABLE data");

            database.execSQL("ALTER TABLE new_data RENAME TO data");
        }
    };
*/
    public static synchronized UserDatabase getUserDatabase(Context context) {
        if (userDatabase == null) {
        // Migration build
/*            userDatabase = Room.databaseBuilder(context, UserDatabase.class, dbName)
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5).build();
*/
            // Original build
            userDatabase = Room.databaseBuilder(context, UserDatabase.class, dbName)
                    .fallbackToDestructiveMigration().build();

        }
        return userDatabase;
    }

    // DAO definitions
    public abstract UserDao userDao();
    public abstract DataDao dataDao();

}
