package com.example.testrepositoryjava.di;


import android.app.Application;


import com.example.testrepositoryjava.TestApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AppNetModule.class, AndroidInjectionModule.class, MainActivityModule.class})
public interface TaskComponent {

    void inject(TestApp testApp);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);


        TaskComponent build();
    }
}
