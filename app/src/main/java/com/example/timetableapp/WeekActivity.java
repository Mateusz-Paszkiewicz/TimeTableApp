package com.example.timetableapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class WeekActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listview;
    public static SharedPreferences sharedPreferences;
    public static String SET_DAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        setupUI();
        setupToolBar();
        setupListView();
    }

    private void setupUI(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarWeek);
        listview = (ListView)findViewById(R.id.lv_week);
        sharedPreferences = getSharedPreferences("DAY", MODE_PRIVATE);
    }

    private void setupToolBar(){
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Week table");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupListView(){
        String[] week = getResources().getStringArray(R.array.Week);

        WeekAdapter weekAdapter = new WeekAdapter(this, R.layout.activity_week_single_item, week);

        listview.setAdapter(weekAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0: {
                        startActivity(new Intent(WeekActivity.this, DayActivity.class));

                        sharedPreferences.edit().putString(SET_DAY, "Monday").apply();
                        break;
                    }
                    case 1: {
                        startActivity(new Intent(WeekActivity.this, DayActivity.class));

                        sharedPreferences.edit().putString(SET_DAY, "Tuesday").apply();
                        break;
                    }
                    case 2: {
                        startActivity(new Intent(WeekActivity.this, DayActivity.class));

                        sharedPreferences.edit().putString(SET_DAY, "Wednesday").apply();
                        break;
                    }
                    case 3: {
                        startActivity(new Intent(WeekActivity.this, DayActivity.class));

                        sharedPreferences.edit().putString(SET_DAY, "Thursday").apply();
                        break;
                    }
                    case 4: {
                        startActivity(new Intent(WeekActivity.this, DayActivity.class));

                        sharedPreferences.edit().putString(SET_DAY, "Friday").apply();
                        break;
                    }
                    case 5: {
                        startActivity(new Intent(WeekActivity.this, DayActivity.class));

                        sharedPreferences.edit().putString(SET_DAY, "Saturday").apply();
                        break;
                    }
                    case 6: {
                        startActivity(new Intent(WeekActivity.this, DayActivity.class));

                        sharedPreferences.edit().putString(SET_DAY, "Sunday").apply();
                        break;
                    }
                    default: break;
                }
            }
        });
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