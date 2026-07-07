package com.himamanth.pastebin.util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeAgoUtil 
{

    public static String timeAgo(Timestamp timestamp) 
    {

        if (timestamp == null) 
        {
            return "";
        }

        LocalDateTime created = timestamp.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        Duration duration = Duration.between(created, now);

        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        if (minutes < 1)
            return "Just now";

        if (minutes < 60)
            return minutes + (minutes == 1 ? "minute ago" : "minutes ago");

        if (hours < 24)
            return hours + (hours == 1 ? "hour ago" : "hours ago");

        if (days < 7)
            return days + (days == 1 ? "day ago" : "days ago");

        if (days < 30) 
        {
            long weeks = days / 7;
            return weeks + (weeks == 1 ? "week ago" : "weeks ago");
        }

        if (days < 365) 
        {
            long months = days / 30;
            return months + (months == 1 ? "month ago" : "months ago");
        }

        long years = days / 365;
        return years + (years == 1 ? "year ago" : " years ago");
    }
}