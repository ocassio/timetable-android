package ru.ionov.timetable.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ru.ionov.timetable.providers.PreferencesProvider;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent;
        if (PreferencesProvider.getCriterion() != null)
        {
            intent = new Intent(this, TimetableActivity.class);
        }
        else
        {
            intent = new Intent(this, CriteriaActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
