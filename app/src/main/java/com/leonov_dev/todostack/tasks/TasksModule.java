package com.leonov_dev.todostack.tasks;

import com.leonov_dev.todostack.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class TasksModule {

    @ActivityScoped
    @Binds abstract TasksContract.Presenter providesTaskPresenter(TasksPresenter presenter);
}
