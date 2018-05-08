package com.leonov_dev.todostack.taskseditor;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.taskseditor.durationdialog.DurationFragment;
import com.leonov_dev.todostack.taskseditor.reminderdialog.ReminderFragment;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class TasksEditorActivity extends DaggerAppCompatActivity implements TasksEditorContract.View,
    ReminderFragment.TaskReminderSaveListener, DurationFragment.TaskDurationSaveListener{

    @Inject
    TasksEditorContract.Presenter mPresenter;

    @Inject
    Lazy<ReminderFragment> mReminderFragment;

    @Inject
    Lazy<DurationFragment> mDurationFragment;


    public static final int ADD_TASK_KEY = 1;
    public static final int EDIT_TASK_KEY = 2;

    public static final String TASK_EDIT_KEY = "edit_task";

    public static final String REMINDER_DIALOG_TAG = "ReminderDialog";
    public static final String DURATION_DIALOG_TAG = "DurationDialog";


    private EditText mTitleEditText;
    private EditText mDescriptionText;
    private RelativeLayout mReminderRelativeLayout;
    private TextView mReminderTextView;
    private RelativeLayout mDurationRelativeLayout;
    private TextView mDurationLeftSpentTextView;
    private TextView mDurationTextView;

    private ActionBar mActionBar;

    private String LOG_TAG = TasksEditorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_editor);

        // Set up the toolbar.
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle(R.string.new_task_activity_title);

        mTitleEditText = findViewById(R.id.add_task_title);
        mDescriptionText = findViewById(R.id.add_task_description);

        mReminderRelativeLayout = findViewById(R.id.reminder_linear_layout);
        mReminderTextView = findViewById(R.id.reminder_condition);

        mDurationRelativeLayout = findViewById(R.id.duration_linear_layout);
        mDurationLeftSpentTextView = findViewById(R.id.duration_left_spent_text_view);
        mDurationTextView = findViewById(R.id.duration_text_view);

        mDurationLeftSpentTextView.setVisibility(View.GONE);

        mReminderRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mPresenter.populateReminder(mReminderTextView.getText().toString());
                mPresenter.prepareReminderDialog(mReminderTextView.getText().toString());
//                DialogFragment newFragment = mReminderFragment.get();
//                newFragment.show(getFragmentManager(), REMINDER_DIALOG_TAG);
            }
        });

        mDurationRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = mDurationFragment.get();
                newFragment.show(getFragmentManager(), DURATION_DIALOG_TAG);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_new_task_menu_item){
            mPresenter.insertTask(
                    mTitleEditText.getText().toString(),
                    mDescriptionText.getText().toString(),
                    mReminderTextView.getText().toString(),
                    mDurationTextView.getText().toString());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEmptyTaskError() {
        Toast.makeText(this,
                R.string.error_message_description_needed, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showTasksList() {
        //Deprecated, feedback added onBackPressed();
        //TODO question is this ok to use static vars of another fragment ? :?
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void showTaskInfo() {
        finish();
    }

    @Override
    public void setTitle(String title) {
        mTitleEditText.setText(title);
    }

    @Override
    public void setDescription(String description) {
        mDescriptionText.setText(description);
    }

    @Override
    public void setReminder(String time) {
        mReminderTextView.setText(time);
    }

    @Override
    public void setDuration(String duration) {
        mDurationTextView.setText(duration);
    }

    @Override
    public void showReminderDialog(Bundle extraData) {
        DialogFragment newFragment = mReminderFragment.get();
        newFragment.setArguments(extraData);
        newFragment.show(getFragmentManager(), REMINDER_DIALOG_TAG);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    //TODO These one line calls... ??? Is that correct?
    @Override
    public void onReminderSaved(String reminder) {
        mPresenter.populateReminder(reminder);
    }

    @Override
    public void onDurationSaved(String duration) {
        mPresenter.populateDuration(duration);
    }

    /** TODO create a Reminder.
     * Save a Task with the reminder
     * Populate the task when opening existing task
     * Populate the dialog while opening the task
     * Set the assigned date to the reminder date
     */


}
