package com.example.testrepositoryjava.util;

import com.example.testrepositoryjava.repository.api.ApiResponse;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private final Type responseType;

    LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    // R is Response type
    // Returns the value type that this adapter uses when converting the HTTP response body to a Java object.
    @Override
    public Type responseType() {
        return responseType;
    }

    // Returns an instance of T which delegates to call.  T will equal to LiveData<ApiResponse<R>>
    @Override
    public LiveData<ApiResponse<R>> adapt(@NotNull final Call<R> call) {

        return new LiveData<ApiResponse<R>>() {
            AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {

                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(@NotNull Call<R> call, @NotNull Response<R> response) {
                            postValue(new ApiResponse<>(response));
                        }

                        @Override
                        public void onFailure(@NotNull Call<R> call, @NotNull Throwable throwable) {
                            postValue(new ApiResponse<>(throwable));
                        }
                    });
                }
            }
        };
    }
}