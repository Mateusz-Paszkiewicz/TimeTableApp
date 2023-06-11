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

    @Query("SELECT * FROM Tasks WHERE day_id = :day_num AND name = :name AND time = :time")
    List<Task> getTasksByDayNameTime(int day_num,String name, String time);

    @Query("DELETE FROM Tasks WHERE day_id = :day_num AND name = :name AND time = :time")
    void deleteTaskByDayNameAndTime(int day_num,String name, String time);

    @Query("DELETE FROM Tasks WHERE name IS NULL OR time IS NULL")
    void deleteNullTasks();

    @Query("UPDATE Tasks SET name = :newSubject, time = :newTime WHERE day_id = :dayId AND name = :oldSubject AND time = :oldTime")
    void updateTask(int dayId, String oldSubject, String oldTime, String newSubject, String newTime);

    @Query("DELETE FROM Tasks")
    void deleteAll();
}

