package com.edu.self.gistviewer.data.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings({"unused", "WeakerAccess"})
public class CommitDetails {
    @SerializedName("user")
    private User user;

    @SerializedName("committed_at")
    private String date;

    private String version;

    @SerializedName("change_status")
    private ChangeStatus changeStatus;

    @NonNull
    public String getUserName() {
        return user != null ? user.getName() : "";
    }

    public long getDeletedCount() {
        return changeStatus != null ? changeStatus.deletedCount : -1;
    }

    public long getAddedCount() {
        return changeStatus != null ? changeStatus.addedCount : -1;
    }

    public String getMultilineDescription() {
        return "by " + getUserName() + "\nat " + date + "\nver: " + version;
    }

    private static class ChangeStatus {
        @SerializedName("deletions")
        private long deletedCount;
        @SerializedName("additions")
        private long addedCount;
    }
}