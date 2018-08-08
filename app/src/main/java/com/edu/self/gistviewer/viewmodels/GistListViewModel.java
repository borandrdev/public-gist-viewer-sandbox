package com.edu.self.gistviewer.viewmodels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.edu.self.gistviewer.App;
import com.edu.self.gistviewer.data.model.Gist;
import com.edu.self.gistviewer.interactors.PaginatingGistListInteractor;

import java.util.List;

import javax.inject.Inject;

public class GistListViewModel extends ApiDataLoadingViewModel {
    @SuppressWarnings("unused")
    private static final String TAG = GistListViewModel.class.getSimpleName();

    private final MutableLiveData<List<Gist>> gists = new MutableLiveData<>();
    private final MutableLiveData<Integer> lastFetchedGistsCount = new MutableLiveData<>();

    @Inject
    PaginatingGistListInteractor gistListInteractor;


    public GistListViewModel(@NonNull Application application) {
        super(application);
        ((App) getApplication()).getDependencies().getGistListComponent().inject(this);
        gistListInteractor.fetchFirstGists(this::onGistListFetched);
    }


    @NonNull
    public synchronized LiveData<List<Gist>> getGists() {
        return gists;
    }


    private void onGistListFetched(@Nullable List<Gist> result) {
        updateIsLoading(gistListInteractor.isFetching());
        if (gists.getValue() == null) {
            gists.setValue(result);
        }
        lastFetchedGistsCount.setValue(gistListInteractor.getLastFetchedGistsCount());
    }

    public void fetchMoreGists() {
        gistListInteractor.fetchMore(this::onGistListFetched);
        updateIsLoading(gistListInteractor.isFetching());
    }

    public LiveData<Integer> getLastFetchedGistsCount() {
        return lastFetchedGistsCount;
    }

    public void resetCache() {
        gistListInteractor.resetCache();
        gists.setValue(gistListInteractor.getCachedGists());
        gistListInteractor.fetchFirstGists(this::onGistListFetched);
    }
}