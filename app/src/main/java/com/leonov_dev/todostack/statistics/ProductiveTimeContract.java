package com.leonov_dev.todostack.statistics;

import com.leonov_dev.todostack.BasePresenter;
import com.leonov_dev.todostack.BaseView;

public interface ProductiveTimeContract {

    interface View extends BaseView<UnproductiveTimeContract.Presenter> {

    }

    interface Presenter extends BasePresenter<UnproductiveTimeContract.View> {

    }

}
