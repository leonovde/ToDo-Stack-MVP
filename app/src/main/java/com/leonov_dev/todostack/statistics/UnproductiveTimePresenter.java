package com.leonov_dev.todostack.statistics;

import com.leonov_dev.todostack.data.TasksRepository;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class UnproductiveTimePresenter implements UnproductiveTimeContract.Presenter {

    private final TasksRepository mTasksRepository;

    @Nullable
    private UnproductiveTimeContract.View mView;

    @Inject
    UnproductiveTimePresenter(TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
    }

    @Override
    public void takeView(UnproductiveTimeContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }
}
