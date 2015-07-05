package ru.ionov.timetable.viewholders;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;
import ru.ionov.timetable.activities.TimetableActivity;
import ru.ionov.timetable.models.Group;
import ru.ionov.timetable.providers.PreferencesProvider;

public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView name;

    private Activity activity;

    private Group group;

    public GroupViewHolder(View itemView, Activity activity)
    {
        super(itemView);
        this.activity = activity;

        name = (TextView) itemView.findViewById(R.id.groupName);

        name.setOnClickListener(this);
    }

    public TextView getName()
    {
        return name;
    }

    public void setName(TextView name)
    {
        this.name = name;
    }

    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
        this.name.setText(group.getName());
    }

    @Override
    public void onClick(View v)
    {
        PreferencesProvider.setGroup(this.group);

        Intent intent = new Intent(activity, TimetableActivity.class);
        activity.startActivity(intent);
    }
}
