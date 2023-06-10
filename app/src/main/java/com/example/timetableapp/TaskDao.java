package com.example.timetableapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertTask(Task task);

    @Query("SELECT * FROM Tasks WHERE id=:id")
    Task getTaskById(int id);

    @Query("SELECT * FROM Tasks WHERE day_id=:dayId")
    List<Task> getTasksByDayId(int dayId);

    @Query("DELETE FROM Tasks")
    void deleteAll();
}

