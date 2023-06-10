package com.example.timetableapp;

import android.widget.TextView;

import com.example.timetableapp.Utils.LetterImageView;

public class ViewHolder {
    private LetterImageView iv_logo;
    private TextView tv_week;

    public TextView getTv_week() {
        return tv_week;
    }

    public LetterImageView getIv_logo() {
        return iv_logo;
    }

    public void setTv_week(TextView tv_week) {
        this.tv_week = tv_week;
    }

    public void setIv_logo(LetterImageView iv_logo) {
        this.iv_logo = iv_logo;
    }

    public void setLetterImageViewToOval(Boolean state){
        iv_logo.setOval(state);
    }

    public void setLetterToLetterImageView(Character letter){
        iv_logo.setLetter(letter);
    }

    public void setTextToLetterImageView(String text){
        tv_week.setText(text);
    }
}
