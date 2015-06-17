package ru.ionov.timetable.models;

import java.io.Serializable;
import java.util.List;

public class Lesson implements Serializable
{
    private String number;
    private String room;
    private String name;
    private String teacher;
    private String type;

    public Lesson()
    {
    }

    public Lesson(String number, String room, String name, String teacher, String type)
    {
        this.number = number;
        this.room = room;
        this.name = name;
        this.teacher = teacher;
        this.type = type;
    }

    public Lesson(String[] params)
    {
        this.room = params[0];
        this.number = params[1];
        this.teacher = params[2];
        this.type = params[3];
        this.name = params[4];
    }

    public Lesson(List<String> params)
    {
        this(params.toArray(new String[params.size()]));
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getRoom()
    {
        return room;
    }

    public void setRoom(String room)
    {
        this.room = room;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return number + ", " + room + ", " + name + ", " + type + ", " + teacher;
    }
}
