package com.edu.self.gistviewer.di;

import android.support.annotation.NonNull;
import android.util.LruCache;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.data.model.GistDetails;
import com.edu.self.gistviewer.interactors.CachingGistDetailsInteractor;
import com.edu.self.gistviewer.interactors.GistDetailsInteractor;

import dagger.Module;
import dagger.Provides;


@Module
public class GistDetailsInteractorModule {
    private static final int MAX_GIST_DETAILS_CACHE_SIZE = 10;

    private final LruCache<String, GistDetails> gistDetailsCache;

    GistDetailsInteractorModule() {
        this.gistDetailsCache = new LruCache<>(MAX_GIST_DETAILS_CACHE_SIZE);
    }

    GistDetailsInteractorModule(@NonNull LruCache<String, GistDetails> gistDetailsCache) {
        this.gistDetailsCache = gistDetailsCache;
    }


    @Provides
    GistDetailsInteractor provide(@NonNull GistService gistService) {
        return new CachingGistDetailsInteractor(gistService, gistDetailsCache);
    }
}