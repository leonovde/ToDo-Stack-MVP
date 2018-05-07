package com.leonov_dev.todostack.taskseditor.durationdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;
import com.leonov_dev.todostack.taskseditor.reminderdialog.ReminderFragment;
import com.shawnlin.numberpicker.NumberPicker;

import javax.inject.Inject;

@ActivityScoped
public class DurationFragment extends DialogFragment implements DurationContract.View {

    @Inject
    public DurationContract.Presenter mPresenter;

    @Inject
    public DurationFragment(){

    }

    @Override
    public void closeDialog(String time) {
        mTaskDurationSaveListener.onDurationSaved(time);
        getDialog().dismiss();
    }

    public interface TaskDurationSaveListener{

        void onDurationSaved(String duration);

    }

    private final String LOG_TAG = DurationFragment.class.getSimpleName();

    private NumberPicker mHourPicker;
    private NumberPicker mMinutesPicker;

    private TaskDurationSaveListener mTaskDurationSaveListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_duration, null);
        mHourPicker = rootView.findViewById(R.id.duration_hours_picker);
        mMinutesPicker = rootView.findViewById(R.id.duration_minutes_picker);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView).setPositiveButton(R.string.dialog_button_save,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Logic is on Resume
            }
        });

        builder.setNegativeButton(R.string.dialog_button_cancel,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DurationFragment.this.getDialog().cancel();
            }
        });

        builder.setNeutralButton(R.string.dialog_button_delete,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.deleteDuration();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mTaskDurationSaveListener = (DurationFragment.TaskDurationSaveListener) context;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error Attaching listener " + e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        final AlertDialog dialog = (AlertDialog) getDialog();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.formatTime(String.valueOf(mHourPicker.getValue()),
                        String.valueOf(mMinutesPicker.getValue()));
            }
        });

        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }
}
