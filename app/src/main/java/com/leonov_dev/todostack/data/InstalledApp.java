package com.leonov_dev.todostack.data;

import android.graphics.drawable.Drawable;

public class InstalledApp {

    private String name;

    private Drawable icon;

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
}
