package com.leonov_dev.todostack.data;

import android.support.annotation.NonNull;
import android.util.Log;

import com.leonov_dev.todostack.utils.AppExecutors;
import com.leonov_dev.todostack.utils.CalendarUtils;

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
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getTasks();
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(runnable);

    }

    @Override
    public void getTask(@NonNull final long taskId, @NonNull final GetTaskCallback callback) {
        Runnable getTaskRunnable = new Runnable() {
            @Override
            public void run() {
                final Task task = mTaskDao.getTaskById(taskId);
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (task != null) {
                            callback.onTaskLoaded(task);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(getTaskRunnable);
    }

    @Override
    public void getThisMonthTasks(@NonNull final LoadTasksCallback callback) {
        Runnable getThisWeekTasksRunnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getThisWeekTasks(
                        CalendarUtils.getStartOfMonthInMilliseconds());
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!tasks.isEmpty()){
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(getThisWeekTasksRunnable);
    }

    @Override
    public void getThisWeekTasks(@NonNull final LoadTasksCallback callback) {
        Runnable getThisWeekTasksRunnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getThisWeekTasks(
                        CalendarUtils.getStartOfWeekInMilliseconds());
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!tasks.isEmpty()){
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(getThisWeekTasksRunnable);
    }

    @Override
    public void getTodaysTasks(@NonNull final LoadTasksCallback callback) {
        Runnable getThisWeekTasksRunnable = new Runnable() {
            @Override
            public void run() {
                final List<Task> tasks = mTaskDao.getThisWeekTasks(
                        CalendarUtils.getStartOfTodayInMilliseconds());
                mAppExecutors.getMainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (!tasks.isEmpty()){
                            callback.onTasksLoaded(tasks);
                        }
                    }
                });
            }
        };
        mAppExecutors.getDiskIO().execute(getThisWeekTasksRunnable);
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
    public void updateTask(@NonNull final Task task) {
        checkNotNull(task);
        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                 mTaskDao.updateTask(task);
            }
        };
        mAppExecutors.getDiskIO().execute(updateRunnable);
    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public void deleteTaskById(@NonNull final long taskId) {
        Runnable deleteTaskRunnable = new Runnable() {
            @Override
            public void run() {
                mTaskDao.deleteTaskById(taskId);
            }
        };
        mAppExecutors.getDiskIO().execute(deleteTaskRunnable);
    }
}
