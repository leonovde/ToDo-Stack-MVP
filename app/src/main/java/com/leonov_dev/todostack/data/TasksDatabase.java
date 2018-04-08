package com.leonov_dev.todostack.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TasksDatabase extends RoomDatabase{

    public abstract TaskDao getTaskDao();

    /*  --------------Deprecated due to use of DI----------------

    private static final String DB_NAME = "Tasks.db";

    private static TasksDatabase instance;

    private static final Object sLock = new Object();

    public static TasksDatabase getInstance(Context context){
        synchronized (sLock){
            if (instance == null){
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        TasksDatabase.class,
                        DB_NAME)
                        .build();
            }
            return instance;
        }
    }

    */
}
