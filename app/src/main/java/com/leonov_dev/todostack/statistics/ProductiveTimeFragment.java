package com.leonov_dev.todostack.statistics;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leonov_dev.todostack.R;
import com.leonov_dev.todostack.di.ActivityScoped;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 */
@ActivityScoped
public class ProductiveTimeFragment extends dagger.android.support.DaggerFragment {


    @Inject
    public ProductiveTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productive_time, container, false);
    }

}
