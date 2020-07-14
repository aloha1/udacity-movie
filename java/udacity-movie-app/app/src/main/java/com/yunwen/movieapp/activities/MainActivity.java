package com.yunwen.movieapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.yunwen.movieapp.R;
import com.yunwen.movieapp.adapter.MovieAdapter;
import com.yunwen.movieapp.constant.Constant;
import com.yunwen.movieapp.data.Movie;
import com.yunwen.movieapp.data.MovieResponse;
import com.yunwen.movieapp.data.TmdbMovie;
import com.yunwen.movieapp.repo.TmdbMovieRepository;
import com.yunwen.movieapp.repo.TmdbMovieViewModel;
import com.yunwen.movieapp.retrofit.MovieDBApi;
import com.yunwen.movieapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String LOG_TAG = "MainActivity";
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private SharedPreferences sharedPreferences;

    private TmdbMovieRepository tmdbMovieRepository;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private static Bundle mBundleRecyclerViewState;
    private List<Movie> movieList;
    private List<TmdbMovie> tmdbMovies = new ArrayList<>();
    private TmdbMovieViewModel tmdbMovieViewModel;

    private AppCompatActivity activity = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_main_activity));
        checkFirstLaunch();
        initViews();
    }

    private void checkFirstLaunch(){
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        Boolean firstStart = prefs.getBoolean("firststart",true);
        if(firstStart) {
            prefs.edit().putBoolean("firststart", false).apply();
            networkCall();
        }
    }

    private void initViews(){
        tmdbMovieRepository = new TmdbMovieRepository(getApplication());
        tmdbMovieViewModel = ViewModelProviders.of(this).get(TmdbMovieViewModel.class);
        setupSharedPreferences();
        initRecyclerView(tmdbMovies);
    }

    private void setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        sharedPreferences.getString(getString(R.string.pref_sort_order_key),
                getString(R.string.pref_favorite));
        loadFromPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void loadFromPreferences(){
        String sorted = sharedPreferences.getString(
                getString(R.string.pref_sort_order_key), getString(R.string.pref_most_popular));
        if(sorted.equals(getString(R.string.pref_most_popular))){
            setTitle(getString(R.string.popular));
            tmdbMovieViewModel.getMostPopular();
        }else if(sorted.equals(getString(R.string.pref_favorite))){
            setTitle(getString(R.string.favorite));
            tmdbMovieViewModel.getFavorite();
        }else if(sorted.equals(getString(R.string.pref_highest_rated))){
            setTitle(getString(R.string.top_rate));
            tmdbMovieViewModel.getTopRated();
        }
    }

    // Updates the screen if the shared preferences change. This method is required when you make a
    // class implement OnSharedPreferenceChangedListener
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_order_key))) {
            loadFromPreferences();
            initRecyclerView(tmdbMovies);
        }
    }

    private void initRecyclerView(List<TmdbMovie> tmdbMovies){
        recyclerView = findViewById(R.id.rv_movie);
        movieAdapter = new MovieAdapter(this, tmdbMovies,this);
        GridLayoutManager gridLayoutManager;

        if (this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT){
            gridLayoutManager = new GridLayoutManager(this,2);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            gridLayoutManager = new GridLayoutManager(this,4);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        if(tmdbMovieViewModel.getListLiveData() != null) {
            tmdbMovieViewModel.getListLiveData().observe(this, new Observer<List<TmdbMovie>>() {
                @Override
                public void onChanged(@Nullable final List<TmdbMovie> tmdbMovies) {
                    // Update the cached copy of the words in the adapter.
                    try {
                        movieAdapter.setMovieList(tmdbMovies);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    //load the data into database
    private void loadMovie(String type){
        try{
            if(Constant.isNetworkConnected(getBaseContext())){
                RetrofitClient Client = new RetrofitClient();
                MovieDBApi apiService = Client.getClient().create(MovieDBApi.class);
                Call<MovieResponse> call;
                if(type.equals(getString(R.string.popular))) {
                    call = apiService.getPopularMovies(Constant.MOVIE_API_KEY);
                }else{
                    call = apiService.getTopRatedMovies(Constant.MOVIE_API_KEY);
                }
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call,
                                           Response<MovieResponse> response) {
                        if(response.isSuccessful()) {
                            List<Movie> movies = response.body().getResults();
                            Log.d(LOG_TAG, movies.size() + " movies to store");
                            for (Movie movie : movies) {
                                TmdbMovie tmdbMovie = convertMovie(movie);
                                tmdbMovieViewModel.insert(tmdbMovie);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.d(LOG_TAG, t.getMessage());
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private TmdbMovie convertMovie(Movie movie){
        TmdbMovie tmdbMovie;
        try {
            tmdbMovie = new TmdbMovie(movie.getId(),
                    movie.getPosterPath(),
                    movie.isAdult(),
                    movie.getOverview(),
                    movie.getReleaseDate(),
                    movie.getOriginalTitle(),
                    movie.getOriginalLanguage(),
                    movie.getTitle(),
                    movie.getBackdropPath(),
                    movie.getPopularity(),
                    movie.getVoteCount(),
                    movie.getVideo(),
                    movie.getVoteAverage(),
                    false,
                    false,
                    false);
            return tmdbMovie;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        try {
            mBundleRecyclerViewState = new Bundle();
            Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
            mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /* restore RecyclerView state */
        try {
            if (mBundleRecyclerViewState != null) {
                Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                recyclerView.getLayoutManager().onRestoreInstanceState(listState);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.sort_by, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void networkCall(){
        loadMovie(getString(R.string.popular));
        loadMovie(getString(R.string.top_rate));
    }

    /**
     * This is where we receive our callback from
     * {@link com.yunwen.movieapp.adapter.MovieAdapter.ListItemClickListener}
     *
     * This callback is invoked when you click on an item in the list.
     *
     * @param clickedItemIndex Index in the list of the item that was clicked.
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
}
