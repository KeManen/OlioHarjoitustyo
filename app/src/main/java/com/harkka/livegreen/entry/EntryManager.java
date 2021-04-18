package com.harkka.livegreen.entry;

import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.ui.testi.TestFragment;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserProfile;

import java.util.ArrayList;
import java.util.UUID;

public class EntryManager {

    public enum Entrytype {WEIGHT, HEIGHT, DAIRY, MEAT, VEGE, OTHER}

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

    public void setEntryValue( int entryType, float newEntry ) {


        // Todo: in here entry type switch using ENUM
        switch(entryType){
            case 0:
                entryManager.entry.setWeightEntry(newEntry);
                entryManager.entry.insertEntry(entryType);
                break;
            case 1:
                entryManager.entry.setHeightEntry(newEntry);
                entryManager.entry.insertEntry(entryType);
                break;
            case 2:
                entryManager.entry.setDairyConsumption(newEntry);
                entryManager.entry.insertEntry(entryType);
                break;
            case 3:
                entryManager.entry.setMeatConsumption(newEntry);
                entryManager.entry.insertEntry(entryType);
                break;
            case 4:
                entryManager.entry.setVegeConsumption(newEntry);
                entryManager.entry.insertEntry(entryType);
            default:
                break;
        }
    }
}
