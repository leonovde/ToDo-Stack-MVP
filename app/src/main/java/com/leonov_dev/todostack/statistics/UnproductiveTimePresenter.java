package com.leonov_dev.todostack.statistics;

import android.app.usage.UsageStats;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.InstalledApp;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.utils.CalendarUtils;
import com.leonov_dev.todostack.utils.DeviceInfoUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class UnproductiveTimePresenter implements UnproductiveTimeContract.Presenter {

    private final TasksRepository mTasksRepository;

    private final PackageManager mPackageManager;

    private final Context mContext;

    private final String LOG_TAG = UnproductiveTimeContract.class.getSimpleName();

    @Nullable
    private UnproductiveTimeContract.View mView;

    @Inject
    UnproductiveTimePresenter(TasksRepository tasksRepository, PackageManager packageManager,
                              Context context) {
        mTasksRepository = tasksRepository;
        mPackageManager = packageManager;
        mContext = context;
    }

    @Override
    public void takeView(UnproductiveTimeContract.View view) {
        mView = view;
        loadApps(R.id.today_statistics_filter);
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadApps(int periodFilter) {
        if (mView != null) {
            //TODO add time interval value
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                //Show list of apps on phone
                mView.showListOfApps(DeviceInfoUtils.getInstalledApps(mPackageManager));
            } else {

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

                //Get Usage Statistics
                List<UsageStats> usageStats = DeviceInfoUtils.getUsageInfo(
                        startOfAppUsagePeriod,
                        CalendarUtils.getCurrentTime(),
                        mContext);
                //If statistic is empty, show Error message and proceed to Settings page
                if (usageStats == null || usageStats.isEmpty() || usageStats.size() == 0){
                    mView.showPermissionError();
                    return;
                }

                List<PackageInfo> installedApps =
                        DeviceInfoUtils.getInstalledAppsPackageInfo(mPackageManager);

                HashMap<String, InstalledApp> appUsageMap = new HashMap<>();

                //Filling all installed apps, time usage 0
                for (PackageInfo pInfo : installedApps){
                    String packageName = pInfo.packageName;
                    String appName = pInfo.applicationInfo.loadLabel(mPackageManager).toString();
                    Drawable appIcon = pInfo.applicationInfo.loadIcon(mPackageManager);
                    InstalledApp currentApp = new InstalledApp(appName, appIcon,
                            mContext.getString(R.string.duration_default_value), 0);
                    appUsageMap.put(packageName, currentApp);
                }

                //Depending on Filter:
                SimpleDateFormat formatter = CalendarUtils.getFormatForTime();
                Date time;
                for (UsageStats usageStat : usageStats) {
                        String packageName = usageStat.getPackageName();
                    if (appUsageMap.containsKey(packageName)) {
                        //TODO some apps are listed twice fix it
                        /**
                         * If App that has data about duration is in list
                         * get the usage duration, concert with time stamp and put back in map
                         *
                         * Create totalDuration length
                         */
                        InstalledApp bufApp = appUsageMap.get(packageName);
                        bufApp.setUsageLong(usageStat.getTotalTimeInForeground());
                        time = new Date(bufApp.getUsageLong());
                        bufApp.setUsage(formatter.format(time));
                        appUsageMap.put(packageName, bufApp);
                    }
                }
                List<InstalledApp> listOfApps = new ArrayList(appUsageMap.values());
                Collections.sort(listOfApps);
                mView.showListOfApps(listOfApps);
                long totalDuration = 0;
                for (InstalledApp installedApp : listOfApps){
                    totalDuration += installedApp.getUsageLong();
                }
                mView.showUnproductiveTime(formatter.format(new Date(totalDuration)));
            }
        }
    }

}
