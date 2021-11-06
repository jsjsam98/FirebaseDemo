package com.example.firebasedemo;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;

import java.sql.Time;
import java.time.LocalDateTime;

public class StickMessage {
    private String time;
    private String text;
    private String image;

    public StickMessage() {
    }

    public StickMessage(String time, String text) {
        this.time = time;
        this.text = text;
    }


    @Override
    public String toString() {
        return "time= " + time + "\n"+
                "text= " + text;
    }
}

