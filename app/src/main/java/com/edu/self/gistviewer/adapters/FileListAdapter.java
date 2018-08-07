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
import com.edu.self.gistviewer.data.model.GistFile;

import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private final Context appContext;
    private final List<GistFile> files;


    public FileListAdapter(@NonNull Context appContext, @NonNull List<GistFile> files) {
        this.appContext = appContext;
        this.files = files;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GistFile file = files.get(position);
        bindViewHolderForFile(holder, file);
    }

    private void bindViewHolderForFile(@NonNull ViewHolder holder, @Nullable GistFile file) {
        String fileInfo = file != null ? file.getDescription() : null;
        holder.tvFileInfo.setText(appContext.getString(R.string.tvFileInfo, fileInfo));
    }


    @Override
    public int getItemCount() {
        return files.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFileInfo;

        ViewHolder(View itemView) {
            super(itemView);
            tvFileInfo = itemView.findViewById(R.id.tvFileInfo);
        }
    }
}