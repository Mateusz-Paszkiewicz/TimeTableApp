package com.example.timetableapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DayActivity extends AppCompatActivity {

    private List<Task> tasks;
    private List<String> dayList;
    private List<String> stringTaskList = new ArrayList<>();
    private List<String> stringTimeList = new ArrayList<>();
    private MyRoomDB db = null;
    private int dayNumber;
    private ListView listview;
    private Toolbar toolbar;
    private ImageView addButton;

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
        addButton = (ImageView)findViewById(R.id.buttonAdd);
    }

    private void setupToolBar(){
        if(toolbar != null){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(WeekActivity.sharedPreferences.getString(WeekActivity.SET_DAY, null));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        }
    }

    private void setupListView(){
        String selected_day = WeekActivity.sharedPreferences.getString(WeekActivity.SET_DAY, null);

        dayList = Arrays.asList(getResources().getStringArray(R.array.Week));
        dayNumber = dayList.indexOf(selected_day);

        ExecutorService executorPull = Executors.newSingleThreadExecutor();

        executorPull.submit(() -> {
            TaskDao taskDao = db.taskDao();
            tasks = taskDao.getTasksByDayId(dayNumber);
        });

        executorPull.shutdown();

        try {
            executorPull.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
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
                this, db,  db.taskDao(), dayNumber,
                (stringTaskList != null) ? stringTaskList.toArray(new String[stringTimeList.size()]) : new String[0],
                (stringTimeList != null) ? stringTimeList.toArray(new String[stringTimeList.size()]) : new String[0]
        );

        addButton.setOnClickListener(new View.OnClickListener() {
            ExecutorService executor = Executors.newSingleThreadExecutor();

            @Override
            public void onClick(View v) {
                showAddTaskDialog(new AddTaskDialogListener() {
                    @Override
                    public void onTaskAdded(String name, String time) {
                        Task task = new Task(dayNumber, name, time);
                        TaskDao taskDao = db.taskDao();

                        executor.submit(() -> {
                            if(taskDao.getTasksByDayNameTime(dayNumber, name, time).isEmpty()) {
                                taskDao.insertTask(task);

                                executor.shutdown();

                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "This activity already exists", 2*Toast.LENGTH_SHORT).show();
                                executor.shutdown();
                            }
                        });
                    }
                });
            }
        });

        listview.setAdapter(dayAdapter);
    }

    private void showAddTaskDialog(AddTaskDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_task, null);
        EditText editTextSubject = dialogView.findViewById(R.id.editTextSubject);
        EditText editTextTime = dialogView.findViewById(R.id.editTextTime);

        builder.setView(dialogView)
                .setTitle("New Task")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editedSubject = editTextSubject.getText().toString();
                        String editedTime = editTextTime.getText().toString();

                        // Pass the edited values back to the listener
                        listener.onTaskAdded(editedSubject, editedTime);

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
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

