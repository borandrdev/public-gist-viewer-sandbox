package com.edu.self.gistviewer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.edu.self.gistviewer.App;
import com.edu.self.gistviewer.data.model.CommitDetails;
import com.edu.self.gistviewer.data.model.GistDetails;
import com.edu.self.gistviewer.data.model.GistFile;
import com.edu.self.gistviewer.interactors.GistDetailsInteractor;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GistDetailsViewModel extends ApiDataLoadingViewModel {
    @SuppressWarnings("unused")
    private static final String TAG = GistDetailsViewModel.class.getSimpleName();

    @Inject
    GistDetailsInteractor interactor;

    private final MutableLiveData<String> gistName = new MutableLiveData<>();
    private final MutableLiveData<String> gistUser = new MutableLiveData<>();
    private final MutableLiveData<List<CommitDetails>> commits = new MutableLiveData<>();
    private final MutableLiveData<Map<String, GistFile>> files = new MutableLiveData<>();

    public GistDetailsViewModel(@NonNull Application application) {
        super(application);
        ((App) application).getDependencies().getAppComponent().inject(this);
    }

    public void getGistDetailsForId(@Nullable String gistId) {
        if ((gistId != null) && !gistId.isEmpty()) {
            interactor.fetch(gistId, this::onDetailsFetched);
        }
    }


    private void onDetailsFetched(@Nullable GistDetails gistDetails) {
        updateIsLoading(interactor.isFetching());
        if (gistDetails != null) {
//            Log.v(TAG, "gistDetails: " + gistDetails);
            gistName.setValue(gistDetails.getName());
            gistUser.setValue(gistDetails.getOwner().getName());
            commits.setValue(gistDetails.getCommits());
            files.setValue(gistDetails.getContents());
        }
    }

    @NonNull
    public MutableLiveData<String> getGistName() {
        return gistName;
    }

    @NonNull
    public MutableLiveData<String> getOwner() {
        return gistUser;
    }

    @NonNull
    public MutableLiveData<List<CommitDetails>> getCommits() {
        return commits;
    }

    @NonNull
    public MutableLiveData<Map<String, GistFile>> getFiles() {
        return files;
    }
}