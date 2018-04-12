package com.leonov_dev.todostack.util;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static long toTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }
}
