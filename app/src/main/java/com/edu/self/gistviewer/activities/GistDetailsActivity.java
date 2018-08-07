package com.edu.self.gistviewer.activities;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edu.self.gistviewer.R;
import com.edu.self.gistviewer.adapters.CommitListAdapter;
import com.edu.self.gistviewer.adapters.FileListAdapter;
import com.edu.self.gistviewer.data.model.CommitDetails;
import com.edu.self.gistviewer.data.model.GistFile;
import com.edu.self.gistviewer.viewmodels.GistDetailsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GistDetailsActivity extends AppCompatActivity {
    @SuppressWarnings("unused")
    private static final String TAG = GistDetailsActivity.class.getSimpleName();
    private static final String EXTRA_STR_GIST_ID = "gist_id";

    private View prgLoading;

    private GistDetailsViewModel viewModel;
    private TextView tvGistName;
    private TextView tvUserName;
    private RecyclerView rvCommits;
    private RecyclerView rvFiles;


    public static Intent getActivityIntentWithExtras(@NonNull Activity currentActivity, @NonNull String gistId) {
        Intent activityIntent = new Intent(currentActivity, GistDetailsActivity.class);
        activityIntent.putExtra(EXTRA_STR_GIST_ID, gistId);
        return activityIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gist_details);
        tvUserName = findViewById(R.id.tvUserName);
        tvGistName = findViewById(R.id.tvGistName);
        rvCommits = findViewById(R.id.rvCommits);
        rvFiles = findViewById(R.id.rvFiles);
        prgLoading = findViewById(R.id.prgLoading);

        initView();
        initViewModel();
        initObservers();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GistDetailsViewModel.class);
        String gistId = (getIntent() != null ? getIntent().getStringExtra(EXTRA_STR_GIST_ID) : null);
        viewModel.getGistDetailsForId(gistId);
    }

    private void initView() {
        initRecyclerView(rvCommits);
        initRecyclerView(rvFiles);
    }

    private void initRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayout.VERTICAL);
        rvCommits.addItemDecoration(dividerItemDecoration);
        populateCommitsList(null);
    }

    private void initObservers() {
        viewModel.isLoading().observe(this, this::updateProgressBarVisibility);
        viewModel.getGistName().observe(this, gistName -> setTextForTextView(gistName, tvGistName));
        viewModel.getOwner().observe(this, ownerName -> setTextForTextView(ownerName, tvUserName));
        viewModel.getCommits().observe(this, this::populateCommitsList);
        viewModel.getFiles().observe(this, this::populateFilesList);
    }

    private void populateCommitsList(@Nullable List<CommitDetails> commits) {
        if (commits != null) {
            CommitListAdapter adapter = new CommitListAdapter(getApplicationContext(), commits);
            rvCommits.setAdapter(adapter);
        }
    }

    private void populateFilesList(@Nullable Map<String, GistFile> files) {
        if (files != null) {
            ArrayList<GistFile> filesList = new ArrayList<>(files.values());
            FileListAdapter adapter = new FileListAdapter(getApplicationContext(), filesList);
            rvFiles.setAdapter(adapter);
        }
    }

    private void setTextForTextView(@Nullable String text, @Nullable TextView textView) {
        if ((text != null) && (textView != null)) {
            textView.setText(text);
        }
    }


    private void updateProgressBarVisibility(Boolean isLoading) {
        if ((prgLoading != null) && (isLoading != null)) {
            prgLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        }
    }
}
