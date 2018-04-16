package com.leonov_dev.todostack.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/*
    Executor to run a Runnable task on a background thread
 */
public class DiskIOThreadExecutor implements Executor{

    private final Executor mDiskIO;

    public DiskIOThreadExecutor(){
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable command) {
        mDiskIO.execute(command);
    }

}
