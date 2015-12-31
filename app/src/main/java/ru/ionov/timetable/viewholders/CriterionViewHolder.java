package ru.ionov.timetable.viewholders;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.ionov.timetable.R;
import ru.ionov.timetable.activities.CriteriaActivity;
import ru.ionov.timetable.activities.TimetableActivity;
import ru.ionov.timetable.models.Criterion;
import ru.ionov.timetable.providers.PreferencesProvider;

public class CriterionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    private TextView name;

    private CriteriaActivity activity;

    private Criterion criterion;

    public CriterionViewHolder(View itemView, CriteriaActivity activity)
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

    public Criterion getCriterion()
    {
        return criterion;
    }

    public void setCriterion(Criterion criterion)
    {
        this.criterion = criterion;
        this.name.setText(criterion.getName());
    }

    @Override
    public void onClick(View v)
    {
        if (criterion.getId() == null)
        {
            return;
        }

        PreferencesProvider.setCriteriaType(activity.getSelectedCriteriaType());
        PreferencesProvider.setCriterion(this.criterion);
        PreferencesProvider.addRecentCriterion(activity.getSelectedCriteriaType(), this.criterion);

        Intent intent = new Intent(activity, TimetableActivity.class);
        activity.startActivity(intent);
    }
}
