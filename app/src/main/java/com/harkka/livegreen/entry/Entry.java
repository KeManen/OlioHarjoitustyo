package com.harkka.livegreen.entry;

import com.harkka.livegreen.roomdb.DataDao;
import com.harkka.livegreen.roomdb.DataEntity;

import java.time.LocalDateTime;
import java.util.UUID;

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

    public Entry() {}

    public Entry(UUID uGuid) {
        entry = new Entry();

        entryDateTime = LocalDateTime.now(); // oisko korjaus että sais toimimaan nykyisellä apitasolla
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

    // Set methods

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

    // Entry management

    // TODO: This is db interface method
    // Load all entries of a user
    public void loadUserEntries(UUID userGuid) {}

    // TODO: This is db interface method
    // Load selected entry
    public void loadEntry(UUID entryGuid) {}

    // TODO: This is db interface method
    // Makes db insert of entry
    public void insertDBEntry() {
        //DataEntity dataEntity = new DataEntity();
        DataEntity dataEntity = DataEntity.getInstance();
        
        dataEntity.setUserId(userGuid);
        dataEntity.setEntryId(entryGuid);
        dataEntity.setDateTime(entryDateTime);
        dataEntity.setWeight(String.valueOf(weight));
        dataEntity.setHeight(String.valueOf(height));
        dataEntity.setDairyUsed(String.valueOf(dairyConsumption));
        dataEntity.setMeatUsed(String.valueOf(meatConsumption));
        dataEntity.setVegeUsed(String.valueOf(vegeConsumption));

    }

    // TODO: This is db interface method
    // Makes db insert of current entry set by EntryManager.setEntryValue()
    public void insertDBEntry(int entryType) {
        DataEntity dataEntity = new DataEntity();
        // Todo: in here entry type switch using ENUM
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
            default:
                break;
        }

    }

    // Aux methods

    private UUID getGuid() {
        UUID guid = UUID.randomUUID();
        System.out.println(classString + " GetGuid()/Guid: " + guid);
        return guid;
    }
}
