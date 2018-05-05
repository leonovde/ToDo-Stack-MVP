package com.leonov_dev.todostack.di;

import com.leonov_dev.todostack.statistics.StatisticsActivity;
import com.leonov_dev.todostack.statistics.StatisticsModule;
import com.leonov_dev.todostack.tasks.TasksActivity;
import com.leonov_dev.todostack.tasks.TasksModule;
import com.leonov_dev.todostack.taskseditor.TasksEditorActivity;
import com.leonov_dev.todostack.taskseditor.TasksEditorModule;
import com.leonov_dev.todostack.tasksinfo.TasksInfoActivity;
import com.leonov_dev.todostack.tasksinfo.TasksInfoModule;
import com.leonov_dev.todostack.tasksinfo.pomodoro.PomodoroActivity;
import com.leonov_dev.todostack.tasksinfo.pomodoro.PomodoroModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = TasksModule.class)
    abstract TasksActivity provideTasksActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = TasksEditorModule.class)
    abstract TasksEditorActivity provideTasksEditor();

    @ActivityScoped
    @ContributesAndroidInjector(modules = TasksInfoModule.class)
    abstract TasksInfoActivity provideTasksInfoModule();

    @ActivityScoped
    @ContributesAndroidInjector(modules = StatisticsModule.class)
    abstract StatisticsActivity providesStatisticsModule();

    @ActivityScoped
    @ContributesAndroidInjector(modules = PomodoroModule.class)
    abstract PomodoroActivity providesPomodoroModule();

}
