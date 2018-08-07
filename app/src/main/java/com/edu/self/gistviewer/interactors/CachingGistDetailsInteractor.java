package com.edu.self.gistviewer.interactors;

import android.support.annotation.NonNull;
import android.util.LruCache;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.data.model.GistDetails;

public class CachingGistDetailsInteractor extends GistDetailsInteractor {

    private final LruCache<String, GistDetails> gistDetailsCache;

    public CachingGistDetailsInteractor(@NonNull GistService service, @NonNull LruCache<String, GistDetails> gistDetailsCache) {
        super(service);
        this.gistDetailsCache = gistDetailsCache;
    }


    @Override
    public void fetch(@NonNull String gistId, @NonNull FetchCallback<GistDetails> callback) {
        GistDetails cachedDetails = gistDetailsCache.get(gistId);
        if (cachedDetails == null) {
            super.fetch(gistId, fetchedDetails -> {
                if (fetchedDetails != null) {
                    gistDetailsCache.put(gistId, fetchedDetails);
                }
                callback.onDataFetched(fetchedDetails);
            });
        } else {
            callback.onDataFetched(cachedDetails);
        }
    }
}