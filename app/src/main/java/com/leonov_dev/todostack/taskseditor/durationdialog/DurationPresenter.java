package com.leonov_dev.todostack.taskseditor.durationdialog;

import android.content.Context;
import android.util.Log;

import com.leonov_dev.todostack.R;

import javax.inject.Inject;

public class DurationPresenter implements DurationContract.Presenter {

    private static final String LOG_TAG = DurationFragment.class.getSimpleName();
    private DurationContract.View mView;

    private Context mContext;

    @Inject
    public DurationPresenter(Context context){
        mContext = context;
    }

    @Override
    public void takeView(DurationContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    //TODO change to formatter or regular expression
    @Override
    public void formatTime(String hours, String minutes) {
        String formHours;
        String formMins;
        Log.e(LOG_TAG, "Hours value " + hours);
        Log.e(LOG_TAG, "Minutes val " + minutes);
        if (hours.length() <= 1){
            formHours = "0" + hours;
        } else {
            formHours = hours;
        }
        if (minutes.length() <= 1){
            formMins = "0" + minutes;
        } else {
            formMins = minutes;
        }
        mView.closeDialog(formHours + ":" + formMins);
    }

    @Override
    public void deleteDuration() {
        String defaultTime = mContext.getString(R.string.duration_default_value);
        mView.closeDialog(defaultTime);
    }
}
