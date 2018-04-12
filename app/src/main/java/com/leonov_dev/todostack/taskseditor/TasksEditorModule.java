package com.leonov_dev.todostack.taskseditor;

import com.leonov_dev.todostack.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TasksEditorModule {

    @ActivityScoped
    @Binds
    abstract TasksEditorContract.Presenter providesTasksEditorPresenter
            (TasksEditorPresenter tasksEditorPresenter);
}
