package com.example.testrepositoryjava.repository;

import android.app.Application;

import com.example.testrepositoryjava.AppExecutors;
import com.example.testrepositoryjava.repository.api.ApiResponse;
import com.example.testrepositoryjava.repository.api.UserService;
import com.example.testrepositoryjava.repository.data.User;
import com.example.testrepositoryjava.repository.db.UserDao;
import com.example.testrepositoryjava.util.NetworkConnection;
import com.example.testrepositoryjava.vo.Resource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

@Singleton
public class UserRepository {
    private final UserDao userDao;
    private final UserService userService;
    private final AppExecutors appExecutors;


    @Inject
    public UserRepository(AppExecutors appExecutors, UserDao userDao, UserService userService) {
        this.userDao = userDao;
        this.userService = userService;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<User>>> getUsers(Application application) {
        return new NetworkBoundResource<List<User>, List<User>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<User> users) {
                userDao.insertAll(users);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<User> data) {

                if (NetworkConnection.isNetworkAvailable(application) && data != null) {
                    appExecutors.diskIO().execute(() -> userDao.deleteUsers(data));
                    return true;
                }
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return userDao.getAllUsers();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<User>>> createCall() {
                return userService.getUsers();
            }
        }
                .asLiveData();
    }
}




