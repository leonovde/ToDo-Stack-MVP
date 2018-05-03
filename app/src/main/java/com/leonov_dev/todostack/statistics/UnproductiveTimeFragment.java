package com.leonov_dev.todostack.statistics;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.InstalledApp;
import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.tasks.TasksActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
/**
 * A simple {@link Fragment} subclass.
 */
@ActivityScoped
public class UnproductiveTimeFragment extends dagger.android.support.DaggerFragment implements UnproductiveTimeContract.View {

    private ListView mListView;

    private AppsAdapter mListAdapter;

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
        View rootView = inflater.inflate(R.layout.fragment_unproductive_time, container, false);
        mListView = rootView.findViewById(R.id.list_of_installed_apps);

        mListAdapter = new UnproductiveTimeFragment.AppsAdapter(getContext(), 0, new ArrayList<InstalledApp>());
        mListView.setAdapter(mListAdapter);

        return rootView;
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
        mListAdapter.clear();
        mListAdapter.addAll(installedApps);
    }

    private class AppsAdapter extends ArrayAdapter<InstalledApp>{

        public AppsAdapter(@NonNull Context context, int resource, List<InstalledApp> apps) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext())
                        .inflate(R.layout.installed_app_item, parent, false);
            }

            ImageView icon = listItemView.findViewById(R.id.app_icon_iv);
            TextView appName = listItemView.findViewById(R.id.app_name_tv);

            final InstalledApp appInfo = getItem(position);

            icon.setImageDrawable(appInfo.getIcon());
            appName.setText(appInfo.getName());


            return listItemView;
        }
    }
}
