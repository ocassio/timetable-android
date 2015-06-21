package ru.ionov.timetable.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import ru.ionov.timetable.providers.CacheProvider;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent;
        if (CacheProvider.getGroup() != null)
        {
            intent = new Intent(this, TimetableActivity.class);
        }
        else
        {
            intent = new Intent(this, GroupsActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
