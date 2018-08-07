package com.edu.self.gistviewer.di;

import android.support.annotation.NonNull;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.interactors.CachingGistDetailsInteractor;
import com.edu.self.gistviewer.interactors.GistDetailsInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class GistDetailsInteractorModule {
    private GistDetailsInteractor gistDetailsInteractor;

    @Singleton
    @Provides
    GistDetailsInteractor provide(@NonNull GistService gistService) {
        if (gistDetailsInteractor == null) {
            gistDetailsInteractor = new CachingGistDetailsInteractor(gistService);
        }
        return gistDetailsInteractor;
    }
}