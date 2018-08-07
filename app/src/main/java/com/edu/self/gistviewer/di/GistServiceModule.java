package com.edu.self.gistviewer.di;

import com.edu.self.gistviewer.data.api.GistService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class GistServiceModule {
    @Singleton
    @Provides
    public GistService provide() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());
        return builder
                .build()
                .create(GistService.class);
    }
}