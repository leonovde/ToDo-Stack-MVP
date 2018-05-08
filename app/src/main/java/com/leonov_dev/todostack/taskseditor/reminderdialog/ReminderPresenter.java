package com.leonov_dev.todostack.taskseditor.reminderdialog;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.di.FragmentScoped;
import com.leonov_dev.todostack.utils.CalendarUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

public class ReminderPresenter implements ReminderDialogContract.Presenter {

    ReminderDialogContract.View mView;

    private final String LOG_TAG = ReminderPresenter.class.getSimpleName();

    private Context mContext;

    private Bundle mExtras;

    @Inject
    ReminderPresenter(Context context){
        mContext = context;
    }

    private ReminderDialogUiType mReminderDialogUiType;

    @Override
    public void takeView(ReminderDialogContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void setReminderDialogUiType(ReminderDialogUiType type) {
        mReminderDialogUiType = type;
    }


    @Override
    public ReminderDialogUiType getReminderDialogUiType() {
        return mReminderDialogUiType;
    }

    @Override
    public void setUpUI() {
        switch (mReminderDialogUiType){
            case DATE_TIME:
                mView.showDateTimePicker();
                break;
            case LOCATION:
                mView.showLocationPicker();
                break;
            default:
                mView.showDateTimePicker();
        }
    }

    @Override
    public void populateDialogDate(String date) {
        mView.setDate(date);
    }

    @Override
    public void populateDialogTime(String time) {
        mView.setTime(time);
    }

    @Override
    public void checkTimeValidity(String date, String time, String standardDate, String standardTime) {
        checkTimeValidityForResult(date, time, standardDate, standardTime, false);
    }

    //TODO should have only this method
    @Override
    public void checkTimeValidityAndClose(String date, String time, String standardDate, String standardTime) {
        checkTimeValidityForResult(date, time, standardDate, standardTime, true);
    }

    @Override
    public void deleteReminder() {
        mView.closeDialogAndDeleteTime(mContext.getString(R.string.reminder_caption));
    }

    @Override
    public void takeView(ReminderDialogContract.View view, Bundle extras) {
        mView = view;
        mExtras = extras;
        if (!mExtras.isEmpty() && mExtras != null) {
            populateReminder(extras);
        }
    }

    private void populateReminder(Bundle extras){
        String reminderCondition = extras.getString(ReminderFragment.REMINDER_KEY);

        if (isDateReminder(reminderCondition)){
            //Set up date and time
            String[] dateAndTime = reminderCondition.split(" ");
            if (dateAndTime.length == 2) {
                mView.showDateTimePicker();
                mView.setDate(dateAndTime[0]);
                mView.setTime(dateAndTime[1]);
            }
        } else {
            //TODO this show doesn't change  the radio button.
            //It is a location so only 2 fields
            mView.showLocationPicker();
            mView.setLocation(reminderCondition);
        }
    }

    private boolean isDateReminder(String date){
        SimpleDateFormat formatter = CalendarUtils.getFormatForDateAndTime();
        try {
            Date formattedDate = formatter.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void checkTimeValidityForResult(String date,
                                            String time,
                                            String standardDate,
                                            String standardTime,
                                            boolean isToCloseDialog){
        if (standardDate.equals(date)) {
            mView.showPickedDateError();
            if (standardTime.equals(time)) {
                mView.showPickedTimeError();
                return;
            }
            return;
        }

        //Because date can be set but time not
        if (standardTime.equals(time)) {
            mView.showPickedTimeError();
            return;
        }

        String fullDateString = date + " " + time;
        Date pickedDateTime = null;
        SimpleDateFormat formatter = CalendarUtils.getFormatForDateAndTime();
        try {
            pickedDateTime = formatter.parse(fullDateString);
        }catch (Exception e){
            Log.e(LOG_TAG, "Date Parsing Exception " + e);
        }

        Date currentDateTime = new Date(CalendarUtils.getCurrentTime());
        Log.e(LOG_TAG, "Current time " + currentDateTime.toString());
        Log.e(LOG_TAG, "Picked time " + pickedDateTime.toString());
        if (pickedDateTime.before(currentDateTime)){
            mView.showPickedTimeError();
        } else {
            if (isToCloseDialog) {
                mView.closeDialog();
            }
        }
    }

}
