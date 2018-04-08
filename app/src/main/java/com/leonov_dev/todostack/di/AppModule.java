package com.leonov_dev.todostack.di;

import android.app.Application;
import android.content.Context;

import dagger.Binds;

public abstract class AppModule {

    @Binds
    abstract Context bingContext(Application application);
}
