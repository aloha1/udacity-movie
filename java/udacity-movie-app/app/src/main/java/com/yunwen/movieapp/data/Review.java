package com.yunwen.movieapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class Review {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    private String author;
    private String content;
    private String url;


    public Review(@NonNull String id, String author, String content,
                 String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return this.url;
    }

}
