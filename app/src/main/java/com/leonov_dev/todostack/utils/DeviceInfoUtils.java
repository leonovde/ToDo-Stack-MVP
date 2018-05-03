package com.leonov_dev.todostack.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.leonov_dev.todostack.data.InstalledApp;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfoUtils {
    public static List<InstalledApp> getInstalledApps(PackageManager packageManager) {
        List<InstalledApp> installedApps = new ArrayList<>();
        List<PackageInfo> packs = packageManager.getInstalledPackages(PackageInfo.INSTALL_LOCATION_AUTO);
        for (PackageInfo pack : packs){
            if (isSystemPackage(pack)){
                Drawable icon = pack.applicationInfo.loadIcon(packageManager);
                String appName = pack.applicationInfo.loadLabel(packageManager).toString();
                installedApps.add(new InstalledApp(appName, icon));
            }
        }
        return installedApps;
    }

    public static boolean isSystemPackage(PackageInfo info) {
        return ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }
}
