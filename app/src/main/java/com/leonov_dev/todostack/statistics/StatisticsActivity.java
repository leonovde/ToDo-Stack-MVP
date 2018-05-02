package com.leonov_dev.todostack.statistics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.leonov_dev.todostack.R;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Show statistics for tasks.
 */
public class StatisticsActivity extends DaggerAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;

    private ViewPager mViewPagerMode;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.statistics_toolbar);

        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_statistics);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //TODO make drawer pick the current section only
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_task);
        navigationView.setNavigationItemSelectedListener(this);

        mViewPagerMode = findViewById(R.id.modes_view_pager);
        StatisticsCategoryAdapter modeAdapter =
                new StatisticsCategoryAdapter(this, getSupportFragmentManager());
        mViewPagerMode.setAdapter(modeAdapter);

        mTabLayout = findViewById(R.id.modes_tab_view);
        mTabLayout.setupWithViewPager(mViewPagerMode);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_new_task_from_menu){

            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }
        //TODO add Sortings (by modified, by assigned, past 30 days) + color additional setting
        return super.onOptionsItemSelected(item);
    }


}
