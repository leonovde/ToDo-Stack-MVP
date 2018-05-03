package com.leonov_dev.todostack.statistics;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


import com.leonov_dev.todostack.R;

import javax.inject.Inject;


public class StatisticsCategoryAdapter extends FragmentPagerAdapter {
    //TODO switch to FragmentManager, not support one
    private Context mContext;

    private final String LOG_TAG = StatisticsCategoryAdapter.class.getSimpleName();
    //TODO how to inject them?
//    @Inject
//    UnproductiveTimeFragment mUnproductiveTimeFragment;
//
//    @Inject
//    ProductiveTimeFragment mProductiveTimeFragment;

    public StatisticsCategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new UnproductiveTimeFragment();
        }else {
            return new ProductiveTimeFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.unproductive_fragment_name);
        }else {
            return mContext.getString(R.string.productive_fragment_name);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
