package ru.ionov.timetable.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.activities.CriteriaActivity;
import ru.ionov.timetable.models.Criterion;
import ru.ionov.timetable.viewholders.CriterionViewHolder;

public class CriterionTileAdapter extends RecyclerView.Adapter<CriterionViewHolder>
{
    private CriteriaActivity activity;
    private List<Criterion> criteria;
    private List<Criterion> filteredCriteria;

    public CriterionTileAdapter(CriteriaActivity activity, List<Criterion> criteria)
    {
        this.activity = activity;
        this.criteria = criteria;

        filteredCriteria = new ArrayList<>(criteria);
    }

    @Override
    public CriterionViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_tile, parent, false);

        return new CriterionViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(CriterionViewHolder holder, int position)
    {
        holder.setCriterion(filteredCriteria.get(position));
    }

    @Override
    public int getItemCount()
    {
        return filteredCriteria.size();
    }

    public void reloadData(List<Criterion> criteria)
    {
        this.criteria = criteria;

        filteredCriteria = new ArrayList<>(criteria);

        notifyDataSetChanged();
    }

    public void filterData(String query)
    {
        if (!TextUtils.isEmpty(query))
        {
            filteredCriteria.clear();
            query = query.toLowerCase();

            for (Criterion criterion : criteria)
            {
                if (criterion.getName().toLowerCase().contains(query))
                {
                    filteredCriteria.add(criterion);
                }
            }
        }
        else
        {
            filteredCriteria = new ArrayList<>(criteria);
        }

        notifyDataSetChanged();
    }
}
