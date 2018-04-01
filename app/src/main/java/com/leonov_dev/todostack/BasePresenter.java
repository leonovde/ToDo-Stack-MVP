package com.leonov_dev.todostack;

public interface BasePresenter<T> {

    void takeView(T view);

    void dropView();
}
