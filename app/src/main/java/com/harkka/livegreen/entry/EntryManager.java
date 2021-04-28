package com.harkka.livegreen.entry;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.harkka.livegreen.roomdb.UserEntity;
import com.harkka.livegreen.ui.testi.TestFragment;
import com.harkka.livegreen.user.User;
import com.harkka.livegreen.user.UserProfile;

import java.util.ArrayList;
import java.util.UUID;

// Handler class for data entries
public class EntryManager {

    public enum Entrytype {WEIGHT, HEIGHT, DAIRY, MEAT, VEGE, OTHER}
    private String classString = "EntryManager: ";

    //Entry storage;
    public Entry entry;

    public static EntryManager entryManager = new EntryManager(); // Singleton!!!

    public static EntryManager getInstance() {
        return entryManager;
    } // Singleton!!!

    private EntryManager() {}

    //Todo: Necessary methods for Main - to be decided

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Entry createEntry(UUID uGuid) {

        entry = new Entry(uGuid);

        return entry;
    }

    public void setEntry(Entry entry) {
        if (!entry.equals(null)) {
            System.out.println(classString + "in setEntry");
        }
    }

    // Get current entry
    public Entry getEntry() {
        System.out.println(classString + "in getEntry");
        return entry.getCurrentEntry();
    }

    // Setting single entry value into Entry class and for DB insert in DataEntity class
    public void setEntryValue( int entryType, float newEntry ) {
        // Todo: in here entry type switch using ENUM
        switch(entryType){
            case 0:
                entry.setWeight(newEntry);
                entryManager.entry.insertDBEntry(entryType);
                break;
            case 1:
                entryManager.entry.setHeight(newEntry);
                entryManager.entry.insertDBEntry(entryType);
                break;
            case 2:
                entryManager.entry.setDairyConsumption(newEntry);
                entryManager.entry.insertDBEntry(entryType);
                break;
            case 3:
                entryManager.entry.setMeatConsumption(newEntry);
                entryManager.entry.insertDBEntry(entryType);
                break;
            case 4:
                entryManager.entry.setVegeConsumption(newEntry);
                entryManager.entry.insertDBEntry(entryType);
                break;
            case 5:
                entryManager.entry.setTotalResult(newEntry);
                entryManager.entry.insertDBEntry(entryType);
                break;
            default:
                break;
        }
    }

    public float getEntryValue(int entryType) {
        // Todo: in here entry type switch using ENUM
        float ret = 0;
        switch(entryType){
            case 0:
                ret = entryManager.entry.getWeight();
                break;
            case 1:
                ret = entryManager.entry.getHeight();
                break;
            case 2:
                ret = entryManager.entry.getDairyConsumption();
                break;
            case 3:
                ret = entryManager.entry.getMeatConsumption();
                break;
            case 4:
                ret = entryManager.entry.getVegeConsumption();
                break;
            case 5:
                ret = entryManager.entry.getTotalResult();
                break;
            default:
                break;
        }
        return ret;
    }
}
