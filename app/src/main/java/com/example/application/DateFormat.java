package com.example.application;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface DateFormat {

    @SuppressLint("SimpleDateFormat")
    default String getDateToString() {
        Date date = new Date();
        final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }
}
