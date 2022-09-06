package ru.ionov.timetable.providers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.ionov.timetable.models.*;
import ru.ionov.timetable.utils.DateUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataProvider
{
    private static final String TIMETABLE_URL = "https://www.tolgas.ru/services/raspisanie/";
    private static final int CONNECTION_TIMEOUT = 15000;

    private static final String ATTR_VALUE = "value";
    private static final String TIME_RANGE_TR_SUBSTRING = "пара";

    private static final Pattern TIME_PATTERN = Pattern.compile("\\d{2}[^\\d]\\d{2}");

    private static final int COL_COUNT = 7;

    private static final List<TimeRange> FALLBACK_TIME_RANGES = new ArrayList<TimeRange>()
    {
        {
            add(new TimeRange("08:30", "10:05"));
            add(new TimeRange("10:15", "11:50"));
            add(new TimeRange("12:35", "14:10"));
            add(new TimeRange("14:20", "15:55"));
            add(new TimeRange("16:05", "17:35"));
            add(new TimeRange("17:45", "19:20"));
            add(new TimeRange("19:30", "21:05"));
        }
    };

    private static final Map<Integer, List<Criterion>> criteriaCaches = new HashMap<>();

    private DataProvider() {}

    public static List<Criterion> getCriteria() throws IOException
    {
        int criteriaType = PreferencesProvider.getCriteriaType();
        return getCriteria(criteriaType, true);
    }

    public static List<Criterion> getCriteria(int criteriaType, boolean forced) throws IOException
    {
        List<Criterion> criteria = criteriaCaches.get(criteriaType);

        if (forced || criteria == null || criteria.isEmpty())
        {
            criteria = new ArrayList<>();

            Document document = Jsoup.connect(TIMETABLE_URL)
                    .timeout(CONNECTION_TIMEOUT)
                    .method(Connection.Method.GET)
                    .data("id", Integer.toString(criteriaType))
                    .get();

            Elements elements = document.select("#vr option");

            for (Element element : elements)
            {
                criteria.add(new Criterion(element.attr(ATTR_VALUE), element.text()));
            }

            criteriaCaches.put(criteriaType, criteria);
        }

        return criteria;
    }

    public static List<Day> getTimetable(String criterion) throws IOException
    {
        DateRange dateRange = PreferencesProvider.getDateRange();
        return getTimetable(criterion, dateRange.getFrom(), dateRange.getTo());
    }

    public static List<Day> getTimetable(String criterion, Date from, Date to) throws IOException
    {
        int rel = PreferencesProvider.getCriteriaType();

        Document document = Jsoup.connect(TIMETABLE_URL)
                .timeout(CONNECTION_TIMEOUT)
                .method(Connection.Method.POST)
                .data("rel", Integer.toString(rel))
                .data("vr", criterion)
                .data("from", DateUtils.toDateString(from))
                .data("to", DateUtils.toDateString(to))
                .data("submit_button", "ПОКАЗАТЬ")
                .post();

        Elements elements = document.select("#send td.hours");
        if (elements.isEmpty())
        {
            throw new IOException("Timetable is missing on loaded page");
        }

        List<TimeRange> timeRanges = getTimeRanges(document);

        return getDays(elements, timeRanges);
    }

    private static List<TimeRange> getTimeRanges(Document document) {
        try {
            List<TimeRange> result = new ArrayList<>();
            Elements rowElements = document.select("form table tr");
            for (Element element : rowElements) {
                String text = element.text();
                if (!text.contains(TIME_RANGE_TR_SUBSTRING)) {
                    continue;
                }

                Matcher matcher = TIME_PATTERN.matcher(text);
                List<String> times = new ArrayList<>();
                while (matcher.find()) {
                    times.add(matcher.group());
                }
                if (times.size() < 2) {
                    continue;
                }

                TimeRange range = new TimeRange(
                        normalizeTime(times.get(0)),
                        normalizeTime(times.get(times.size() - 1))
                );

                result.add(range);
            }

            if (result.isEmpty()) {
                return FALLBACK_TIME_RANGES;
            }

            return result;
        } catch (Exception ignored) {
            return FALLBACK_TIME_RANGES;
        }
    }

    private static String normalizeTime(String time) {
        return time.replaceAll("[^\\d]", ":");
    }

    private static List<Day> getDays(Elements elements, List<TimeRange> timeRanges)
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

                    Lesson lesson = new Lesson(params);
                    setTimeRanges(lesson, timeRanges);
                    day.addLesson( lesson);
                }
                while (i < elements.size() && !DateUtils.isDate(elements.get(i).text()));

                days.add(day);
            }
        }

        return days;
    }

    private static void setTimeRanges(Lesson lesson, List<TimeRange> timeRanges)
    {
        int lessonNum = Integer.parseInt(lesson.getNumber());
        if (lessonNum <= timeRanges.size())
        {
            lesson.setTime(timeRanges.get(lessonNum - 1));
        }
    }
}
