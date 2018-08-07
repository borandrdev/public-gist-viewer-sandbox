package com.edu.self.gistviewer.data.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GistFile {
    @SerializedName("filename")
    private String name;

    private long size;

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public String getDescription() {
        return getName() + " (" + size + " bytes)";
    }
}