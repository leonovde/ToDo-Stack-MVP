package com.leonov_dev.todostack.di;

import com.leonov_dev.todostack.tasks.TasksActivity;
import com.leonov_dev.todostack.tasks.TasksModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = TasksModule.class)
    abstract TasksActivity provideTasksActivity();
}
