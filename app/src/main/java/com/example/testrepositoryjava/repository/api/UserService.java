package com.example.testrepositoryjava.repository.api;

import com.example.testrepositoryjava.repository.data.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.http.GET;

public interface UserService {

    String BASE_URL = "https://randomapi.com/api/";


    @GET("6de6abfedb24f889e0b5f675edc50deb?fmt=raw&sole")
    LiveData<ApiResponse<List<User>>> getUsers();

}
