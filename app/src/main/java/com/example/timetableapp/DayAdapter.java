package com.example.timetableapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.timetableapp.Utils.LetterImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DayAdapter extends BaseAdapter {
    List<String> _subjects;
    List<String> _times;
    private Context _context;
    private LayoutInflater _layoutInflater;
    private ImageView _editButton, _deleteButton;
    private LetterImageView _letterImageView;
    private TextView _subject, _time;
    private RoomDatabase _db=null;
    private int _dayNumber;
    private TaskDao _taskDao;

    public DayAdapter(Context context, RoomDatabase roomDatabase, TaskDao taskDao , int dayNumber , String[] title, String[] time){
        _context = context;
        _subjects = new ArrayList<>(Arrays.asList(title));
        _times = new ArrayList<>(Arrays.asList(time));
        _db = roomDatabase;
        _dayNumber = dayNumber;
        _taskDao = taskDao;


        _db = Room.databaseBuilder(
                context,
                MyRoomDB.class, "my-room-db"
        ).build();

        _layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _subjects.size();
    }

    @Override
    public Object getItem(int i) {
        return _subjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = _layoutInflater.inflate(R.layout.day_activity_single_item, null);
        }

        _subject = (TextView)view.findViewById(R.id.tv_day);
        _time = (TextView)view.findViewById(R.id.tv_timer);
        _letterImageView = (LetterImageView)view.findViewById(R.id.iv_DayLetter);
        _editButton = (ImageView)view.findViewById(R.id.buttonEdit);
        _deleteButton = (ImageView)view.findViewById(R.id.buttonDelete);

        if(!_subjects.get(i).isEmpty())
            _subject.setText(_subjects.get(i));

        if(!_times.get(i).isEmpty())
            _time.setText(_times.get(i));

        _letterImageView.setOval(true);

        if(!_subjects.get(i).isEmpty()) {
            _letterImageView.setLetter(_subjects.get(i).charAt(0));
        }

        _editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldSubject = _subjects.get(i);
                String oldTime = _times.get(i);

                showEditTaskDialog(oldSubject, oldTime, new EditTaskDialogListener() {
                    @Override
                    public void onTaskEdited(String subject, String time) {
                        _subjects.set(i, subject);
                        _times.set(i, time);

                        notifyDataSetChanged();

                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        executor.submit(() -> {
                            _taskDao.updateTask(_dayNumber, oldSubject, oldTime, subject, time);
                            executor.shutdown();
                        });
                    }
                });
            }
        });

        _deleteButton.setOnClickListener(new View.OnClickListener() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            @Override
            public void onClick(View v) {
                String subject = _subjects.get(i);
                String time = _times.get(i);

                _subjects.remove(i);
                _times.remove(i);
                notifyDataSetChanged();

                executor.submit(() -> {
                    _taskDao.deleteTaskByDayNameAndTime(_dayNumber, subject, time);
                });

                executor.shutdown();
            }
        });

        return view;
    }

    private void showEditTaskDialog(String currentSubject, String currentTime, EditTaskDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        View dialogView = LayoutInflater.from(_context).inflate(R.layout.dialog_edit_task, null);
        EditText editTextSubject = dialogView.findViewById(R.id.editTextSubject);
        EditText editTextTime = dialogView.findViewById(R.id.editTextTime);

        // Set the starting text for the EditText fields
        editTextSubject.setText(currentSubject);
        editTextTime.setText(currentTime);

        builder.setView(dialogView)
                .setTitle("Edit Task")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String editedSubject = editTextSubject.getText().toString();
                        String editedTime = editTextTime.getText().toString();

                        // Pass the edited values back to the listener
                        listener.onTaskEdited(editedSubject, editedTime);

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
}


