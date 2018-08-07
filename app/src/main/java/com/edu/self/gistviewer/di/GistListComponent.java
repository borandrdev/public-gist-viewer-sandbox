package com.edu.self.gistviewer.di;

import com.edu.self.gistviewer.viewmodels.GistListViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {GistListInteractorModule.class})
public interface GistListComponent {
    void inject(GistListViewModel viewModel);
}