package com.leonov_dev.todostack.tasks;

import android.app.Activity;
import android.util.Log;

import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.taskseditor.TasksEditorActivity;
import com.leonov_dev.todostack.tasksinfo.TasksInfoActivity;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.Nullable;
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
        mTasksRepository.getTasks(new TaskDataSoruce.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                //TODO add filters for (day && type of action)
                if (mTasksView == null /*|| !mTasksView.isActive()*/){
                    return;
                }
                if (tasks != null) {
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
            mTasksView.showAddTask();
        }
    }

    @Override
    public void openStatistics() {
        if (mTasksView != null){
            mTasksView.showStatistics();
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

    @Override
    public void checkResult(int requestCode, int resultCode) {
        if (mTasksView != null){
            if (requestCode == TasksEditorActivity.ADD_TASK_KEY && resultCode == Activity.RESULT_OK){
                mTasksView.showSuccessfullySavedMessage();
            }
            if (requestCode == TasksInfoActivity.SHOW_TASK_INFO &&
                    resultCode == TasksInfoActivity.SHOW_TASK_INFO_DELETED){
                mTasksView.showSuccessfullyDeletedMessage();
            }
        }
    }

    @Override
    public void setCategoryFilter() {

    }

    @Override
    public void setTimeFilter() {

    }

}
