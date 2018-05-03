package com.leonov_dev.todostack.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.InstalledApp;
import com.leonov_dev.todostack.di.ActivityScoped;

import java.util.List;

import javax.inject.Inject;
/**
 * A simple {@link Fragment} subclass.
 */
@ActivityScoped
public class UnproductiveTimeFragment extends dagger.android.support.DaggerFragment implements UnproductiveTimeContract.View {

    private ListView mListView;

    private List<InstalledApp> installedApps;

    private final String LOG_TAG = UnproductiveTimeFragment.class.getSimpleName();

    @Inject
    UnproductiveTimeContract.Presenter mPresenter;

    @Inject
    public UnproductiveTimeFragment() {
        Log.e(LOG_TAG, "Unproductive born");
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(LOG_TAG, "View created Unproductive");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unproductive_time, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void showListOfApps(List<InstalledApp> installedApps) {

    }
}
