package com.yunwen.movieapp.repo;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.yunwen.movieapp.data.TmdbMovie;

import java.util.List;

public class TmdbMovieViewModel extends AndroidViewModel {

    private TmdbMovieRepository mRepository;
    private LiveData<List<TmdbMovie>> listLiveData;
    private LiveData<TmdbMovie> movieLiveData;

    public TmdbMovieViewModel(Application application) {
        super(application);
        mRepository = new TmdbMovieRepository(application);

    }

    public  LiveData<TmdbMovie> getMovieLiveData() {
        return  movieLiveData;
    }

    public LiveData<List<TmdbMovie>> getListLiveData() {
        return  listLiveData;
    }

    public LiveData<List<TmdbMovie>> getTopRatedData(){
        listLiveData = mRepository.getTopRated();
        return  listLiveData;
    }

    public LiveData<List<TmdbMovie>> getMostPopularData(){
        listLiveData = mRepository.getTopRated();
        return  listLiveData;
    }

    public void getMostPopular() {
        listLiveData =  mRepository.getMostPopular();
    }

    public void getFavorite() {
        listLiveData =  mRepository.getFavorite();
    }


    public void getTopRated() {
        listLiveData =  mRepository.getTopRated();
    }

    public void getAllMovies() {
        listLiveData =  mRepository.getListLiveData();
    }

    public void insert(TmdbMovie tmdbMovie) {
        mRepository.insert(tmdbMovie);
    }
}
