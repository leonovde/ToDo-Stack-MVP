package com.leonov_dev.todostack.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.leonov_dev.todostack.util.AppExecutors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import static dagger.internal.Preconditions.checkNotNull;

@Singleton
public class TasksRepository implements TaskDataSoruce {

    private final TaskDao mTaskDao;

    private final AppExecutors mAppExecutors;

    private final String LOG_TAG = TasksRepository.class.getSimpleName();

    @Inject
    public TasksRepository(@NonNull AppExecutors appExecutors, @NonNull TaskDao taskDao){
        mAppExecutors = appExecutors;
        mTaskDao = taskDao;
    }


    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        //TODO run a callback
        Log.e(LOG_TAG, "22222 get Tasks in Repo ");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getTasks();
                Log.e(LOG_TAG, "3333 Running a querry to DB size of tasks " + tasks.size());
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(LOG_TAG, "44444-000 Runned");
                        if (tasks.isEmpty()) {
                            Log.e(LOG_TAG, "Empty Task ");
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            Log.e(LOG_TAG, "Not empty task ");
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);

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
        mAppExecutors.getDiskIO().execute(saveRunnable);

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
