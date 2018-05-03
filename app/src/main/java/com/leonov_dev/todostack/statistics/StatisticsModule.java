package com.leonov_dev.todostack.statistics;

import android.content.Context;
import android.content.pm.PackageManager;

import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.di.FragmentScoped;

import javax.inject.Inject;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StatisticsModule {

    @ActivityScoped
    @Provides
    static PackageManager providePackageManage(StatisticsActivity activity){
        return activity.getPackageManager();
    }

    @ActivityScoped
    @Binds
    abstract UnproductiveTimeContract.Presenter getUnproductiveTimePresenter(UnproductiveTimePresenter presenter);

    @ActivityScoped
    @Binds
    abstract ProductiveTimeContract.Presenter getProductiveTimePresenter(ProductiveTimePresenter presenter);

    @FragmentScoped
    @ContributesAndroidInjector
    abstract UnproductiveTimeFragment getUnproductiveTimeFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract ProductiveTimeFragment getProductiveTimeFragment();
}
