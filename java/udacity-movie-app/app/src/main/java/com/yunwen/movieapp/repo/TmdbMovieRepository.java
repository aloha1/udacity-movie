package com.yunwen.movieapp.repo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.yunwen.movieapp.dao.TmdbMovieDao;
import com.yunwen.movieapp.dao.TmdbMovieRoomDatabase;
import com.yunwen.movieapp.dao.VideoDao;
import com.yunwen.movieapp.dao.VideoRoomDatabase;
import com.yunwen.movieapp.data.TmdbMovie;
import com.yunwen.movieapp.data.Video;

import java.util.List;

public class TmdbMovieRepository {

    private TmdbMovieDao tmdbMovieDao;
    private LiveData<List<TmdbMovie>> listLiveData;
    private LiveData<TmdbMovie> movieLiveData;

    public TmdbMovieRepository(Application application) {
        TmdbMovieRoomDatabase db = TmdbMovieRoomDatabase.getDatabase(application);
        tmdbMovieDao = db.tmdbMovieDao();

    }

    public LiveData<TmdbMovie> loadFavoriteById(int id){
        movieLiveData = tmdbMovieDao.loadFavoriteById(id);
        return movieLiveData;
    }

    public LiveData<List<TmdbMovie>> getListLiveData() {
        listLiveData = tmdbMovieDao.getMovies();
        try {
            Log.d("repo", "size of All: " + listLiveData.getValue().size());
        }catch (Exception e){
            e.printStackTrace();
        }
        return listLiveData;
    }

    public LiveData<List<TmdbMovie>> getMostPopular() {
        try {
            Log.d("repo", "size of pop: " + tmdbMovieDao.getPopularMovies().getValue().size());
        }catch (Exception e){
            e.printStackTrace();
        }
        listLiveData = tmdbMovieDao.getPopularMovies();
        return listLiveData;
    }

    public LiveData<List<TmdbMovie>> getTopRated() {
        try {
            Log.d("repo", "size of pop: " + tmdbMovieDao.getTopRatedMovies().getValue().size());
        }catch (Exception e){
            e.printStackTrace();
        }
        listLiveData = tmdbMovieDao.getTopRatedMovies();
        return listLiveData;
    }

    public LiveData<List<TmdbMovie>> getFavorite() {
        try {
            Log.d("repo", "size of pop: " + tmdbMovieDao.getFavoriteMovies().getValue().size());
        }catch (Exception e){
            e.printStackTrace();
        }
        return tmdbMovieDao.getFavoriteMovies();
    }

    public void insert(TmdbMovie tmdbMovie) {
        new insertAsyncTask(tmdbMovieDao).execute(tmdbMovie);
    }

    private static class insertAsyncTask extends AsyncTask<TmdbMovie, Void, Void> {

        private TmdbMovieDao mAsyncTaskDao;

        insertAsyncTask(TmdbMovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TmdbMovie... params) {
            for(TmdbMovie tmdbMovie:params) {
                mAsyncTaskDao.insert(tmdbMovie);
            }
            return null;
        }
    }
}
