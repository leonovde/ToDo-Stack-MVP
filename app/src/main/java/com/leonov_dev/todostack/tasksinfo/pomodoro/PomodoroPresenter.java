package com.leonov_dev.todostack.tasksinfo.pomodoro;

import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

@ActivityScoped
public class PomodoroPresenter implements PomodoroContract.Presenter {

    @Nullable
    private TasksRepository mRepository;

    private long mId;

    private PomodoroContract.View mView;

    @Inject
    public PomodoroPresenter(long taskId, TasksRepository tasksRepository){
        mRepository = tasksRepository;
        mId = taskId;
    }

    @Override
    public void startTimer() {

    }

    @Override
    public void takeView(PomodoroContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
