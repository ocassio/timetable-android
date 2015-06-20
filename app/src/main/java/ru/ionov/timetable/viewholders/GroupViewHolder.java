package ru.ionov.timetable.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Group;
import ru.ionov.timetable.providers.CacheProvider;

public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView name;

    private Group group;

    public GroupViewHolder(View itemView)
    {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.groupName);

        itemView.setOnClickListener(this);
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
        Group group = CacheProvider.getGroup();
        if (group != null)
        {
            System.out.println("Previous group: " + group.getName());
        }
        else
        {
            System.out.println("There is no group stored in preferences");
        }
        CacheProvider.saveGroup(this.group);
        System.out.println("New group: " + this.group.getName());
    }
}
