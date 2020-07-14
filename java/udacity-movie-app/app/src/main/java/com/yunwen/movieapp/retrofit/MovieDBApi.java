package com.yunwen.movieapp.retrofit;

import com.yunwen.movieapp.data.MovieResponse;
import com.yunwen.movieapp.data.ReviewResponse;
import com.yunwen.movieapp.data.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBApi {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getMovieReview(@Path("movie_id") int id, @Query("api_key") String apiKey);
}
