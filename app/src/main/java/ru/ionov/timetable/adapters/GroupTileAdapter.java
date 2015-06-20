package ru.ionov.timetable.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Group;
import ru.ionov.timetable.viewholders.GroupViewHolder;

public class GroupTileAdapter extends RecyclerView.Adapter<GroupViewHolder>
{
    private List<Group> groups;

    public GroupTileAdapter(List<Group> groups)
    {
        this.groups = groups;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_tile, parent, false);

        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position)
    {
        holder.getName().setText(groups.get(position).getName());
    }

    @Override
    public int getItemCount()
    {
        return groups.size();
    }

    public void reloadData(List<Group> groups)
    {
        this.groups = groups;
        notifyDataSetChanged();
    }
}
