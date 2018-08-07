package com.edu.self.gistviewer.di;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.interactors.PaginatingGistListInteractor;

import dagger.Module;
import dagger.Provides;

// TODO Define scope
@Module
public class GistListInteractorModule {
    @Provides
    PaginatingGistListInteractor provide(GistService gistService) {
//        gistService = new DummyGistService(); // TODO Remove DEBUG
        return new PaginatingGistListInteractor(gistService);
    }
}