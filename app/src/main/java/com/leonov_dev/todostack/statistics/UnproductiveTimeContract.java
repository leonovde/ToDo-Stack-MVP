package com.leonov_dev.todostack.statistics;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;
import com.leonov_dev.todostack.data.InstalledApp;

import java.util.List;

public interface UnproductiveTimeContract {

    interface View extends BaseView<Presenter> {

        void showListOfApps(List<InstalledApp> installedApps);

    }

    interface Presenter extends BasePresenter<View> {

        void loadApps();

    }

}
