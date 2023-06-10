package com.example.timetableapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DayActivity extends AppCompatActivity {

    //    private String[] prefferedDay;
    //    private String[] prefferedTimer;
    private List<Task> tasks;
    private List<String> dayList;
    private List<String> stringTaskList = new ArrayList<>();
    private List<String> stringTimeList = new ArrayList<>();
    private MyRoomDB db = null;
    private int dayNumber;
    private ListView listview;
    private Toolbar toolbar;

//    public static String[] Monday;
//    public static String[] Tuesday;
//    public static String[] Wednesday;
//    public static String[] Thursday;
//    public static String[] Friday;
//    public static String[] Saturday;
//    public static String[] Sunday;
//
//    public static String[] MondayTimer;
//    public static String[] TuesdayTimer;
//    public static String[] WednesdayTimer;
//    public static String[] ThursdayTimer;
//    public static String[] FridayTimer;
//    public static String[] SaturdayTimer;
//    public static String[] SundayTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        db = Room.databaseBuilder(
                this,
                MyRoomDB.class, "my-room-db"
        ).build();

        setupUI();
        setupToolBar();
        setupListView();
    }

    private void setupUI(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarDay);
        listview = (ListView)findViewById(R.id.lv_day);
    }

    private void setupToolBar(){
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(WeekActivity.sharedPreferences.getString(WeekActivity.SET_DAY, null));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupListView(){
        String selected_day = WeekActivity.sharedPreferences.getString(WeekActivity.SET_DAY, null);

        dayList = Arrays.asList(getResources().getStringArray(R.array.Week));
        dayNumber = dayList.indexOf(selected_day);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            DayDao dayDao = db.dayDao();
            String test = dayDao.getDayById(0).getDay_string();

            TaskDao taskDao = db.taskDao();
            tasks = taskDao.getTasksByDayId(dayNumber);
        });

//        Monday = getResources().getStringArray(R.array.Monday);
//        Tuesday = getResources().getStringArray(R.array.Tuesday);
//        Wednesday = getResources().getStringArray(R.array.Wednesday);
//        Thursday = getResources().getStringArray(R.array.Thursday);
//        Friday = getResources().getStringArray(R.array.Friday);
//        Saturday = getResources().getStringArray(R.array.Saturday);
//        Sunday = getResources().getStringArray(R.array.Sunday);
//
//
//        MondayTimer = getResources().getStringArray(R.array.MondayTimer);
//        TuesdayTimer = getResources().getStringArray(R.array.TuesdayTimer);
//        WednesdayTimer = getResources().getStringArray(R.array.WednesdayTimer);
//        ThursdayTimer = getResources().getStringArray(R.array.ThursdayTimer);
//        FridayTimer = getResources().getStringArray(R.array.FridayTimer);
//        SaturdayTimer = getResources().getStringArray(R.array.SaturdayTimer);
//        SundayTimer = getResources().getStringArray(R.array.SundayTimer);

//        if(selected_day.equalsIgnoreCase("Monday")){
//            prefferedDay = Monday;
//            prefferedTimer = MondayTimer;
//        } else if(selected_day.equalsIgnoreCase("Tuesday")){
//            prefferedDay = Tuesday;
//            prefferedTimer = TuesdayTimer;
//        } else if(selected_day.equalsIgnoreCase("Wednesday")){
//            prefferedDay = Wednesday;
//            prefferedTimer = WednesdayTimer;
//        } else if(selected_day.equalsIgnoreCase("Thursday")){
//            prefferedDay = Thursday;
//            prefferedTimer = ThursdayTimer;
//        } else if(selected_day.equalsIgnoreCase("Friday")) {
//            prefferedDay = Friday;
//            prefferedTimer = FridayTimer;
//        } else if(selected_day.equalsIgnoreCase("Saturday")) {
//            prefferedDay = Saturday;
//            prefferedTimer = SaturdayTimer;
//        } else{
//            prefferedDay = Sunday;
//            prefferedTimer = SundayTimer;
//        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(tasks != null) {
            for (Task task : tasks) {
                stringTaskList.add(task.getName());
                stringTimeList.add(task.getTime());
            }
        }

        DayAdapter dayAdapter = new DayAdapter(
                this,
                (stringTaskList != null) ? stringTaskList.toArray(new String[stringTimeList.size()]) : new String[0],
                (stringTimeList != null) ? stringTimeList.toArray(new String[stringTimeList.size()]) : new String[0]
        );

        listview.setAdapter(dayAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}