package com.edu.self.gistviewer.interactors;

import android.support.annotation.NonNull;
import android.util.LruCache;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.data.model.GistDetails;

public class CachingGistDetailsInteractor extends GistDetailsInteractor {
    private static final int MAX_LRU_SIZE = 10;

    private final LruCache<String, GistDetails> gistDetailsCache;

    public CachingGistDetailsInteractor(@NonNull GistService service) {
        super(service);
        gistDetailsCache = new LruCache<>(MAX_LRU_SIZE);
    }

    @SuppressWarnings("unused")
    public CachingGistDetailsInteractor(@NonNull GistService service, int maxLruCacheSize) {
        super(service);
        gistDetailsCache = new LruCache<>(maxLruCacheSize > 0 ? maxLruCacheSize : MAX_LRU_SIZE);
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