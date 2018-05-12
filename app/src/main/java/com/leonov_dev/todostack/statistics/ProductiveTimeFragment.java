package com.leonov_dev.todostack.statistics;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.InstalledApp;
import com.leonov_dev.todostack.di.ActivityScoped;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
@ActivityScoped
public class ProductiveTimeFragment extends dagger.android.support.DaggerFragment implements
    ProductiveTimeContract.View {

    @Inject
    ProductiveTimeContract.Presenter mPresenter;

    @Inject
    public ProductiveTimeFragment() {
        // Required empty public constructor
    }

    private TextView mTasksCounterTextView;
    private TextView mTasksTimeSpentTextView;
    private ListView mProductiveTasksList;
    private ProductiveTasksAdapter mListAdapter;

    private LinearLayout mNoTasksErrorLayout;
    private LinearLayout mProductiveTasksWrapper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_productive_time, container, false);

        mNoTasksErrorLayout = rootView.findViewById(R.id.productive_tasks_error_message);
        mNoTasksErrorLayout.setVisibility(View.GONE);

        mProductiveTasksWrapper = rootView.findViewById(R.id.productive_tasks_wrapper);
        mTasksCounterTextView = rootView.findViewById(R.id.productive_tasks_executed);
        mTasksTimeSpentTextView = rootView.findViewById(R.id.productive_time_spent_on_tasks);
        mProductiveTasksList = rootView.findViewById(R.id.list_productive_tasks_executed);

        mListAdapter = new ProductiveTasksAdapter(getContext(), 0, new ArrayList<ProductivityReport>());
        mProductiveTasksList.setAdapter(mListAdapter);

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
    public void showListOfProductiveTasks(List<ProductivityReport> productivityReports) {
        mNoTasksErrorLayout.setVisibility(View.GONE);
        mProductiveTasksWrapper.setVisibility(View.VISIBLE);

        mListAdapter.clear();
        mListAdapter.addAll(productivityReports);
    }

    @Override
    public void showTimeSpentOnTasks(String time) {
        mTasksTimeSpentTextView.setText(time);
    }

    @Override
    public void showNumberOfTasksPerformed(int numberOfTasks) {
        mTasksCounterTextView.setText(numberOfTasks);
    }

    @Override
    public void showNoTasksExecuted() {
        mProductiveTasksWrapper.setVisibility(View.GONE);
        mNoTasksErrorLayout.setVisibility(View.VISIBLE);
    }

    private class ProductiveTasksAdapter extends ArrayAdapter<ProductivityReport> {

        public ProductiveTasksAdapter(@NonNull Context context, int resource, List<ProductivityReport> apps) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext())
                        .inflate(R.layout.productive_time_item, parent, false);
            }

            TextView taskTitle = listItemView.findViewById(R.id.productive_task_title);
            TextView taskTimeSpent = listItemView.findViewById(R.id.productive_task_time_spent);

            final ProductivityReport productivityReport = getItem(position);

            taskTitle.setText(productivityReport.getTaskTitle());
            taskTimeSpent.setText(productivityReport.getTaskTimeSpent());

            return listItemView;
        }
    }
}
