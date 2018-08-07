package com.edu.self.gistviewer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.edu.self.gistviewer.R;
import com.edu.self.gistviewer.data.model.CommitDetails;

import java.util.List;

public class CommitListAdapter extends RecyclerView.Adapter<CommitListAdapter.ViewHolder> {
    private final Context appContext;
    private final List<CommitDetails> commits;


    public CommitListAdapter(@NonNull Context appContext, @NonNull List<CommitDetails> commits) {
        this.appContext = appContext;

        this.commits = commits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_commit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommitDetails commit = commits.get(position);
        bindViewHolderForCommit(holder, commit);
    }

    private void bindViewHolderForCommit(@NonNull ViewHolder holder, @Nullable CommitDetails commit) {
        holder.tvCommitInfo.setText(commit != null ? commit.getMultilineDescription() : null);
        holder.tvCommitAdded.setText(appContext.getString(R.string.tvCommitAdded, commit != null ? commit.getAddedCount() : -1));
        holder.tvCommitDeleted.setText(appContext.getString(R.string.tvCommitDeleted, commit != null ? commit.getDeletedCount() : -1));
    }


    @Override
    public int getItemCount() {
        return commits.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCommitInfo;
        private final TextView tvCommitAdded;
        private final TextView tvCommitDeleted;

        ViewHolder(View itemView) {
            super(itemView);
            tvCommitInfo = itemView.findViewById(R.id.tvCommitInfo);
            tvCommitAdded = itemView.findViewById(R.id.tvCommitAdded);
            tvCommitDeleted = itemView.findViewById(R.id.tvCommitDeleted);
        }
    }
}