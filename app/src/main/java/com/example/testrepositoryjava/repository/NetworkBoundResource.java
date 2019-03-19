package com.example.testrepositoryjava.repository;

import com.example.testrepositoryjava.AppExecutors;
import com.example.testrepositoryjava.repository.api.ApiResponse;
import com.example.testrepositoryjava.vo.Resource;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();


    @MainThread
    NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();


        // add source Starts to listen the given source LiveData, onChanged observer will be called when source value was changed.
        // remove source means stop listening to that source and remove it

        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });


    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource, newData -> result.setValue(Resource.loading(newData)));


        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    appExecutors.mainThread().execute(() ->
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource(loadFromDb(),
                                    newData -> result.setValue(Resource.success(newData)))
                    );
                });
            } else {
                onFetchFailed();
                result.addSource(dbSource,
                        newData -> result.setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    private void onFetchFailed() {
    }

    LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    private RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @WorkerThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
