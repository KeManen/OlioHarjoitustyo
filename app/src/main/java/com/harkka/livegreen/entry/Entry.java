package com.harkka.livegreen.entry;

import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Entry {

    private String classString = "Entry Class: ";

    private LocalDateTime entryDateTime;
    private UUID userGuid;
    private UUID entryGuid;
    private float weightEntry;
    private float heightEntry;
    private float dairyConsumption;
    private float meatConsumption;
    private float vegeConsumption;

    public Entry(UUID uGuid) {
        entryDateTime = LocalDateTime.now();
        userGuid = uGuid;
        entryGuid = getGuid();

        System.out.println(classString + entryDateTime + " User Guid: " + userGuid + " Entry Guid: " + entryGuid);
    }

    // Get methods

    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public float getWeightEntry() {
        return weightEntry;
    }

    public float getHeightEntry() {
        return heightEntry;
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

    // Set methods

    public void setEntryDateTime(LocalDateTime localDateTime) {
        this.entryDateTime = localDateTime;
        System.out.println(classString + "localDateTime set to " + localDateTime);
    }

    public void setHeightEntry(float heightEntry) {
        this.heightEntry = heightEntry;
        System.out.println(classString + "heightEntry set to " + heightEntry);
    }

    public void setWeightEntry(float weightEntry) {
        this.weightEntry = weightEntry;
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

    // Entry management

    // TODO: This is db interface method
    // Load all entries of a user
    public void loadUserEntries(UUID userGuid) {}

    // TODO: This is db interface method
    // Load selected entry
    public void loadEntry(UUID entryGuid) {}

    // TODO: This is db interface method
    // Makes db insert of current entry set by EntryManager.setEntryValue()
    public void insertEntry(int entryType) {
        DataEntity dataEntity = new DataEntity();
        // Todo: in here entry type switch using ENUM
        switch(entryType){
            case 0:
                dataEntity.setWeight(String.valueOf(weightEntry));
                System.out.println(classString + "Weight " + weightEntry + " with guid " + entryGuid + " inserted in database!");
                break;
            case 1:
                System.out.println(classString + "Height " + heightEntry + " with guid " + entryGuid + " inserted in database!");
                break;
            case 2:
                System.out.println(classString + "dairyConsumption " + dairyConsumption + " with guid " + entryGuid + " inserted in database!");
                break;
            case 3:
                System.out.println(classString + "meatConsumption " + meatConsumption + " with guid " + entryGuid + " inserted in database!");
                break;
            case 4:
                System.out.println(classString + "vegeConsumption " + vegeConsumption + " with guid " + entryGuid + " inserted in database!");
            default:
                break;
        }

    }

    // Aux methods

    private UUID getGuid() {
        UUID guid = UUID.randomUUID();
        System.out.println("GetGuid()/Guid: " + guid);
        return guid;
    }
}
