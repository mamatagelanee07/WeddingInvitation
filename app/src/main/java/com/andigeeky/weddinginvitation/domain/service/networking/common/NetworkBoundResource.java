/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andigeeky.weddinginvitation.domain.service.networking.common;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.google.android.gms.tasks.Task;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <RequestType>
 */
public abstract class NetworkBoundResource<RequestType> {
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<RequestType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        fetchFromNetwork();
    }

    @MainThread
    private void setValue(Resource<RequestType> newValue) {
        if (!Objects.equals(result.getValue(), newValue)) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork() {
        LiveData<Task<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            //noinspection ConstantConditions
            if (response.isSuccessful()) {
                appExecutors.mainThread().execute(() -> result.setValue(Resource.success(processResponse(response))));
            } else {
                appExecutors.mainThread().execute(() -> result.setValue(Resource.error(response.getException().getMessage(), null)));
            }
        });
    }

    public LiveData<Resource<RequestType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(Task<RequestType> response) {
        return response.getResult();
    }

    @NonNull
    @MainThread
    protected abstract LiveData<Task<RequestType>> createCall();
}
