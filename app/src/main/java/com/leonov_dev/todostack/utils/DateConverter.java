package com.leonov_dev.todostack.utils;

import android.arch.persistence.room.TypeConverter;

import java.sql.Date;

public class DateConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long toTimeStamp(Date date){
        if (date == null) {
            return null;
        } else {
            return date.getTime();
        }
    }
}
