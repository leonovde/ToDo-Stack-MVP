package com.leonov_dev.todostack.statistics;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.leonov_dev.todostack.R;

import dagger.android.support.DaggerAppCompatActivity;

public class StatisticsActivity extends DaggerAppCompatActivity {

    private ViewPager mViewPagerMode;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mViewPagerMode = findViewById(R.id.statistics_view_pager);
        StatisticsCategoryAdapter modeAdapter = new StatisticsCategoryAdapter(this, getSupportFragmentManager());
        mViewPagerMode.setAdapter(modeAdapter);

        mTabLayout = findViewById(R.id.statistics_tab_view);
        mTabLayout.setupWithViewPager(mViewPagerMode);
    }
}
