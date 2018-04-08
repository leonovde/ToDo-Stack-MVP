package com.leonov_dev.todostack.data;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import static dagger.internal.Preconditions.checkNotNull;

@Singleton
public class TasksRepository implements TaskDataSoruce {

    private final TaskDao mTaskDao;

    @Inject
    public TasksRepository(@NonNull TaskDao taskDao){
        mTaskDao = taskDao;
    }


    @Override
    public void getTasks(@NonNull LoadTasksCallback callback) {

    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull GetTaskCallback callback) {

    }

    @Override
    public void saveTask(@NonNull final Task task) {
        checkNotNull(task);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mTaskDao.insertTask(task);
            }
        };

    }

    @Override
    public void updateTask(@NonNull Task task) {

    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }
}
