package com.example.testrepositoryjava.util;

import com.example.testrepositoryjava.repository.api.ApiResponse;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.lifecycle.LiveData;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(@NotNull Type returnType, @NotNull Annotation[] annotations, @NotNull Retrofit retrofit) {

        // Extract the raw class type from type. For example,the type representing List<? extends Runnable> returns List.class.
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }


        // getParameterUpperBound For example, index 1 of Map<String, ? extends Runnable> returns Runnable.
        // observableType == ApiResponse<List<User>
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);

        // rawObservableType == ApiResponse
        Class<?> rawObservableType = getRawType(observableType);


        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }

        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }

        // bodyType == List<User
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType);
    }
}