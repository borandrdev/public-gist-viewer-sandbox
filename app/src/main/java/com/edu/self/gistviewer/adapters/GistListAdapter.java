package com.edu.self.gistviewer.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edu.self.gistviewer.R;
import com.edu.self.gistviewer.data.model.Gist;
import com.edu.self.gistviewer.data.model.User;

import java.util.List;

public class GistListAdapter extends RecyclerView.Adapter<GistListAdapter.ViewHolder> {

    private final List<Gist> gists;
    @Nullable
    private final IItemClickListener<Gist> gistItemClickListener;

    public GistListAdapter(@NonNull List<Gist> gists, @Nullable IItemClickListener<Gist> gistItemClickListener) {
        this.gists = gists;
        this.gistItemClickListener = gistItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_gist, parent, false);
        return new ViewHolder(view, gistItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Gist gist = gists.get(position);
        if (gist != null) {
            int index = position + 1;
            bindViewHolderForGist(holder, gist, index);
        }
    }


    private void bindViewHolderForGist(@NonNull ViewHolder holder, @NonNull Gist gist, int position) {
        User user = gist.getOwner();
        holder.setGist(gist);
        holder.tvGistName.setText(gist.getName());
        holder.tvIndex.setText(String.valueOf(position));
        if (user != null) {
            bindViewHolderForGistOwner(holder, user);
        }
    }

    private void bindViewHolderForGistOwner(@NonNull ViewHolder holder, @NonNull User user) {
        holder.tvUserName.setText(user.getName());
        if (user.getAvatarUrl() != null) {
            Glide.with(holder.imgAvatar).load(user.getAvatarUrl()).into(holder.imgAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return gists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgAvatar;
        private final TextView tvUserName;
        private final TextView tvGistName;
        private final TextView tvIndex;
        @Nullable
        private Gist gist;

        ViewHolder(View itemView, @Nullable IItemClickListener<Gist> gistItemClickListener) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvGistName = itemView.findViewById(R.id.tvGistName);
            tvIndex = itemView.findViewById(R.id.tvIndex);
            if (gistItemClickListener != null) {
                itemView.setOnClickListener(clickedView -> {
                    if (gist != null) {
                        gistItemClickListener.onItemClicked(gist, itemView);
                    }
                });
            }
        }

        public void setGist(@Nullable Gist gist) {
            this.gist = gist;
        }
    }

    public interface IItemClickListener<T> {
        void onItemClicked(@NonNull T clickedItem, @NonNull View clickedItemView);
    }
}