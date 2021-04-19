package com.harkka.livegreen.roomdb;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

public interface DataDao {

    // Basic db functions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDataEntities(DataEntity... dataEntities);

    @Insert
    public void insertDataEntity(DataEntity dataEntity);

    @Update
    public void updateDataEntities(DataEntity... dataEntities);

    @Delete
    public void deleteDataEntities(DataEntity... dataEntities);

    // Queries

    @Query("SELECT * FROM data")
    public DataEntity[] loadAllDataEntities();

    @Query("SELECT * FROM data WHERE userId = :userId")
    public DataEntity[] loadAllDataEntitiesByUserId(String userId);

    @Query("SELECT * FROM data WHERE entryId = :entryId")
    public DataEntity[] loadAllDataEntitiesByEntryId(String entryId);

}
