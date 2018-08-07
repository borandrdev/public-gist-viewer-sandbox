package com.edu.self.gistviewer.di;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GistServiceModule.class})
public interface AppComponent {
    GistListComponent buildGistListComponent(GistListInteractorModule module);

    GistDetailsComponent buildGistDetailsComponent(GistDetailsInteractorModule module);
}