package ru.ionov.timetable.viewholders;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;

import ru.ionov.timetable.R;

public class LessonViewHolder extends RecyclerView.ViewHolder
{
    private TextView number;
    private TextView name;
    private TextView room;
    private TextView type;
    private TextView teacher;

    public LessonViewHolder(View itemView)
    {
        super(itemView);

        number = (TextView) itemView.findViewById(R.id.lessonNum);

        name = (TextView) itemView.findViewById(R.id.lessonName);
        name.setTypeface(Typeface.SANS_SERIF);

        room = (TextView) itemView.findViewById(R.id.lessonRoom);
        room.setTypeface(Typeface.SANS_SERIF);
        ViewHelper.setAlpha(room, 0.57f);

        type = (TextView) itemView.findViewById(R.id.lessonType);
        ViewHelper.setAlpha(type, 0.57f);

        teacher = (TextView) itemView.findViewById(R.id.lessonTeacher);
        teacher.setTypeface(Typeface.SANS_SERIF);
//        ViewHelper.setAlpha(teacher, 0.57f);
    }

    public TextView getNumber()
    {
        return number;
    }

    public void setNumber(TextView number)
    {
        this.number = number;
    }

    public TextView getName()
    {
        return name;
    }

    public void setName(TextView name)
    {
        this.name = name;
    }

    public TextView getRoom()
    {
        return room;
    }

    public void setRoom(TextView room)
    {
        this.room = room;
    }

    public TextView getType()
    {
        return type;
    }

    public void setType(TextView type)
    {
        this.type = type;
    }

    public TextView getTeacher()
    {
        return teacher;
    }

    public void setTeacher(TextView teacher)
    {
        this.teacher = teacher;
    }
}
