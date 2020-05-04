package ru.ionov.timetable.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import ru.ionov.timetable.R;
import ru.ionov.timetable.models.Lesson;
import ru.ionov.timetable.providers.PreferencesProvider;
import ru.ionov.timetable.viewholders.LessonViewHolder;

import java.util.List;

public class LessonTileAdapter extends RecyclerView.Adapter<LessonViewHolder>
{
    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_WITH_NOTE = 1;

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
        int layoutResource;
        switch (viewType)
        {
            case TYPE_WITH_NOTE:
                layoutResource = R.layout.lesson_with_note_tile;
                break;

            default:
                layoutResource = R.layout.lesson_tile;
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutResource, parent, false);

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

        if (getItemViewType(position) == TYPE_WITH_NOTE)
        {
            holder.getNote().setVisibility(View.VISIBLE);
            holder.getNote().setText(lesson.getNote());
        }

        resolveUpperAndLowerLabels(holder, lesson);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (!TextUtils.isEmpty(lessons.get(position).getNote()))
        {
            return TYPE_WITH_NOTE;
        }
        return TYPE_DEFAULT;
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
