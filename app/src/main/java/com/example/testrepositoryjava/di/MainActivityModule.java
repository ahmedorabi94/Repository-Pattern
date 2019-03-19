package com.example.testrepositoryjava.di;



import com.example.testrepositoryjava.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();
}
