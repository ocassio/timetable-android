package ru.ionov.timetable.models;

import java.io.Serializable;

public class TimeRange implements Serializable
{
    private String from;
    private String to;

    public TimeRange()
    {
    }

    public TimeRange(String from, String to)
    {
        this.from = from;
        this.to = to;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }
}
