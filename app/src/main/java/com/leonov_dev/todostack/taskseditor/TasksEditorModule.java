package com.leonov_dev.todostack.taskseditor;

import android.support.annotation.Nullable;

import com.leonov_dev.todostack.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class TasksEditorModule {

    @ActivityScoped
    @Binds
    abstract TasksEditorContract.Presenter providesTasksEditorPresenter
            (TasksEditorPresenter tasksEditorPresenter);

    @Provides
    @ActivityScoped
    static long provideTaskId(TasksEditorActivity tasksEditorActivity){
        return tasksEditorActivity.getIntent().getLongExtra(TasksEditorActivity.TASK_EDIT_KEY, -1);
    }
}
