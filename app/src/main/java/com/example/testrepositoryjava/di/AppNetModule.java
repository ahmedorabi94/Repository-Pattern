package com.example.testrepositoryjava.di;


import android.app.Application;
import androidx.room.Room;

import com.example.testrepositoryjava.repository.api.UserService;
import com.example.testrepositoryjava.repository.db.AppDatabase;
import com.example.testrepositoryjava.repository.db.UserDao;
import com.example.testrepositoryjava.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppNetModule {



    @Singleton
    @Provides
    UserService provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(UserService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(UserService.class);
    }

    @Singleton
    @Provides
    AppDatabase provideDb(Application application) {

        return Room.databaseBuilder(application, AppDatabase.class, "test-repo-java.db")
                .fallbackToDestructiveMigration()
                .build();

    }

    @Singleton
    @Provides
    UserDao provideItemDao(AppDatabase appDatabase) {
        return appDatabase.userDao();
    }


}
