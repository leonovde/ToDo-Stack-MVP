package com.leonov_dev.todostack.statistics;

import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;

import javax.annotation.Nullable;
import javax.inject.Inject;

@ActivityScoped
public final class ProductiveTimePresenter implements ProductiveTimeContract.Presenter {

    private final TasksRepository mTasksRepository;

    @Nullable
    private ProductiveTimeContract.View mView;

    @Inject
    ProductiveTimePresenter(TasksRepository tasksRepository) {
        mTasksRepository = tasksRepository;
    }

    @Override
    public void takeView(UnproductiveTimeContract.View view) {

    }

    @Override
    public void dropView() {

    }
}
