package com.edu.self.gistviewer.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.edu.self.gistviewer.R;
import com.edu.self.gistviewer.adapters.GistListAdapter;
import com.edu.self.gistviewer.data.model.Gist;
import com.edu.self.gistviewer.viewmodels.GistListViewModel;
import com.edu.self.gistviewer.views.RecyclerViewScrolledToBottomListener;

import java.util.List;

public class GistListActivity extends AppCompatActivity implements GistListAdapter.IItemClickListener<Gist>, RecyclerViewScrolledToBottomListener.IScrolledToBottomListener {
    private static final String TAG = GistListActivity.class.getSimpleName();

    private View prgLoading;
    private RecyclerView rvGists;

    private GistListViewModel viewModel;
    private final RecyclerViewScrolledToBottomListener scrolledToBottomListener = new RecyclerViewScrolledToBottomListener(this);
    private Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gist_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        prgLoading = findViewById(R.id.prgLoading);
        rvGists = findViewById(R.id.rvGists);

        viewModel = ViewModelProviders.of(this).get(GistListViewModel.class);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, R.string.msg_refresh_list, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btnRefresh, clickedView -> resetFetchedData()).show());

        initView();
        initObservers();
    }

    private void resetFetchedData() {
        rvGists.setAdapter(null);
        viewModel.resetCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rvGists != null) {
            rvGists.removeOnScrollListener(scrolledToBottomListener);
        }
        dismissSnackbar();
    }

    private void dismissSnackbar() {
        if (snackbar != null) {
            snackbar.dismiss();
        }
    }


    private void initView() {
        rvGists.setLayoutManager(new LinearLayoutManager(this));
        rvGists.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        rvGists.addOnScrollListener(scrolledToBottomListener);
    }

    private void initObservers() {
        viewModel.getGists().observe(this, this::onGistListUpdated);
        viewModel.isLoading().observe(this, this::updateProgressBarVisibility);
        viewModel.getLastFetchedGistsCount().observe(this, this::onMoreGistsFetched);
    }

    private void updateProgressBarVisibility(Boolean isLoading) {
        if ((prgLoading != null) && (isLoading != null)) {
            prgLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
    }

    private void onGistListUpdated(@Nullable List<Gist> gists) {
        if (gists == null) {
            showRetryOnError();
        }
        populateGistList(gists);
    }

    private void populateGistList(@Nullable List<Gist> gists) {
        GistListAdapter adapter;
        if ((rvGists.getAdapter() == null) || (rvGists.getAdapter().getItemCount() == 0)) {
            if (gists != null) {
                adapter = new GistListAdapter(gists, this);
                rvGists.setAdapter(adapter);
            }
        }
    }

    private void showRetryOnError() {
        snackbar = Snackbar.make(rvGists, R.string.err_unknown, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.btnRetry, clickedView -> {
                    dismissSnackbar();
                    resetFetchedData();
                });
        snackbar.show();
    }

    private void onMoreGistsFetched(@Nullable Integer lastFetchedGistsCount) {
        RecyclerView.Adapter adapter = rvGists.getAdapter();
        if ((adapter != null) && (lastFetchedGistsCount != null) && (lastFetchedGistsCount > 0)) {
            int positionStart = adapter.getItemCount() - lastFetchedGistsCount;
            if (positionStart >= 0) {
                if (positionStart > 0) {
                    adapter.notifyItemChanged(positionStart - 1);
                }
                adapter.notifyItemRangeInserted(positionStart, lastFetchedGistsCount);
            }
            Log.i(TAG, "Gists: " + adapter.getItemCount());
        }
        scrolledToBottomListener.resetScrolledToBottomEventFired();
    }

    private void openDetailsScreenForGist(@NonNull Gist clickedItem, @NonNull View clickedItemView) {
        String gistId = clickedItem.getId();
        if (gistId != null) {
            Intent activityIntent = GistDetailsActivity.getActivityIntentWithExtras(this, gistId);
            Pair<View, String> pair1 = Pair.create(clickedItemView.findViewById(R.id.tvGistName), "gistName");
            Pair<View, String> pair2 = Pair.create(clickedItemView.findViewById(R.id.tvUserName), "userName");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2);
            ActivityCompat.startActivity(this, activityIntent, options.toBundle());
        }
    }


// GistListAdapter.IItemClickListener<Gist> ========================================================

    @Override
    public void onItemClicked(@NonNull Gist clickedItem, @NonNull View clickedItemView) {
        openDetailsScreenForGist(clickedItem, clickedItemView);
    }


// GistListAdapter.IItemClickListener<Gist> ========================================================

    @Override
    public void onScrolledToBottom() {
        viewModel.fetchMoreGists();
    }
}
