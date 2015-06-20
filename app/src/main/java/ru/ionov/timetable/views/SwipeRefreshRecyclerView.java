package ru.ionov.timetable.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SwipeRefreshRecyclerView extends RecyclerView
{
    public SwipeRefreshRecyclerView(Context context)
    {
        super(context);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SwipeRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canScrollVertically(int direction)
    {
        if (direction < 1) {
            boolean original = super.canScrollVertically(direction);
            return !original && getChildAt(0) != null && getChildAt(0).getTop() < 0 || original;
        }

        return super.canScrollVertically(direction);
    }
}
