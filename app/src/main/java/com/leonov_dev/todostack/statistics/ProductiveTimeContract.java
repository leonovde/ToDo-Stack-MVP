package com.leonov_dev.todostack.statistics;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

import java.util.List;

public interface ProductiveTimeContract {

    interface View extends BaseView<ProductiveTimeContract.Presenter> {

        void showListOfProductiveTasks(List<ProductivityReport> productivityReports);

        void showTimeSpentOnTasks(String time);

        void showNumberOfTasksPerformed(int numberOfTasks);

        void showNoTasksExecuted();

    }

    interface Presenter extends BasePresenter<ProductiveTimeContract.View> {

        void loadProgress(int periodFilter);

    }

}
