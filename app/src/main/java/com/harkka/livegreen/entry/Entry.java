package com.harkka.livegreen.entry;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;
import com.harkka.livegreen.roomdb.UserDao;

import java.time.LocalDateTime;
import java.util.UUID;

// Data entry class for user input data
public class Entry {

    private String classString = "Entry Class: ";

    public Entry entry;
    private LocalDateTime entryDateTime;
    private UUID userGuid;
    private UUID entryGuid;
    private float weight;
    private float height;
    private float dairyConsumption;
    private float meatConsumption;
    private float vegeConsumption;
    private float totalResult;

    public Entry() {}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Entry(UUID uGuid) {
        entry = new Entry();

        entryDateTime = LocalDateTime.now();
        System.out.println(entryDateTime);
        System.out.println(entryDateTime.toString());
        userGuid = uGuid;
        entryGuid = getGuid();

        System.out.println(classString + entryDateTime + " User Guid: " + userGuid + " Entry Guid: " + entryGuid);
    }

    // Get methods

    public Entry getCurrentEntry() {
        return entry;
    }

    public UUID getUserGuid() { return userGuid; }

    public UUID getEntryGuid() { return entryGuid; }

    public LocalDateTime getDateTime() {
        return entryDateTime;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public float getDairyConsumption() {
        return dairyConsumption;
    }

    public float getMeatConsumption() {
        return meatConsumption;
    }

    public float getVegeConsumption() {
        return vegeConsumption;
    }

    public float getTotalResult() {
        return totalResult;
    }

    // Set methods

    public void setUserGuid(UUID uGuid) { this.userGuid = uGuid; }

    public void setEntryGuid(UUID eGuid){ this.entryGuid = eGuid;}

    public void setDateTime(LocalDateTime localDateTime) {
        this.entryDateTime = localDateTime;
        System.out.println(classString + "localDateTime set to " + localDateTime);
    }

    public void setHeight(float heightEntry) {
        this.height = heightEntry;
        System.out.println(classString + "heightEntry set to " + heightEntry);
    }

    public void setWeight(float weightEntry) {
        this.weight = weightEntry;
        System.out.println(classString + "weightEntry set to " + weightEntry);
    }

    public void setDairyConsumption(float dairyConsumption) {
        this.dairyConsumption = dairyConsumption;
        System.out.println(classString + "dairyConsumption set to " + dairyConsumption);
    }

    public void setMeatConsumption(float meatConsumption) {
        this.meatConsumption = meatConsumption;
        System.out.println(classString + "meatConsumption set to " + meatConsumption);
    }

    public void setVegeConsumption(float vegeConsumption) {
        this.vegeConsumption = vegeConsumption;
        System.out.println(classString + "vegeConsumption set to " + vegeConsumption);
    }

    public void setTotalResult(float totalResult) {
        this.totalResult = totalResult;
        System.out.println(classString + "totalResult set to " + totalResult);
    }

    // Entry management
/*
    // Load all entries of a user
    public Entry[] loadUserEntries(UUID userGuid) {
        //DataEntity dataEntity = DataEntity.getInstance();
        DataEntity dataEntries[];
        Entry entry = new Entry();
        Entry entries[];
        int i = 0;

        for (DataEntity entity : dataEntries = DataDao.loadAllDataEntitiesByUserId(userGuid)) {
            entry[i].userGuid = entity.getUserId();
            entry[i].userGuid = entity.getEntryId();
            entry[i].userGuid = entity.getDateTime();
            entry[i].userGuid = Float.parseFloat(entity.getWeight());
            entry[i].userGuid = Float.parseFloat(entity.getHeight());
            entry[i].userGuid = Float.parseFloat(entity.getDairyUsed());
            entry[i].userGuid = Float.parseFloat(entity.getMeatUsed());
            entry[i].userGuid = Float.parseFloat(entity.getVegeUsed());
            entry[i].userGuid = Float.parseFloat(entity.getTotalResult());
            i++;
        };

        return entries;
    }


    // Load selected entry
    public Entry[] loadEntry(UUID entryGuid) {
        DataEntity dataEntity = DataEntity.getInstance();
    };
*/

    // Makes data insert into DataEntity class for DB insert
    public void insertDBEntry() {
        DataEntity dataEntity = DataEntity.getInstance();

        System.out.println(classString + " - insertDBEntry");
        System.out.println(classString + " - userGuid: " + userGuid);
        System.out.println(classString + " - entryGuid: " + entryGuid);
        System.out.println(classString + " - DateTime: " + entryDateTime);
        System.out.println(classString + " - meatConsumption: " + meatConsumption);
        System.out.println(classString + " - totalResult: " + totalResult);

        dataEntity.setUserId(userGuid);
        dataEntity.setEntryId(entryGuid);
        dataEntity.setDateTime(entryDateTime);
        dataEntity.setWeight(String.valueOf(weight));
        dataEntity.setHeight(String.valueOf(height));
        dataEntity.setDairyUsed(String.valueOf(dairyConsumption));
        dataEntity.setMeatUsed(String.valueOf(meatConsumption));
        dataEntity.setVegeUsed(String.valueOf(vegeConsumption));
        dataEntity.setTotalResult(String.valueOf(totalResult));
    }


    // Makes db insert of current entry set by EntryManager.setEntryValue()
    // Entry types:
    // 0: Weight
    // 1: Height
    // 2: Dairy consumption
    // 3: Meat consumption
    // 4: Vegetables consumption
    // 5: Total calculated consumption

    // Data entry for DataEntity class for DB insert
    public void insertDBEntry(int entryType) {
        DataEntity dataEntity = DataEntity.getInstance();

        switch(entryType){
            case 0:
                dataEntity.setWeight(String.valueOf(weight));
                System.out.println(classString + "Weight " + weight + " with guid " + entryGuid + " inserted in database!");
                break;
            case 1:
                dataEntity.setHeight(String.valueOf(height));
                System.out.println(classString + "Height " + height + " with guid " + entryGuid + " inserted in database!");
                break;
            case 2:
                dataEntity.setDairyUsed(String.valueOf(dairyConsumption));
                System.out.println(classString + "dairyConsumption " + dairyConsumption + " with guid " + entryGuid + " inserted in database!");
                break;
            case 3:
                dataEntity.setMeatUsed(String.valueOf(meatConsumption));
                System.out.println(classString + "meatConsumption " + meatConsumption + " with guid " + entryGuid + " inserted in database!");
                break;
            case 4:
                dataEntity.setVegeUsed(String.valueOf(vegeConsumption));
                System.out.println(classString + "vegeConsumption " + vegeConsumption + " with guid " + entryGuid + " inserted in database!");
            case 5:
                dataEntity.setTotalResult(String.valueOf(totalResult));
                System.out.println(classString + "totalResult " + totalResult + " with guid " + entryGuid + " inserted in database!");
                break;
            default:
                break;
        }

    }

    // Aux methods
    // Create a new entry guid
    private UUID getGuid() {
        UUID guid = UUID.randomUUID();
        System.out.println(classString + " GetGuid()/Guid: " + guid);
        return guid;
    }
}
