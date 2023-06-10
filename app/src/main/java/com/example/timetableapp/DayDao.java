package com.example.timetableapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface DayDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertDay(Day Day);

    @Query("SELECT * FROM Days WHERE id=:id")
    Day getDayById(int id);

    @Query("DELETE FROM Days")
    void deleteAll();

    @Query("SELECT (SELECT COUNT(*) FROM Days) == 0")
    boolean isEmpty();
}
