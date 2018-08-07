package com.edu.self.gistviewer.data.model;

import com.google.gson.annotations.SerializedName;

public class User {
    private long id;

    @SerializedName("login")
    private String name;

    @SerializedName("avatar_url")
    private String avatarUrl;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}