package com.leonov_dev.todostack.taskseditor.reminderdialog;


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.di.FragmentScoped;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.DaggerDialogFragment;

@ActivityScoped
public class ReminderFragment extends DaggerDialogFragment implements ReminderDialogContract.View{

    @Inject
    ReminderDialogContract.Presenter mPresenter;

    @Inject
    public ReminderFragment(){

    }

//    @Inject
//    Lazy<TimerReminderFragment> mTimeReminderFragment;

    private RadioGroup mRadioGroup;

    private LinearLayout mDateTimeLayout;
    private TextView mDatePickerTv;
    private TextView mTimePickerTv;

    private Spinner mLocationPickerSpinner;

    private static final String DATE_DIALOG = "DateDialog";
    private static final String TIME_DIALOG = "TimeDialog";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reminder, null);

        mRadioGroup = rootView.findViewById(R.id.reminder_radio_group);

        mDateTimeLayout = rootView.findViewById(R.id.reminder_dialog_date_time_layout);
        mDatePickerTv = rootView.findViewById(R.id.time_reminder_date_tv);
        mTimePickerTv = rootView.findViewById(R.id.time_reminder_time_tv);

        mLocationPickerSpinner = rootView.findViewById(R.id.location_spinner);
        mLocationPickerSpinner.setVisibility(View.GONE);

        //TODO confused here. It so redundant to call Presenter
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.time_reminder_rb:
                        mPresenter.setReminderDialogUiType(ReminderDialogUiType.DATE_TIME);
                        break;
                    case R.id.location_reminder_rb:
                        mPresenter.setReminderDialogUiType(ReminderDialogUiType.LOCATION);
                        break;
                    default:
                        mPresenter.setReminderDialogUiType(ReminderDialogUiType.DATE_TIME);
                        break;
                }
                mPresenter.setUpUI();
            }
        });

        /**
         * Open Date Dialog On Click
         */
        mDatePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /**
         * Open Time Dialog On Click
         */
        mTimePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimerReminderFragment();//mTimeReminderFragment.get();
                newFragment.show(getFragmentManager(), TIME_DIALOG);
            }
        });


        return rootView;
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
    public void showDateTimePicker() {
        mLocationPickerSpinner.setVisibility(View.GONE);
        mDateTimeLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLocationPicker() {
        mDateTimeLayout.setVisibility(View.GONE);
        mLocationPickerSpinner.setVisibility(View.VISIBLE);
    }

    //TODO Should this be injected? Why? Where?
//    @FragmentScoped
    public static class TimerReminderFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

//        @Inject
//        public TimerReminderFragment(){
//
//        }

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
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    }
}
