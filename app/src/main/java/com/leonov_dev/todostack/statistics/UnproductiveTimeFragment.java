package com.leonov_dev.todostack.statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leonov_dev.todostack.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnproductiveTimeFragment extends Fragment {


    public UnproductiveTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_unproductive_time, container, false);
    }

}
