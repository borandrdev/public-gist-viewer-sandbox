package com.edu.self.gistviewer.data.api;

import android.support.annotation.NonNull;

import com.edu.self.gistviewer.data.model.Gist;
import com.edu.self.gistviewer.data.model.GistDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DummyGistService implements GistService {
    private final AtomicInteger returnedItemsCount = new AtomicInteger(0);

    @Override
    public Call<List<Gist>> getPublicGists(int page, int countPerPage) {
        return new Call<List<Gist>>() {
            @Override
            public Response<List<Gist>> execute() throws IOException {
                throw new IOException("Not used");
            }

            @Override
            public void enqueue(@NonNull Callback<List<Gist>> callback) {
                Response<List<Gist>> dummyGistsResponse = getDummyGistsResponse(countPerPage);
                if (System.currentTimeMillis() % 2 == 1) {
                    callback.onResponse(this, dummyGistsResponse);
                } else {
                    callback.onFailure(this, new IOException());
                }
                returnedItemsCount.set(returnedItemsCount.get() + countPerPage);
            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Gist>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
    }

    @Override
    public Call<GistDetails> getGistById(@NonNull String gistId) {
        return null;
    }

    @NonNull
    private Response<List<Gist>> getDummyGistsResponse(int itemsCount) {
        List<Gist> dummyGists = new ArrayList<>(itemsCount);
        int returnedItemsCount = this.returnedItemsCount.get();
        for (int i = 0; i < itemsCount; i++) {
            Gist gist = new Gist();
            gist.setId("id -> " + String.valueOf(returnedItemsCount + i + 1));
            dummyGists.add(gist);
        }
        return Response.success(dummyGists);
    }
}
