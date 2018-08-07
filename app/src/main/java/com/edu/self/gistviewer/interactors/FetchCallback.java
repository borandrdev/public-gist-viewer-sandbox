package com.edu.self.gistviewer.interactors;

import android.support.annotation.Nullable;

public interface FetchCallback<T> {
    void onDataFetched(@Nullable T data);
}