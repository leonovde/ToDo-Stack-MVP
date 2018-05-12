package com.leonov_dev.todostack.statistics;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.data.TaskDataSoruce;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.utils.CalendarUtils;

import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void takeView(ProductiveTimeContract.View view) {
        mView = view;
        loadProgress(R.id.today_statistics_filter);
    }



    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadProgress(int periodFilter) {
        long startOfAppUsagePeriod;
        switch (periodFilter){
            case R.id.today_statistics_filter:
                startOfAppUsagePeriod = CalendarUtils.getStartOfTodayInMilliseconds();
                break;
            case R.id.this_week_statistics_filter:
                startOfAppUsagePeriod = CalendarUtils.getStartOfWeekInMilliseconds();
                break;
            case R.id.this_month_statistics_filter:
                startOfAppUsagePeriod = CalendarUtils.getStartOfMonthInMilliseconds();
                break;
            default:
                startOfAppUsagePeriod = CalendarUtils.getStartOfTodayInMilliseconds();
        }
        mTasksRepository.getTasks(startOfAppUsagePeriod, new TaskDataSoruce.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                long productiveTime = 0;
                int productiveTasksCounter = 0;
                SimpleDateFormat format = CalendarUtils.getFormatForTime();
                List<ProductivityReport> productivityReports = new ArrayList<>();
                for (Task task : tasks){
                    //TODO put indicator in DB that time was spent in this period

                    if (task.getTimeSpent() > 0){
                        productiveTime += task.getTimeSpent();
                        ++productiveTasksCounter;
                        Date time = new Date(task.getTimeSpent());
                        String timeSpentString = format.format(time);
                        productivityReports.add(
                                new ProductivityReport(
                                        task.getTitle(),
                                        timeSpentString,
                                        task.getDuration()));
                    }
                }

                Date timeOnAllTasks = new Date (productiveTime);
                String timeOnAllTasksString = format.format(timeOnAllTasks);

                mView.showTimeSpentOnTasks(timeOnAllTasksString);
                mView.showNumberOfTasksPerformed(productiveTasksCounter);
                mView.showListOfProductiveTasks(productivityReports);

            }

            @Override
            public void onDataNotAvailable() {
                mView.showNoTasksExecuted();
            }
        });
    }
}
