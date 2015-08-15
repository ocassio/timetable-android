package ru.ionov.timetable.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class RobotoTextView extends TextView
{
    private static Typeface font;

    public RobotoTextView(Context context)
    {
        super(context);
        setFont();
    }

    public RobotoTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setFont();
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    private void setFont()
    {
        if (font == null)
        {
            font = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
        }
        setTypeface(font);
    }
}
