package com.leonov_dev.todostack.statistics;

import android.content.Intent;
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
import android.util.Log;
import android.view.MenuItem;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.tasks.TasksActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Show statistics for tasks.
 */
public class StatisticsActivity extends DaggerAppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mDrawerLayout;

    private ViewPager mViewPagerMode;
    private TabLayout mTabLayout;

    private final String LOG_TAG = StatisticsActivity.class.getSimpleName();

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

        Log.e(LOG_TAG, "Reached adapter ");

        //How to provide tho??
        StatisticsCategoryAdapter modeAdapter =
                new StatisticsCategoryAdapter(this, getSupportFragmentManager());
        Log.e(LOG_TAG, "Reached adapter 2");
        mViewPagerMode.setAdapter(modeAdapter);
        Log.e(LOG_TAG, "Reached adapter 3");
        mTabLayout = findViewById(R.id.modes_tab_view);
        mTabLayout.setupWithViewPager(mViewPagerMode);
        Log.e(LOG_TAG, "Reached adapter 4");
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

                        int id = menuItem.getItemId();
                        switch (id){
                            case R.id.drawer_todo:
                                Intent intent = new Intent(StatisticsActivity.this, TasksActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.drawer_statistics:
                                break;
                            default:
                                break;
                        }

                        menuItem.setChecked(true);
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_todo:
                Intent intent = new Intent(this, TasksActivity.class);
                startActivity(intent);
                break;
            case R.id.drawer_statistics:
                break;
            default:
                break;
        }

        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
