package com.edu.self.gistviewer.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class GistDetails extends Gist {
    @SerializedName("files")
    private Map<String, GistFile> contents;

    @SerializedName("history")
    private List<CommitDetails> commits;

    public Map<String, GistFile> getContents() {
        return contents;
    }

    public List<CommitDetails> getCommits() {
        return commits;
    }
}