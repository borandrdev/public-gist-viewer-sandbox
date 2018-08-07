package com.edu.self.gistviewer.interactors;

import android.support.annotation.NonNull;
import android.util.Log;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.data.model.GistDetails;

import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GistDetailsInteractor {
    private static final String TAG = GistDetailsInteractor.class.getSimpleName();

    private final GistService service;

    private AtomicBoolean isFetching = new AtomicBoolean(false);

    GistDetailsInteractor(@NonNull GistService service) {
        this.service = service;
    }


    public void fetch(@NonNull String gistId, @NonNull FetchCallback<GistDetails> callback) {
        Log.v(TAG, "fetch for " + gistId);
        if (!isFetching()) {
            isFetching.set(true);
            service.getGistById(gistId).enqueue(new Callback<GistDetails>() {
                @Override
                public void onResponse(@NonNull Call<GistDetails> call, @NonNull Response<GistDetails> response) {
                    Log.v(TAG, "Fetch response: " + response.code());
                    isFetching.set(false);
                    if (response.isSuccessful()) {
                        GistDetails gist = response.body();
                        callback.onDataFetched(gist);
                    } else {
                        Log.e(TAG, "Failed to fetch gists: " + response.code());
                        callback.onDataFetched(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GistDetails> call, @NonNull Throwable t) {
                    isFetching.set(false);
                    Log.e(TAG, "Failed to fetch gists", t);
                    callback.onDataFetched(null);
                }
            });
        }
    }


    public boolean isFetching() {
        return isFetching.get();
    }
}