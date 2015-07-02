package ru.ionov.timetable.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Group;
import ru.ionov.timetable.viewholders.GroupViewHolder;

public class GroupTileAdapter extends RecyclerView.Adapter<GroupViewHolder>
{
    private Activity activity;
    private List<Group> groups;
    private List<Group> filteredGroups;

    public GroupTileAdapter(Activity activity, List<Group> groups)
    {
        this.activity = activity;
        this.groups = groups;

        filteredGroups = new ArrayList<>(groups);
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_tile, parent, false);

        return new GroupViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position)
    {
        holder.setGroup(filteredGroups.get(position));
    }

    @Override
    public int getItemCount()
    {
        return filteredGroups.size();
    }

    public void reloadData(List<Group> groups)
    {
        this.groups = groups;

        filteredGroups = new ArrayList<>(groups);

        notifyDataSetChanged();
    }

    public void filterData(String query)
    {
        if (!TextUtils.isEmpty(query))
        {
            filteredGroups.clear();
            query = query.toLowerCase();

            for (Group group : groups)
            {
                if (group.getName().toLowerCase().contains(query))
                {
                    filteredGroups.add(group);
                }
            }
        }
        else
        {
            filteredGroups = new ArrayList<>(groups);
        }

        notifyDataSetChanged();
    }
}
