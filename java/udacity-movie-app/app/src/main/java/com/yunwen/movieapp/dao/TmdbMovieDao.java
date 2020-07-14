package com.yunwen.movieapp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.yunwen.movieapp.data.TmdbMovie;


import java.util.List;

@Dao
public interface TmdbMovieDao {

    @Query("SELECT * from movie_table ORDER BY id ASC")
    LiveData<List<TmdbMovie>> getMovies();

    @Query("SELECT * from movie_table ORDER BY popularity DESC")
    LiveData<List<TmdbMovie>> getPopularMovies();

    @Query("SELECT * from movie_table ORDER BY voteAverage DESC")
    LiveData<List<TmdbMovie>> getTopRatedMovies();

    @Query("SELECT *  from movie_table WHERE isFavorite == 1 ORDER BY id ASC")
    LiveData<List<TmdbMovie>> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TmdbMovie tmdbMovie);

    @Query("DELETE FROM movie_table WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM movie_table")
    void deleteAll();

    @Query("SELECT * FROM movie_table WHERE id = :id")
    LiveData<TmdbMovie> loadFavoriteById(int id);
}
