package com.harkka.livegreen.roomdb;

import androidx.annotation.RequiresPermission;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.harkka.livegreen.entry.EntryManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity(tableName = "data")
public class DataEntity {

    public static DataEntity dataEntity = new DataEntity(); // Singleton!!!

    public static DataEntity getInstance() {
        return dataEntity;
    } // Singleton!!!

    // uid tahan?
    @PrimaryKey(autoGenerate = true)
    Integer id;

    // add needed data components to store with the uid
    // daily data inputs

    @ColumnInfo(name = "userId")
    String userId;

    @ColumnInfo(name = "entryId")
    String entryId;

    @ColumnInfo(name = "datetime")
    String dateTime;

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

    @ColumnInfo(name = "totalResult")
    String totalResult;

    // adding getters + setters

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

     // Room cannot handle UUID -> conversions to String and back
    public UUID getUserId() {
        return UUID.fromString(userId);
    }

    public void setUserId(UUID uId) {
        this.userId = uId.toString();
        System.out.println(" DataEntity UserID: " + this.userId);
    }

    // Room cannot handle UUID -> conversions to String and back
    public UUID getEntryId() {
        return UUID.fromString(entryId);
    }

    public void setEntryId(UUID eId) {
        this.entryId = eId.toString();
        System.out.println(" DataEntity EntryID: " + this.entryId);
    }

    public LocalDateTime getDateTime() {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        LocalDateTime localDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            localDateTime = LocalDateTime.parse(dateTime, formatter);
        }
        return localDateTime;
    }

    public void setDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime.toString();
    }

    public String getWeight() { return weight; }

    public void setWeight(String weight) { this.weight = weight; System.out.println(" DataEntity Weigth " + this.weight); }

    public String getHeight() { return height; }

    public void setHeight(String height) { this.height = height; }

    public String getDairyUsed() { return dairyUsed; }

    public void setDairyUsed(String dairyUsed) { this.dairyUsed = dairyUsed; }

    public String getMeatUsed() { return meatUsed; }

    public void setMeatUsed(String meatUsed) { this.meatUsed = meatUsed; }

    public String getVegeUsed() { return vegeUsed; }

    public void setVegeUsed(String vegeUsed) { this.vegeUsed = vegeUsed; }

    public String getTotalResult() { return totalResult; }

    public void setTotalResult(String totalResult) { this.totalResult = totalResult; }

}
