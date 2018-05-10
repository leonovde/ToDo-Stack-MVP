package com.leonov_dev.todostack.data;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class InstalledApp implements Comparable<InstalledApp> {

    private String name;
    private Drawable icon;
    private String usage;
    private long usageLong;


    public InstalledApp (String name, Drawable icon, String usage, long usageLong){
        this.name = name;
        this.icon = icon;
        this.usage = usage;
        this.usageLong = usageLong;
    }

    public InstalledApp (String name, Drawable icon){
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public long getUsageLong(){
        return usageLong;
    }

    public void setUsageLong(long usageLong){
        this.usageLong = usageLong;
    }

    @Override
    public int compareTo(@NonNull InstalledApp app) {
        return (int)(app.usageLong - this.usageLong);
    }
}
