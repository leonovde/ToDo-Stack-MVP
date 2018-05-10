package com.leonov_dev.todostack.utils;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.leonov_dev.todostack.data.InstalledApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DeviceInfoUtils {

    public static List<InstalledApp> getInstalledApps(PackageManager packageManager) {
        List<InstalledApp> installedApps = new ArrayList<>();
        List<PackageInfo> packs = packageManager.getInstalledPackages(PackageInfo.INSTALL_LOCATION_AUTO);
        for (PackageInfo pack : packs){
            if (isNotSystemPackage(pack)){
                Drawable icon = pack.applicationInfo.loadIcon(packageManager);
                String appName = pack.applicationInfo.loadLabel(packageManager).toString();
                installedApps.add(new InstalledApp(appName, icon, "No Data", 0));
            }
        }
        return installedApps;
    }

    public static List<PackageInfo> getInstalledAppsPackageInfo(PackageManager packageManager) {
        List<PackageInfo> installedAppsPackageInfo = new ArrayList<>();
        List<PackageInfo> packs = packageManager.getInstalledPackages(PackageInfo.INSTALL_LOCATION_AUTO);
        for (PackageInfo pack : packs){
            if (isNotSystemPackage(pack)){
                installedAppsPackageInfo.add(pack);
            }
        }
        return installedAppsPackageInfo;
    }

    public static boolean isNotSystemPackage(PackageInfo info) {
        return ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0);
    }

    public static List<UsageStats> getUsageInfo(long from, long until, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            UsageStatsManager mUsageStatsManager =
                    (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                    from, until);

            if (stats.size() == 0) {
                return null;
            }
            return stats;
        }
        return null;
    }

}
