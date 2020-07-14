package com.yunwen.movieapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yunwen.movieapp.data.Video;

import java.util.List;

@Dao
public interface VideoDao {

    @Query("SELECT * from video_table ORDER BY id ASC")
    LiveData<List<Video>> getVideos();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Video word);

    @Query("DELETE FROM video_table")
    void deleteAll();
}
