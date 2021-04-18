package com.harkka.livegreen.entry;

import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserProfile;

import java.util.ArrayList;
import java.util.UUID;

public class EntryManager {

    //Entry storage;
    public Entry entry;

    public static EntryManager entryManager = new EntryManager(); // Singleton!!!

    public static EntryManager getInstance() {
        return entryManager;
    } // Singleton!!!

    private EntryManager() {}

    //Todo: Necessary methods for Main - to be decided

    public Entry createEntry(UUID uGuid) {

        entry = new Entry(uGuid);

        return entry;
    }
}
