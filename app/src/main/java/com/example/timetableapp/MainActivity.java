package com.example.timetableapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listview;
    private RelativeLayout layout;
    private MyRoomDB db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        db = Room.databaseBuilder(
                this,
                MyRoomDB.class, "my-room-db"
        ).build();

        executor.submit(() -> {
            DayDao dayDao = db.dayDao();
            if(dayDao.isEmpty())
            {
                SetupDB setupObj = new SetupDB();
                setupObj.setup(this, dayDao);
            }

            TaskDao taskDao = db.taskDao();
            taskDao.deleteNullTasks();
        });

        executor.shutdown();

        layout = (RelativeLayout) findViewById(R.id.main_layout);

        //Very important, that setupUI executes first, so TB gets initialised
        setupUI();
        setupToolBar();
        setupListView();
    }

    private void setupUI(){
        toolbar = (Toolbar)findViewById(R.id.ToolbarMain);
        listview = (ListView) findViewById(R.id.lv_main);
    }

    private void setupToolBar(){
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Organizer");
        }
    }

    private void setupListView(){
        String[] titles = getResources().getStringArray(R.array.titles);
        String[] descriptions = getResources().getStringArray(R.array.descriptions);

        Adapter adapter = new Adapter(this, titles, descriptions);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: {
                        Intent intent = new Intent(MainActivity.this, WeekActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        break;
                    }
                }
            }
        });
    }
}