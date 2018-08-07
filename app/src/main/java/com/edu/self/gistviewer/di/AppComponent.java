package com.edu.self.gistviewer.di;

import com.edu.self.gistviewer.viewmodels.GistDetailsViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GistServiceModule.class, GistDetailsInteractorModule.class})
public interface AppComponent {
    GistListComponent buildGistListComponent(GistListInteractorModule module);

    void inject(GistDetailsViewModel viewModel);
}