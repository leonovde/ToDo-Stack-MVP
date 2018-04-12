package com.leonov_dev.todostack.tasks;

import android.util.Log;

import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

@ActivityScoped
public final class TasksPresenter implements TasksContract.Presenter {

    private final String LOG_TAG = TasksPresenter.class.getSimpleName();

    private final TasksRepository mTasksRepository;

    @Nullable
    private TasksContract.View mTasksView;

    @Inject
    TasksPresenter(TasksRepository tasksRepository){
        mTasksRepository = tasksRepository;
    }

    @Override
    public void loadTasks() {
        Log.e(LOG_TAG, "1111 LoadTasks in presenter");
        mTasksRepository.getTasks(new TaskDataSoruce.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                Log.e(LOG_TAG, "44444-1 Passing to a view Size of tasks " +  tasks.size());
                //TODO add filters for (day && type of action)
                if (mTasksView == null /*|| !mTasksView.isActive()*/){
                    return;
                }
                if (tasks != null) {
                    Log.e(LOG_TAG, "44444-2 Passing to a view Size of tasks " +  tasks.size());
                    mTasksView.showTasks(tasks);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mTasksView.isActive()){
                    return;
                }
                //TODO add some UI indicator of loading
            }
        });
    }

    @Override
    public void addNewTask() {
        if (mTasksView != null){
            Log.e(LOG_TAG, "Providing new Task Activity");
            mTasksView.showAddTask();
        }
    }

    @Override
    public void takeView(TasksContract.View view) {
        this.mTasksView = view;
        loadTasks();
    }

    @Override
    public void dropView() {
        mTasksView = null;
    }
}
