package com.leonov_dev.todostack.tasks;

import android.support.annotation.NonNull;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.data.Task;

import java.util.List;

public interface TasksContract {

    interface View extends BaseView<Presenter> {

        void showTasks(List<Task> tasks);

        void showNoTasks();

        void showAddTask();

        void showStatistics();

        void showTaskInfo(long taskId);

        void showSuccessfullySavedMessage();

        void showSuccessfullyDeletedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter<View> {

        void loadTasks();

        void addNewTask();

        void openStatistics();

        void takeView(TasksContract.View view);

        void dropView();

        void checkResult(int requestCode, int resultCode);

    }
}
