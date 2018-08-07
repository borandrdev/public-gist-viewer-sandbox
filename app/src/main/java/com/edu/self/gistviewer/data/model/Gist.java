package com.edu.self.gistviewer.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Gist {
    private String id;

    @SerializedName("description")
    private String description;

    @SerializedName("owner")
    private User owner;

    public String getId() {
        return id;
    }

    @NonNull
    public String getDescription() {
        return description == null ? "" : description;
    }

    @NonNull
    public String getName() {
        return getId() + (getDescription().isEmpty() ? "" : "\n(" + getDescription() + ")");
    }

    public User getOwner() {
        return owner;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }
}