package com.leonov_dev.todostack.utils;

import android.content.Context;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    public static long getStartOfTodayInMilliseconds(){
        Calendar calendar = getClearedCalendarInstance();
        return calendar.getTimeInMillis();
    }

    public static long getStartOfWeekInMilliseconds(){
        Calendar calendar = getClearedCalendarInstance();
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTimeInMillis();
    }

    public static long getStartOfMonthInMilliseconds(){
        Calendar calendar = getClearedCalendarInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    private static Calendar getClearedCalendarInstance(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar;
    }

    public static long getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTimeInMillis();
    }

    public static SimpleDateFormat getFormatForTime(){
        return new SimpleDateFormat("HH:mm");
    }

    public static SimpleDateFormat getFormatForDate(){
        return new SimpleDateFormat("yyyy/MM/dd");
    }

    public static SimpleDateFormat getFormatForDateAndTime(){
        return new SimpleDateFormat("yyyy/MM/dd HH:mm");
    }

    public static void SetAlarm (Date date, Context context){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//
//        Intent intent = new Intent(context, ClassThatWillHandleThisShit.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), req1, intent, 0);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, target.getTimeInMillis(), pendingIntent);

    }
}
