package ru.ionov.timetable.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import ru.ionov.timetable.R;
import ru.ionov.timetable.activities.CriteriaActivity;
import ru.ionov.timetable.models.Criterion;
import ru.ionov.timetable.viewholders.CriterionViewHolder;

import java.util.ArrayList;
import java.util.List;

public class CriterionTileAdapter extends RecyclerView.Adapter<CriterionViewHolder>
{
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_GROUP_HEADER = 1;

    private CriteriaActivity activity;
    private List<Criterion> criteria;
    private List<Criterion> recentCriteria;
    private List<Criterion> filteredCriteria;

    public CriterionTileAdapter(CriteriaActivity activity, List<Criterion> criteria)
    {
        this(activity, criteria, null);
    }

    public CriterionTileAdapter(CriteriaActivity activity, List<Criterion> criteria, List<Criterion> recentCriteria)
    {
        this.activity = activity;
        this.criteria = criteria;
        this.recentCriteria = recentCriteria;

        filteredCriteria = getCombinedCriteria();
    }

    @Override
    public CriterionViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        int layoutResource;

        switch (viewType)
        {
            case TYPE_GROUP_HEADER:
                layoutResource = R.layout.group_header_tile;
                break;

            default:
                layoutResource = R.layout.criterion_tile;
                break;
        }

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResource, parent, false);

        return new CriterionViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(CriterionViewHolder holder, int position)
    {
        holder.setCriterion(filteredCriteria.get(position));
    }

    @Override
    public int getItemViewType(int position)
    {
        if (filteredCriteria.get(position).getId() == null)
        {
            return TYPE_GROUP_HEADER;
        }
        return TYPE_DEFAULT;
    }

    @Override
    public int getItemCount()
    {
        return filteredCriteria.size();
    }

    public void reloadData(List<Criterion> criteria)
    {
        reloadData(criteria, null);
    }

    public void reloadData(List<Criterion> criteria, List<Criterion> recentCriteria)
    {
        this.criteria = criteria;
        this.recentCriteria = recentCriteria;

        filteredCriteria = getCombinedCriteria();

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
            filteredCriteria = getCombinedCriteria();
        }

        notifyDataSetChanged();
    }

    private List<Criterion> getCombinedCriteria()
    {
        List<Criterion> combinedCriteria = new ArrayList<>();

        if (recentCriteria != null && !recentCriteria.isEmpty())
        {
            combinedCriteria.add(new Criterion(activity.getResources().getString(R.string.recent)));
            combinedCriteria.addAll(recentCriteria);
            combinedCriteria.add(new Criterion(activity.getResources().getString(R.string.all)));
        }

        combinedCriteria.addAll(criteria);

        return combinedCriteria;
    }
}
