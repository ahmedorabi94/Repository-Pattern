package com.example.testrepositoryjava.viewmodel;

import android.app.Application;

import com.example.testrepositoryjava.repository.UserRepository;
import com.example.testrepositoryjava.repository.data.User;
import com.example.testrepositoryjava.vo.Resource;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {

    private final LiveData<Resource<List<User>>> usersResponse;

    @Inject
    public MainActivityViewModel(UserRepository userRepository, @NonNull Application application) {
        super(application);

        usersResponse = userRepository.getUsers(application);
    }

    public LiveData<Resource<List<User>>> getModelResonse() {
        return usersResponse;
    }
}
