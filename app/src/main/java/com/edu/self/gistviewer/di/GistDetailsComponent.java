package com.edu.self.gistviewer.di;

import com.edu.self.gistviewer.viewmodels.GistDetailsViewModel;

import dagger.Subcomponent;

@Subcomponent(modules = {GistDetailsInteractorModule.class})
public interface GistDetailsComponent {
    void inject(GistDetailsViewModel viewModel);
}