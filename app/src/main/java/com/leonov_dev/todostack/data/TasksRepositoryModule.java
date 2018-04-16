package com.leonov_dev.todostack.data;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.leonov_dev.todostack.utils.AppExecutors;
import com.leonov_dev.todostack.utils.DiskIOThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class TasksRepositoryModule {


    //TODO why singleton provides, not provides singletone
    @Singleton
    @Provides
    static TaskDao provideTaskDao(TasksDatabase db){
        return db.getTaskDao();
    }

    @Singleton
    @Provides
    static TasksDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), TasksDatabase.class, "Tasks.db")
                .build();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors(){
        return new AppExecutors(new DiskIOThreadExecutor(),
                new AppExecutors.MainThreadExecutor());
    }

}
