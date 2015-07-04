package ru.ionov.timetable.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import ru.ionov.timetable.R;

public class DateActivity extends ActionBarActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

//        DatePicker datePicker = (DatePicker) findViewById(R.id.customDateFrom);
//        datePicker.setDateRange(1, 1, 2015, 1, 1, 2016);
//        datePicker.setDate(1, 07, 2015);
    }
}
