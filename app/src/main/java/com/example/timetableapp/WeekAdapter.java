package com.example.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.timetableapp.Utils.LetterImageView;

public class WeekAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater layoutInflater;
    private String[] weekStrings;

    public WeekAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);

        this.resource = resource;
        this.weekStrings = objects;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(resource, null);
            viewHolder.setTv_week((TextView) convertView.findViewById(R.id.tv_main));
            viewHolder.setIv_logo((LetterImageView) convertView.findViewById(R.id.iv_letter));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setLetterImageViewToOval(true);
        viewHolder.setLetterToLetterImageView(weekStrings[position].charAt(0));
        viewHolder.setTextToLetterImageView(weekStrings[position]);

        return convertView;
    }
}
