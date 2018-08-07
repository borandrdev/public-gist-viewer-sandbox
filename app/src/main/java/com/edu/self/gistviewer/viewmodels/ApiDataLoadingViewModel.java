package com.edu.self.gistviewer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

class ApiDataLoadingViewModel extends AndroidViewModel {
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    ApiDataLoadingViewModel(@NonNull Application application) {
        super(application);
    }


    void updateIsLoading(boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    @NonNull
    public LiveData<Boolean> isLoading() {
        return isLoading;
    }
}