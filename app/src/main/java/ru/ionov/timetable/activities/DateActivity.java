package ru.ionov.timetable.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import ru.ionov.timetable.R;

public class DateActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
    }

    @Override
    public void onBackPressed()
    {
        NavUtils.navigateUpFromSameTask(this);
    }
}
