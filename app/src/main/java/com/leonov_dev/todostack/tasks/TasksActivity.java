package com.leonov_dev.todostack.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.taskseditor.TasksEditorActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class TasksActivity extends DaggerAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        TasksContract.View{

    @Inject
    TasksContract.Presenter mPresenter;

    private final String LOG_TAG = TasksActivity.class.getSimpleName();
    private ListView mListView;
    private TasksAdapter mListAdapter;

    //private TextView mNotTasksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_of_tasks);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mListAdapter = new TasksAdapter(this, 0, new ArrayList<Task>());
        mListView.setAdapter(mListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
        mPresenter.loadTasks();
        mListAdapter.clear();
        Log.e(LOG_TAG, "0000 Resumed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_new_task_from_menu){
            Log.e(LOG_TAG, "Triggered menu option selection");
            mPresenter.addNewTask();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }
        //TODO add Sortings (by modified, by assigned, past 30 days) + color additional setting
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    -----------Activity--------------
     */
    @Override
    public void showTasks(List<Task> tasks) {
        mListAdapter.addAll(tasks);
    }

    @Override
    public void showNoTasks() {
        //TODO hide the list view and show message in the center "No TODOs are made"
    }

    @Override
    public void showAddTask() {
        Log.e(LOG_TAG, "Triggered start of an TasksEditorActivity");
        Intent intent = new Intent(this, TasksEditorActivity.class);
        //TODO startActivityForResult(intent, TasksEditorActivity.SOME_CONSTANT);
        startActivity(intent);
    }

    @Override
    public void showTaskDetailsUi(String taskId) {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showFilteringPopUpMenu() {

    }

    //----- Tasks Adapter ----

    //Declare static class as it is a member of an Activity
    private static class TasksAdapter extends ArrayAdapter<Task>{

        public TasksAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Task> tasks) {
            super(context, 0, tasks);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null){
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
            }

            final Task task = getItem(position);

            TextView taskTitle = listItemView.findViewById(R.id.task_title_preview);
            TextView taskDate = listItemView.findViewById(R.id.task_made_time_preview);

            taskTitle.setText(task.getTitle());
            //TODO should be a date converted to dd/mm/yyyy
            taskDate.setText(String.valueOf(task.getModifyDate()));

            return listItemView;
        }
    }

}
