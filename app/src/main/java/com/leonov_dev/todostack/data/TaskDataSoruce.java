package com.leonov_dev.todostack.data;

import android.support.annotation.NonNull;

import java.util.List;

public interface TaskDataSoruce {

    interface LoadTasksCallback {

        void onTasksLoaded(List<Task> tasks);

        void onDataNotAvailable();
    }

    interface GetTaskCallback {

        void onTaskLoaded(Task task);

        void onDataNotAvailable();
    }

    void getTasks(@NonNull LoadTasksCallback callback);

    void getTask(@NonNull long taskId, @NonNull GetTaskCallback callback);

    void getTasks(@NonNull long fromTime, @NonNull LoadTasksCallback callback);

    void saveTask(@NonNull Task task);

    void updateTask(@NonNull Task task);

    void deleteAllTasks();

    void deleteTaskById(@NonNull long taskId);
}
