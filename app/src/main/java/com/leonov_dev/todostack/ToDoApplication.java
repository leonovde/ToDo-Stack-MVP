package com.leonov_dev.todostack;

import com.leonov_dev.todostack.data.TasksRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class ToDoApplication extends DaggerApplication {

    @Inject
    public TasksRepository mTasksRepository;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }

}
