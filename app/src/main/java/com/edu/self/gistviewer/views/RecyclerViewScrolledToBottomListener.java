package com.edu.self.gistviewer.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewScrolledToBottomListener extends RecyclerView.OnScrollListener {
    private static final int DIRECTION_SCROLL_DOWN = 1;

    private final IScrolledToBottomListener callback;

    private boolean isScrolledToBottomEventFired = true;


    public RecyclerViewScrolledToBottomListener(@NonNull IScrolledToBottomListener callback) {
        this.callback = callback;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

        resetScrollToBottomEventReportedIfIsIdleState(newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        fireScrollToBottomEventIfRequired(recyclerView);
    }

    public void resetScrolledToBottomEventFired() {
        isScrolledToBottomEventFired = false;
    }

    private void fireScrollToBottomEventIfRequired(RecyclerView recyclerView) {
        if (!isScrolledToBottomEventFired && isAtTheListBottom(recyclerView)) {
            isScrolledToBottomEventFired = true;
            callback.onScrolledToBottom();
        }
    }

    public boolean isAtTheListBottom(@NonNull RecyclerView recyclerView) {
        return !recyclerView.canScrollVertically(DIRECTION_SCROLL_DOWN);
    }

    private void resetScrollToBottomEventReportedIfIsIdleState(int newState) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            isScrolledToBottomEventFired = false;
        }
    }

    public interface IScrolledToBottomListener {
        void onScrolledToBottom();
    }
}
