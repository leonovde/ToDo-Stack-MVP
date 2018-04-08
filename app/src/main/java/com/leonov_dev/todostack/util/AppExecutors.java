package com.leonov_dev.todostack.util;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

@Singleton
public class AppExecutors {

    private static final int THREAD_COUNT = 2;

    private final Executor diskIO;

    private final Executor mainThread;

    public AppExecutors(Executor diskIO, Executor mainThread){
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    public static class MainThreadExecutor implements Executor{

        @Override
        public void execute(@NonNull Runnable command) {

        }
    }
}
