package com.example.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timetableapp.Utils.LetterImageView;

public class DayAdapter extends BaseAdapter {
    private Context _context;
    private LayoutInflater _layoutInflater;
    private String[] _subjectStrings;
    private String[] _timeStrings;
    private LetterImageView _letterImageView;
    private TextView _subject, _time;
    private MyRoomDB db = null;

    public DayAdapter(Context context, String[] title, String[] description){
        _context = context;
        _subjectStrings = title;
        _timeStrings = description;

        _layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _subjectStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return _subjectStrings[i];
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

        _subject.setText(_subjectStrings[i]);
        _time.setText(_timeStrings[i]);

        _letterImageView.setOval(true);
        _letterImageView.setLetter(_subjectStrings[i].charAt(0));

        return view;
    }
}
