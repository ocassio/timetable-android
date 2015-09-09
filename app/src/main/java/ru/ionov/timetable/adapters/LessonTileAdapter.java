package ru.ionov.timetable.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Lesson;
import ru.ionov.timetable.viewholders.LessonViewHolder;

public class LessonTileAdapter extends RecyclerView.Adapter<LessonViewHolder>
{
    private List<Lesson> lessons;

    public LessonTileAdapter(List<Lesson> lessons)
    {
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
        holder.getRoom().setText(lesson.getRoom());
        holder.getType().setText(lesson.getType());

        if (!TextUtils.isEmpty(lesson.getTeacher()))
        {
            holder.getTeacher().setText(lesson.getTeacher());
        }
        else
        {
            holder.getTeacher().setText("-");
        }
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
}
