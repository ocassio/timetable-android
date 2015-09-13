package ru.ionov.timetable.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Lesson;
import ru.ionov.timetable.providers.PreferencesProvider;
import ru.ionov.timetable.viewholders.LessonViewHolder;

public class LessonTileAdapter extends RecyclerView.Adapter<LessonViewHolder>
{
    private final Context context;

    private List<Lesson> lessons;

    public LessonTileAdapter(Context context, List<Lesson> lessons)
    {
        this.context = context;
        this.lessons = lessons;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lesson_tile, parent, false);

        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position)
    {
        Lesson lesson = lessons.get(position);

        holder.getNumber().setText(lesson.getNumber());
        if (lesson.getTime() != null)
        {
            holder.getTimeFrom().setText(lesson.getTime().getFrom());
            holder.getTimeTo().setText(lesson.getTime().getTo());
        }
        holder.getName().setText(lesson.getName());
        holder.getType().setText(lesson.getType());

        resolveUpperAndLowerLabels(holder, lesson);
    }

    @Override
    public int getItemCount()
    {
        return lessons.size();
    }

    public void reloadData(List<Lesson> lessons)
    {
        this.lessons = lessons;
        notifyDataSetChanged();
    }

    private void resolveUpperAndLowerLabels(LessonViewHolder holder, Lesson lesson)
    {
        int criteriaType = PreferencesProvider.getCriteriaType();
        Resources resources = context.getResources();

        if (criteriaType == resources.getInteger(R.integer.criteriaTypeGroup))
        {
            holder.getUpperLabel().setText(lesson.getRoom());
            holder.getLowerLabel().setText(lesson.getTeacher());
        }
        else if (criteriaType == resources.getInteger(R.integer.criteriaTypeTeacher))
        {
            holder.getUpperLabel().setText(lesson.getRoom());
            holder.getLowerLabel().setText(lesson.getGroup());
        }
        else if (criteriaType == resources.getInteger(R.integer.criteriaTypeRoom))
        {
            holder.getUpperLabel().setText(lesson.getGroup());
            holder.getLowerLabel().setText(lesson.getTeacher());
        }

        if (TextUtils.isEmpty(holder.getUpperLabel().getText()))
        {
            holder.getUpperLabel().setText("-");
        }
        if (TextUtils.isEmpty(holder.getLowerLabel().getText()))
        {
            holder.getLowerLabel().setText("-");
        }
    }
}
