package com.leonov_dev.todostack.taskseditor.reminderdialog;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.android.DaggerDialogFragment;

@ActivityScoped
public class ReminderFragment extends DaggerDialogFragment implements ReminderDialogContract.View,
        TimePickerDialog.OnTimeSetListener{

    @Inject
    ReminderDialogContract.Presenter mPresenter;

    @Inject
    public ReminderFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}
