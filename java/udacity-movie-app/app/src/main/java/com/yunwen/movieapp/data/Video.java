package com.yunwen.movieapp.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "video_table")
public class Video {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;
    private String iso_639_1;
    private String iso_3166_1;
    private String key;
    private String name;
    private String site;
    private Integer size;
    private String type;

    public Video(@NonNull String id, String iso_639_1, String iso_3166_1,
                 String key, String name, String site, Integer size, String type) {
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.iso_3166_1 = iso_3166_1;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    @NonNull
    public String getId() {
        return this.id;
    }

    public String getIso_639_1() {
        return this.iso_639_1;
    }

    public String getIso_3166_1() {
        return this.iso_3166_1;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public Integer getSize() {
        return this.size;
    }

    public String getSite() {
        return this.site;
    }

    public String getType() {
        return this.type;
    }

}
