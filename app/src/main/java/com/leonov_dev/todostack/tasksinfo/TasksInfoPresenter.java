package com.leonov_dev.todostack.tasksinfo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;

import javax.annotation.Nullable;
import javax.inject.Inject;

@ActivityScoped
public class TasksInfoPresenter implements TasksInfoContract.Presenter {

    @Nullable
    private TasksInfoContract.View mView;

    @NonNull
    private final TasksRepository mTasksRepository;

    @NonNull
    private long mTaskId;

    private final String LOG_TAG = TasksInfoPresenter.class.getSimpleName();

    @Inject
    public TasksInfoPresenter(long id, TasksRepository tasksRepository){
        mTaskId = id;
        mTasksRepository = tasksRepository;
    }

    @Override
    public void editTask() {

    }

    @Override
    public void deleteTask() {

    }

    @Override
    public void takeView(TasksInfoContract.View view) {
        mView = view;
        showToDo();
    }

    @Override
    public void dropView() {
        mView = null;
    }

    private void showToDo(){
        Log.e(LOG_TAG, "ShowingTodo triggered, ID = " + mTaskId);
        mTasksRepository.getTask(mTaskId, new TaskDataSoruce.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (mView == null) {
                    return;
                }
                Log.e(LOG_TAG, "Filling todo is triggered");
                fillTodo(task);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void fillTodo(Task task){
        mView.showTitle(task.mTitle);
        mView.showDescription(task.mDescription);
    }
}
