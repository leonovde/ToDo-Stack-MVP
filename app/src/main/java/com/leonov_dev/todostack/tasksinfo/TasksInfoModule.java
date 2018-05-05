package com.leonov_dev.todostack.tasksinfo;

import com.leonov_dev.todostack.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class TasksInfoModule {

    @ActivityScoped
    @Binds
    abstract TasksInfoContract.Presenter provideTasksInfoPresenter(
            TasksInfoPresenter tasksInfoPresenter);

    @Provides
    @ActivityScoped
    static long provideTaskId(TasksInfoActivity tasksInfoActivity){
        return tasksInfoActivity.getIntent().getExtras().getLong(tasksInfoActivity.TASK_ID_KEY);
    }

}
