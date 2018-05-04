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
            if (isNotSystemPackage(pack)){
                Drawable icon = pack.applicationInfo.loadIcon(packageManager);
                String appName = pack.applicationInfo.loadLabel(packageManager).toString();
                installedApps.add(new InstalledApp(appName, icon));
            }
        }
        return installedApps;
    }

    public static boolean isNotSystemPackage(PackageInfo info) {
        return ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    //YOUTUBE, CHROME
    public static boolean isAnException(PackageInfo info) {
        if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){

        }
        return true;
    }
}
