package com.edu.self.gistviewer.interactors;

import android.support.annotation.NonNull;

public interface DataFetcher<T> extends DataFetching {
    void fetch(@NonNull FetchCallback<T> callback);
}