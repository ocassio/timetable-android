package ru.ionov.timetable.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;

public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView name;

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

    @Override
    public void onClick(View v)
    {
        System.out.println("Clicked: " + name.getText());
    }
}
