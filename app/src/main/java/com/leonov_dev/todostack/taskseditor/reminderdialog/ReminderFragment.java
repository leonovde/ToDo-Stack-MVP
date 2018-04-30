package com.leonov_dev.todostack.taskseditor.reminderdialog;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.utils.CalendarUtils;

import java.util.Calendar;
import java.util.zip.Inflater;

import javax.inject.Inject;

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
    private TextView mDatePickerError;
    private TextView mTimePickerTv;
    private TextView mTimePickerError;

    private Spinner mLocationPickerSpinner;

    private static final String DATE_DIALOG = "DateDialog";
    private static final String TIME_DIALOG = "TimeDialog";


    DatePickerListener mDatePickerListener = new DatePickerListener() {

        @Override
        public void onDateSet(int year, int month, int date) {
            mPresenter.populateDialogDate(year + "/" + month + "/" + date);
        }

    };

    TimePickerListener mTimePickerListener = new TimePickerListener() {
        @Override
        public void onTimeSet(int hourOfDay, int minute) {
            mPresenter.populateDialogTime(hourOfDay + ":" + minute);
        }
    };

    private final String LOG_TAG = ReminderFragment.class.getSimpleName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_reminder, null);

        mRadioGroup = rootView.findViewById(R.id.reminder_radio_group);

        mDateTimeLayout = rootView.findViewById(R.id.reminder_dialog_date_time_layout);
        mDatePickerTv = rootView.findViewById(R.id.time_reminder_date_tv);
        mTimePickerTv = rootView.findViewById(R.id.time_reminder_time_tv);

        mDatePickerError = rootView.findViewById(R.id.time_reminder_date_error_tv);
        mTimePickerError = rootView.findViewById(R.id.time_reminder_time_error_tv);

        mDatePickerError.setVisibility(View.GONE);
        mTimePickerError.setVisibility(View.GONE);

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
                DialogFragment dateReminderFragment = new DateReminderFragment(mDatePickerListener);
                dateReminderFragment.show(getFragmentManager(), DATE_DIALOG);
            }
        });

        /**
         * Open Time Dialog On Click
         */
        mTimePickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timeReminderFragment = new TimerReminderFragment(mTimePickerListener);//mTimeReminderFragment.get();
                timeReminderFragment.show(getFragmentManager(), TIME_DIALOG);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).setPositiveButton(R.string.dialog_button_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // All logic is on resume
            }
        });

        builder.setNegativeButton(R.string.dialog_button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReminderFragment.this.getDialog().cancel();
            }
        });

        builder.setNeutralButton(R.string.dialog_button_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkTimeValidity(
                        mDatePickerTv.getText().toString(),
                        mTimePickerTv.getText().toString(),
                        getString(R.string.select_date_reminder_tv),
                        getString(R.string.select_time_rendinder_tv));
            }
        });

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

    @Override
    public void setDate(String date) {
        mDatePickerError.setVisibility(View.GONE);
        mDatePickerTv.setText(date);
    }

    @Override
    public void setTime(String time) {
        mTimePickerError.setVisibility(View.GONE);
        mTimePickerTv.setText(time);
    }

    @Override
    public void showPickedTimeError() {
        mTimePickerError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPickedDateError() {
        mDatePickerError.setVisibility(View.VISIBLE);
        //TODO make it red or something
    }

    @Override
    public void closeDialog() {
        getDialog().dismiss();
    }

    interface DatePickerListener{

        void onDateSet(int year, int month, int date);

    }

    interface TimePickerListener{

        void onTimeSet(int hourOfDay, int minute);

    }

    @SuppressLint("ValidFragment")
    public static class TimerReminderFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener{

        private TimePickerListener mTimePickerListener;

        public TimerReminderFragment(TimePickerListener listener){
            mTimePickerListener = listener;
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
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mTimePickerListener.onTimeSet(hourOfDay, minute);
        }
    }

    @SuppressLint("ValidFragment")
    public static class DateReminderFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener{

        private DatePickerListener mListener;

        public DateReminderFragment(DatePickerListener listener){
            mListener = listener;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker()
                    .setMinDate(CalendarUtils.getStartOfTodayInMilliseconds());
            return datePickerDialog;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mListener.onDateSet(year, month, dayOfMonth);
        }

    }
}
