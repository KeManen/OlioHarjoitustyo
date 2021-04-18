package com.harkka.livegreen.roomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "data")
public class DataEntity {

    // uid tahan?
    @PrimaryKey(autoGenerate = true)
    Integer id;

    // add needed data components to store with the uid
    // daily data inputs
/*
    @ColumnInfo(name = "userId")
    String userId;

    @ColumnInfo(name = "entryId")
    String entryId;
*/
    @ColumnInfo(name = "weight")
    String weight;

    @ColumnInfo(name = "height")
    String height;

    @ColumnInfo(name = "dairyUsed")
    String dairyUsed;

    @ColumnInfo(name = "meatUsed")
    String meatUsed;

    @ColumnInfo(name = "vegeUsed")
    String vegeUsed;

    // adding getters + setters

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    /*
    // Room cannot handle UUID -> conversions to String and back
    public UUID getUserId() {
        return UUID.fromString(userId);
    }

    public void setUserId(UUID userId) {
        this.userId = userId.toString();
    }

    // Room cannot handle UUID -> conversions to String and back
    public UUID getEntryId() {
        return UUID.fromString(entryId);
    }

    public void setEntryId(UUID entryId) {
        this.entryId = entryId.toString();
    }

 */
    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; }

    public String getHeight() { return height; }

    public void setHeight(String height) { this.height = height; }

    public String getDairyUsed() { return dairyUsed; }

    public void setDairyUsed(String dairyUsed) { this.dairyUsed = dairyUsed; }

    public String getMeatUsed() { return meatUsed; }

    public void setMeatUsed(String meatUsed) { this.meatUsed = meatUsed; }

    public String getVegeUsed() { return vegeUsed; }

    public void setVegeUsed(String vegeUsed) { this.vegeUsed = vegeUsed; }

}
