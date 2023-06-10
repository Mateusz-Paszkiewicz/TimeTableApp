package com.example.timetableapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "Tasks",
        foreignKeys = @ForeignKey(entity = Day.class,
                parentColumns = "id",
                childColumns = "day_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("day_id")})
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "day_id")
    private int day_id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "time")
    private String time;

    public Task(int day_id, String name, String time) {
        this.day_id = day_id;
        this.name = name;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay_id() {
        return day_id;
    }

    public void setDay_id(int day_id) {
        this.day_id = day_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}