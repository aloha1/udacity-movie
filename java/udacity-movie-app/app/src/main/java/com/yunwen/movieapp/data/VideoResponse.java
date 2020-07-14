package com.yunwen.movieapp.data;

import java.util.List;

public class VideoResponse {
    private String id;
    private List<Video> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
