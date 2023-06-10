package com.example.timetableapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Days")
public class Day {
    @PrimaryKey()
    private int id;
    @ColumnInfo(name = "day_string")
    private String day_string;

    public Day(int id, String day_string) {
        this.id = id;
        this.day_string = day_string;
    }

    public int getId() {
        return id;
    }

    public String getDay_string() {
        return day_string;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDay_string(String day_string) {
        this.day_string = day_string;
    }
}