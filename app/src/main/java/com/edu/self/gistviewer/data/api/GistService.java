package com.edu.self.gistviewer.data.api;

import android.support.annotation.NonNull;

import com.edu.self.gistviewer.data.model.Gist;
import com.edu.self.gistviewer.data.model.GistDetails;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GistService {
    @GET("gists/public")
    Call<List<Gist>> getPublicGists(@Query("page") int page, @Query("per_page") int countPerPage);
    // TODO Use RxJava

    @GET("gists/{gist_id}")
    Call<GistDetails> getGistById(@Path("gist_id") @NonNull String gistId);
}