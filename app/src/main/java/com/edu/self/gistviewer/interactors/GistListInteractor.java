package com.edu.self.gistviewer.interactors;

import android.support.annotation.NonNull;
import android.util.Log;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.data.model.Gist;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GistListInteractor implements DataFetching {
    private static final String TAG = GistListInteractor.class.getSimpleName();


    private final GistService service;

    private AtomicBoolean isFetching = new AtomicBoolean(false);

    @SuppressWarnings("WeakerAccess")
    public GistListInteractor(@NonNull GistService service) {
        this.service = service;
    }

    void fetch(int page, int countPerPage, @NonNull FetchCallback<List<Gist>> callback) {
        if (!isFetching() && (page > 0) && (countPerPage > 0)) {
            isFetching.set(true);
            service.getPublicGists(page, countPerPage).enqueue(new Callback<List<Gist>>() {
                @Override
                public void onResponse(@NonNull Call<List<Gist>> call, @NonNull Response<List<Gist>> response) {
                    Log.v(TAG, "Fetch response: " + response.code());
                    isFetching.set(false);
                    if (response.isSuccessful()) {
                        List<Gist> gists = response.body();
                        callback.onDataFetched(gists);
                    } else {
                        Log.e(TAG, "Failed to fetch gists: " + response.code());
                        callback.onDataFetched(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Gist>> call, @NonNull Throwable t) {
                    isFetching.set(false);
                    Log.e(TAG, "Failed to fetch gists", t);
                    callback.onDataFetched(null);
                }
            });
        }
    }


    @Override
    public boolean isFetching() {
        return isFetching.get();
    }
}