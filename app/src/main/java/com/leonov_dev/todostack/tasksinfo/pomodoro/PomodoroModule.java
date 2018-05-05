package com.leonov_dev.todostack.tasksinfo.pomodoro;

import com.leonov_dev.todostack.di.ActivityScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class PomodoroModule {

    @Provides
    @ActivityScoped
    static long provideTaskId(PomodoroActivity activity){
        return activity.getIntent().getExtras().getLong(activity.TASK_ID_KEY);
    }

    @ActivityScoped
    @Binds
    abstract PomodoroContract.Presenter providesPresenter(PomodoroPresenter presenter);

}
