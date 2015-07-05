package ru.ionov.timetable.providers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.ionov.timetable.models.DateRange;
import ru.ionov.timetable.models.Day;
import ru.ionov.timetable.models.Group;
import ru.ionov.timetable.models.Lesson;
import ru.ionov.timetable.utils.DateUtils;

public final class DataProvider
{
    private static final String REL_GROUP = "0";

    private static final String ATTR_VALUE = "value";

    private static final String TIMETABLE_URL = "http://www.tolgas.ru/services/raspisanie/";

    private static final int COL_COUNT = 7;

    private DataProvider() {}

    public static List<Group> getGroups() throws IOException
    {
        List<Group> groups = new ArrayList<>();

        Document document = Jsoup.connect(TIMETABLE_URL)
                .method(Connection.Method.GET)
                .get();

        Elements elements = document.select("#vr option");

        for (Element element : elements)
        {
            groups.add(new Group(element.attr(ATTR_VALUE), element.text()));
        }

        return groups;
    }

    public static List<Day> getTimetableByGroup(String group) throws IOException
    {
        DateRange dateRange = PreferencesProvider.getDateRange();
        return getTimetableByGroup(group, dateRange.getFrom(), dateRange.getTo());
    }

    public static List<Day> getTimetableByGroup(String group, Date from, Date to) throws IOException
    {
        Document document = Jsoup.connect(TIMETABLE_URL)
                .method(Connection.Method.POST)
                .data("rel", REL_GROUP)
                .data("vr", group)
                .data("from", DateUtils.toString(from))
                .data("to", DateUtils.toString(to))
                .data("submit_button", "ПОКАЗАТЬ")
                .post();

        Elements elements = document.select("#send td.hours");

        return getDays(elements);
    }

    private static List<Day> getDays(Elements elements)
    {
        List<Day> days = new ArrayList<>();

        // No lessons have been found
        if (elements.size() == 1)
        {
            return days;
        }

        int i = 0;
        while (i < elements.size())
        {
            String date = elements.get(i).text();
            if (DateUtils.isDate(date))
            {
                Day day = new Day(date);
                i++;
                do
                {
                    List<String> params = new ArrayList<>();

                    for (int j = 0; j < COL_COUNT; j++)
                    {
                        params.add(elements.get(i).text());
                        i++;
                    }

                    day.addLesson(new Lesson(params));
                }
                while (i < elements.size() && !DateUtils.isDate(elements.get(i).text()));

                days.add(day);
            }
        }

        return days;
    }


}
