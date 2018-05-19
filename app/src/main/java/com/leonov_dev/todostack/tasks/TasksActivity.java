package com.leonov_dev.todostack.tasks;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.data.Task;
import com.leonov_dev.todostack.statistics.StatisticsActivity;
import com.leonov_dev.todostack.taskseditor.TasksEditorActivity;
import com.leonov_dev.todostack.tasksinfo.TasksInfoActivity;
import com.leonov_dev.todostack.utils.CalendarUtils;
import com.leonov_dev.todostack.utils.DateConverter;

import java.text.SimpleDateFormat;
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

    private List<Task> mTasks = new ArrayList<>();

    private LinearLayout mNoTasksLayoutWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mListView = findViewById(R.id.list_of_tasks);

        mNoTasksLayoutWrapper = findViewById(R.id.no_todos_layout_wrapper);
        mNoTasksLayoutWrapper.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mListAdapter = new TasksAdapter(this, 0, new ArrayList<Task>());
        mListView.setAdapter(mListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showTaskInfo(mTasks.get(position).mId);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.checkResult(requestCode, resultCode);
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
        getMenuInflater().inflate(R.menu.tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add_new_task_from_menu){
            mPresenter.addNewTask();
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }
        //TODO add Sortings (by modified, by assigned, past 30 days) + color additional setting
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.drawer_todo:
                break;
            case R.id.drawer_statistics:
                Intent intent = new Intent(this, StatisticsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
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
        mListAdapter.clear();
        mTasks = tasks;
        mListAdapter.addAll(tasks);
        mListView.setVisibility(View.VISIBLE);
        mNoTasksLayoutWrapper.setVisibility(View.GONE);
    }

    @Override
    public void showNoTasks() {
        mListView.setVisibility(View.GONE);
        mNoTasksLayoutWrapper.setVisibility(View.VISIBLE);
        //TODO hide the list view and show message in the center "No TODOs are made"
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(this, TasksEditorActivity.class);
        startActivityForResult(intent, TasksEditorActivity.ADD_TASK_KEY);
    }

    @Override
    public void showStatistics() {
    }

    @Override
    public void showTaskInfo(long taskId) {
        Intent intent = new Intent(this, TasksInfoActivity.class);
        intent.putExtra(TasksInfoActivity.TASK_ID_KEY, taskId);
        startActivityForResult(intent, TasksInfoActivity.SHOW_TASK_INFO);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        //TODO provide callback form AddTask Presenter
        showMessage(getString(R.string.todo_succesfully_created));
    }

    @Override
    public void showSuccessfullyDeletedMessage() {
        //TODO provide undo button
        showMessage(getString(R.string.todo_successfully_deleted));
    }

    public void showMessage(String message){
        Snackbar.make(mListView, message, Snackbar.LENGTH_LONG).show();
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

        private SimpleDateFormat simpleDateFormat = CalendarUtils.getFormatForDate();

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

            taskDate.setText(
                    simpleDateFormat.format(DateConverter.fromTimestamp(task.getModifyDate())));


            return listItemView;
        }
    }

}
