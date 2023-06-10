package com.example.timetableapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Day.class, Task.class}, version = 1, exportSchema = false)
public abstract class MyRoomDB extends RoomDatabase {
    public abstract DayDao dayDao();
    public abstract TaskDao taskDao();
}

