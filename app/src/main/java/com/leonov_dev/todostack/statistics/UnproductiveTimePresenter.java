package com.leonov_dev.todostack.statistics;

import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.utils.DeviceInfoUtils;

import javax.inject.Inject;

public class UnproductiveTimePresenter implements UnproductiveTimeContract.Presenter {

    private final TasksRepository mTasksRepository;

    private final PackageManager mPackageManager;

    private final String LOG_TAG = UnproductiveTimeContract.class.getSimpleName();

    @Nullable
    private UnproductiveTimeContract.View mView;

    @Inject
    UnproductiveTimePresenter(TasksRepository tasksRepository, PackageManager packageManager) {
        mTasksRepository = tasksRepository;
        mPackageManager = packageManager;
    }

    @Override
    public void takeView(UnproductiveTimeContract.View view) {
        Log.e(LOG_TAG, "took view");
        mView = view;
        loadApps();
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadApps() {
        Log.e(LOG_TAG, "loading list of apps");
        mView.showListOfApps(DeviceInfoUtils.getInstalledApps(mPackageManager));
    }
}
