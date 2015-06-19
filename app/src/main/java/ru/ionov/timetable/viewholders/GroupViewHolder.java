package ru.ionov.timetable.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;

public class GroupViewHolder extends RecyclerView.ViewHolder
{
    private TextView name;

    public GroupViewHolder(View itemView)
    {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.groupName);
    }

    public TextView getName()
    {
        return name;
    }

    public void setName(TextView name)
    {
        this.name = name;
    }
}
