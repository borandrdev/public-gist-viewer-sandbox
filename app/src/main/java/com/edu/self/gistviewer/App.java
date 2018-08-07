package com.edu.self.gistviewer;

import android.app.Application;
import android.support.annotation.NonNull;

import com.edu.self.gistviewer.di.Dependencies;

public class App extends Application {
    private final Dependencies dependencies;

    public App() {
        super();
        dependencies = new Dependencies(this);
    }

    @NonNull
    public Dependencies getDependencies() {
        return dependencies;
    }
}