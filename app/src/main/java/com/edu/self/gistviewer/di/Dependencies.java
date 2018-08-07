package com.edu.self.gistviewer.di;

import android.content.Context;
import android.support.annotation.NonNull;

public class Dependencies {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final Context appContext;

    private final AppComponent appComponent;
    private final GistListComponent gistListComponent;
    private final GistDetailsComponent gistDetailsComponent;

    public Dependencies(@NonNull Context appContext) {
        this.appContext = appContext;

        appComponent = DaggerAppComponent.builder()
                .gistServiceModule(new GistServiceModule())
                .build();
        gistListComponent = appComponent
                .buildGistListComponent(new GistListInteractorModule());

        gistDetailsComponent = appComponent.buildGistDetailsComponent(new GistDetailsInteractorModule());
    }


    @NonNull
    public AppComponent getAppComponent() {
        return appComponent;
    }

    @NonNull
    public GistListComponent getGistListComponent() {
        return gistListComponent;
    }

    @NonNull
    public GistDetailsComponent getGistDetailsComponent() {
        return gistDetailsComponent;
    }
}