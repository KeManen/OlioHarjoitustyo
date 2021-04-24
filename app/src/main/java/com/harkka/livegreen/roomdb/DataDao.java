package com.harkka.livegreen.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DataDao {

    // Basic db functions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDataEntities(DataEntity... dataEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDataEntity(DataEntity dataEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    public void updateDataEntities(DataEntity... dataEntities);

    @Delete
    public void deleteDataEntities(DataEntity... dataEntities);

    // Queries

    @Query("SELECT * FROM data")
    public DataEntity[] loadAllDataEntities();

    @Query("SELECT * FROM data WHERE userId = :userId")
    public DataEntity[] loadAllDataEntitiesByUserId(String userId);

    @Query("SELECT * FROM data WHERE entryId = :entryId")
    public DataEntity[] loadDataEntityByEntryId(String entryId);

}
