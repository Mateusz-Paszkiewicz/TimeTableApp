package com.example.timetableapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Adapter extends BaseAdapter {
    private Context _context;
    private LayoutInflater _layoutInflater;
    private String[] _titles;
    private String[] _descriptions;
    private ImageView _imageview;
    private TextView _title,_description;

    public Adapter(Context context, String[] title, String[] description){
        _context = context;
        _titles = title;
        _descriptions = description;

        _layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return _titles.length;
    }

    @Override
    public Object getItem(int i) {
        return _titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = _layoutInflater.inflate(R.layout.activity_main_single_item, null);
        }

        _title = (TextView)view.findViewById(R.id.tv_main);
        _description = (TextView)view.findViewById(R.id.tv_description);
        _imageview = (ImageView)view.findViewById(R.id.iv_main);

        _title.setText(_titles[i]);
        _description.setText(_descriptions[i]);

        if(_titles[i].equalsIgnoreCase("Timetable")){
            _imageview.setImageResource(R.drawable.timetable);
        } else if(_titles[i].equalsIgnoreCase("Calendar")){
            _imageview.setImageResource(R.drawable.calendar);
        }

        return view;
    }
}
