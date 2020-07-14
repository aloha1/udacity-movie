package com.yunwen.movieapp.data;

import java.util.List;

public class ReviewResponse {
    private String id;
    private List<Review> results;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Review> getResults() {
        return results;
    }

    public void setResults(List<Review> results) {
        this.results = results;
    }
}
