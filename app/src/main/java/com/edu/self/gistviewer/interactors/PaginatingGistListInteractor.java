package com.edu.self.gistviewer.interactors;

import android.support.annotation.NonNull;
import android.util.Log;

import com.edu.self.gistviewer.data.api.GistService;
import com.edu.self.gistviewer.data.model.Gist;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class PaginatingGistListInteractor extends GistListInteractor {
    private static final String TAG = PaginatingGistListInteractor.class.getSimpleName();

    private final List<Gist> gists = new CopyOnWriteArrayList<>();

    private static final int COUNT_PER_PAGE = 30;

    private int nextPageToFetch = 1;
    private int lastFetchedGistsCount = 0;
    private final Set<String> loadedGistIds = new HashSet<>();

    public PaginatingGistListInteractor(@NonNull GistService service) {
        super(service);
    }

    public void fetchFirstGists(@NonNull FetchCallback<List<Gist>> callback) {
        if (gists.isEmpty()) {
            super.fetch(nextPageToFetch, COUNT_PER_PAGE, onFetchedGists(callback));
        }
    }

    public void fetchMore(@NonNull FetchCallback<List<Gist>> callback) {
        super.fetch(nextPageToFetch, COUNT_PER_PAGE, onFetchedGists(callback));
    }

    @NonNull
    private FetchCallback<List<Gist>> onFetchedGists(@NonNull FetchCallback<List<Gist>> callback) {
        return lastFetchedGists -> {
            if ((lastFetchedGists != null) && !lastFetchedGists.isEmpty()) {
                addFetchedGistsToCache(lastFetchedGists);
                callback.onDataFetched(gists);
            } else {
                callback.onDataFetched(null);
            }
        };
    }

    private void addFetchedGistsToCache(@NonNull List<Gist> lastFetchedGists) {
        removeGistsFetchedBefore(lastFetchedGists);
        this.gists.addAll(lastFetchedGists);
        for (Gist gist : lastFetchedGists) {
            if ((gist != null) && (gist.getId() != null)) {
                loadedGistIds.add(gist.getId());
            }
        }
        this.lastFetchedGistsCount = lastFetchedGists.size();
        ++nextPageToFetch;
    }

    // Manual duplication removal is required since sometimes there are new gist created at first page between requests,
    // thus other items are shifted and can be returned again on next page request
    private void removeGistsFetchedBefore(@NonNull List<Gist> lastFetchedGists) {
        for (Iterator<Gist> it = lastFetchedGists.iterator(); it.hasNext(); ) {
            Gist gist = it.next();
            if (gist != null) {
                String id = gist.getId();
                if ((id != null) && loadedGistIds.contains(id)) {
                    Log.i(TAG, "Removed duplicate: " + id);
                    it.remove();
                }
            }
        }
    }

    public int getLastFetchedGistsCount() {
        return lastFetchedGistsCount;
    }

    public void resetCache() {
        loadedGistIds.clear();
        this.gists.clear();
        nextPageToFetch = 1;
        lastFetchedGistsCount = 0;
    }

    @NonNull
    public List<Gist> getCachedGists() {
        return gists;
    }
}