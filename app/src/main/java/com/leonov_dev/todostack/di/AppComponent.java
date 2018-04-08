package com.leonov_dev.todostack.di;

import android.app.Application;

import com.leonov_dev.todostack.ToDoApplication;
import com.leonov_dev.todostack.data.TasksRepository;
import com.leonov_dev.todostack.data.TasksRepositoryModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjection;

@Singleton
@Component(modules = {AndroidSupportInjection.class,
        AppModule.class,
        ActivityBindingModule.class,
        TasksRepositoryModule.class})
public interface AppComponent extends AndroidInjector<ToDoApplication>{

    TasksRepository getTasksRepository();

    @Component.Builder
    interface Builder{

//        AppComponent build();

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
