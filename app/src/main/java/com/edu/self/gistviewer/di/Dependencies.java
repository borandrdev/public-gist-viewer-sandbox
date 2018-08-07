package com.edu.self.gistviewer.di;

import android.content.Context;
import android.support.annotation.NonNull;

public class Dependencies {
    private final Context appContext;
    private final AppComponent appComponent;
    private final GistListComponent gistListComponent;

    public Dependencies(@NonNull Context appContext) {
        this.appContext = appContext;

        appComponent = DaggerAppComponent.builder()
                .gistServiceModule(new GistServiceModule())
                .gistDetailsInteractorModule(new GistDetailsInteractorModule())
                .build();
        gistListComponent = appComponent
                .buildGistListComponent(new GistListInteractorModule());
    }


    @NonNull
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @NonNull
    public GistListComponent getGistListComponent() {
        return gistListComponent;
    }
}