package com.leonov_dev.todostack.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class TasksRepositoryModule {



    @Provides
    @Singleton
    static TaskDao provideTaskDao(TasksDatabase db){
        return db.getTaskDao();
    }

    @Provides
    @Singleton
    static TasksDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), TasksDatabase.class, "Tasks.db")
                .build();
    }


}
