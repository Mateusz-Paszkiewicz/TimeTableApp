package com.example.timetableapp;

import android.content.Context;

public class SetupDB {
    void setup(Context context , DayDao dayDao){
        String[] week = context.getResources().getStringArray(R.array.Week);
        Day monday = new Day(0, week[0]);
        Day tuesday = new Day(1, week[1]);
        Day wednesday = new Day(2, week[2]);
        Day thursday = new Day(3, week[3]);
        Day friday = new Day(4, week[4]);
        Day saturday = new Day(5, week[5]);
        Day sunday = new Day(6, week[6]);

        dayDao.insertDay(monday);
        dayDao.insertDay(tuesday);
        dayDao.insertDay(wednesday);
        dayDao.insertDay(thursday);
        dayDao.insertDay(friday);
        dayDao.insertDay(saturday);
        dayDao.insertDay(sunday);
    }
}
