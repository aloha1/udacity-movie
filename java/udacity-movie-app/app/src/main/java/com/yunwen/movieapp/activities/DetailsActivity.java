package com.yunwen.movieapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yunwen.movieapp.R;
import com.yunwen.movieapp.adapter.ReviewAdapter;
import com.yunwen.movieapp.adapter.VideoAdapter;
import com.yunwen.movieapp.constant.Constant;
import com.yunwen.movieapp.data.Review;
import com.yunwen.movieapp.data.ReviewResponse;
import com.yunwen.movieapp.data.TmdbMovie;
import com.yunwen.movieapp.data.Video;
import com.yunwen.movieapp.data.VideoResponse;
import com.yunwen.movieapp.repo.TmdbMovieRepository;
import com.yunwen.movieapp.repo.TmdbMovieViewModel;
import com.yunwen.movieapp.retrofit.MovieDBApi;
import com.yunwen.movieapp.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements VideoAdapter.ListItemClickListener {

    private final static String LOG_TAG = "DetailsActivity";
    private TmdbMovie movie;
    TmdbMovieRepository tmdbMovieRepository;
    TmdbMovieViewModel tmdbMovieViewModel;

    String poster, thumbnail, movieName, synopsis, rating, dateOfRelease,movieDuration;
    boolean isFavorite;
    Integer runtime;
    int movie_id;

    private Button btnAddFavorite;
    private RecyclerView rvTrailer;
    private RecyclerView rvReview;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle(R.string.title_detail_activity);
        initViews();
    }

    private void initViews(){
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            movie = (TmdbMovie) intentThatStartedThisActivity.getParcelableExtra(Intent.EXTRA_TEXT);
        }
        ImageView imagePoster = findViewById(R.id.iv_poster);
        TextView textTitle = findViewById(R.id.tv_title);

        btnAddFavorite = findViewById(R.id.btn_favorite);
        rvTrailer = findViewById(R.id.rv_trailer);
        rvReview = findViewById(R.id.rv_review);

        TextView plotSynopsis = (TextView) findViewById(R.id.tv_description);
        TextView userRating = (TextView) findViewById(R.id.tv_rate);
        TextView releaseDate = (TextView) findViewById(R.id.tv_year);
        TextView duration = (TextView) findViewById(R.id.tv_duration);

        try {
            poster = Constant.MOVIEDB_LARGE_POSTER_URL + movie.getPosterPath();
            thumbnail = movie.getPosterPath();
            movieName = movie.getOriginalTitle();
            synopsis = movie.getOverview();
            rating = movie.getVoteAverage() + "/10";
            dateOfRelease = movie.getReleaseDate();
            movie_id = movie.getId();
            loadTrailer(movie_id);
            loadReview(movie_id);
        }catch (Exception e){
            e.printStackTrace();
        }

        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.default_poster)
                .into(imagePoster);
        textTitle.setText(movieName);
        plotSynopsis.setText(synopsis);
        userRating.setText(rating);
        releaseDate.setText(dateOfRelease);
    }

    private void checkFavorite(int id){
        tmdbMovieRepository = new TmdbMovieRepository(getApplication());
        try {
            if (movie.getIsFavorite()) {
                changeFavorite();
            } else {
                addToFavorite();
            }
        }catch (Exception e){
            e.printStackTrace();
            addToFavorite();
        }
    }

    //not favorite
    private void addToFavorite(){
        btnAddFavorite.setText(R.string.mark_favorite);
        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setIsFavorite(true);
                tmdbMovieRepository.insert(movie);
                btnAddFavorite.setText(R.string.delete_favorite);
            }
        });
    }

    //it is favorite
    private void changeFavorite(){
        btnAddFavorite.setText(R.string.delete_favorite);
        btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movie.setIsFavorite(false);
                tmdbMovieRepository.insert(movie);
                btnAddFavorite.setText(R.string.mark_favorite);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        try {
            checkFavorite(movie.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadTrailer(int movie_id){
        try{
            if(Constant.isNetworkConnected(getBaseContext())){
                RetrofitClient Client = new RetrofitClient();
                MovieDBApi apiService = Client.getClient().create(MovieDBApi.class);
                Call<VideoResponse> call = apiService.getMovieTrailer(movie_id,Constant.MOVIE_API_KEY);
                call.enqueue(new Callback<VideoResponse>() {
                    @Override
                    public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                        if(response.isSuccessful()) {
                            List<Video> trailers = response.body().getResults();
                            rvTrailer.setLayoutManager(
                                    new LinearLayoutManager(DetailsActivity.this,
                                            LinearLayoutManager.VERTICAL,
                                            false));
                            VideoAdapter adapter = new VideoAdapter(DetailsActivity.this,
                                    trailers, DetailsActivity.this);
                            rvTrailer.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            rvTrailer.smoothScrollToPosition(0);
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoResponse> call, Throwable t) {
                        Log.d(LOG_TAG, t.getMessage());
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadReview(int movie_id){
        try{
            if(Constant.isNetworkConnected(getBaseContext())){
                RetrofitClient Client = new RetrofitClient();
                MovieDBApi apiService = Client.getClient().create(MovieDBApi.class);
                Call<ReviewResponse> call = apiService.getMovieReview(movie_id,Constant.MOVIE_API_KEY);
                call.enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        List<Review> reviews = response.body().getResults();
                        rvReview.setLayoutManager(
                                new LinearLayoutManager(DetailsActivity.this,
                                        LinearLayoutManager.VERTICAL,
                                        false));
                        ReviewAdapter adapter = new ReviewAdapter(DetailsActivity.this,
                                reviews);
                        rvReview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        rvReview.smoothScrollToPosition(0);
                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                        Log.d(LOG_TAG, t.getMessage());
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }
}
