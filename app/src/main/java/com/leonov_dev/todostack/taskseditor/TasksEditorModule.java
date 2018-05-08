package com.leonov_dev.todostack.taskseditor;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.di.FragmentScoped;
import com.leonov_dev.todostack.taskseditor.durationdialog.DurationContract;
import com.leonov_dev.todostack.taskseditor.durationdialog.DurationFragment;
import com.leonov_dev.todostack.taskseditor.durationdialog.DurationPresenter;
import com.leonov_dev.todostack.taskseditor.reminderdialog.ReminderDialogContract;
import com.leonov_dev.todostack.taskseditor.reminderdialog.ReminderFragment;
import com.leonov_dev.todostack.taskseditor.reminderdialog.ReminderPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

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

    //ReminderDialogModule
    @FragmentScoped
    @ContributesAndroidInjector
    abstract ReminderFragment provideReminderFragment();

    @ActivityScoped
    @Binds
    abstract ReminderDialogContract.Presenter providesReminderPresenter
            (ReminderPresenter reminderPresenter);

//    @FragmentScoped
//    @ContributesAndroidInjector
//    abstract ReminderFragment.TimerReminderFragment provideTimerReminderFragment();

    //Duration Dialog Module
    @FragmentScoped
    @ContributesAndroidInjector
    abstract DurationFragment prodvideDurationFragment();

    @ActivityScoped
    @Binds
    abstract DurationContract.Presenter providesDurationPresenter
            (DurationPresenter durationPresenter);

}
