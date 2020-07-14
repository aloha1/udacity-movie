package com.yunwen.movieapp.constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yunwen.movieapp.BuildConfig;

public class Constant {

    public static final String MOVIE_API_KEY = BuildConfig.MOVIE_API_KEY;
    /**
     * The constant MOVIEDB_API_URL.
     */
    public final static String MOVIEDB_API_URL = "https://api.themoviedb.org/3/";

    /**
     * The constant MOVIEDB_SMALL_POSTER_URL.
     */
    public final static String MOVIEDB_SMALL_POSTER_URL = "https://image.tmdb.org/t/p/w185";

    /**
     * The constant MOVIEDB_LARGE_POSTER_URL.
     */
    public final static String MOVIEDB_LARGE_POSTER_URL = "https://image.tmdb.org/t/p/w500";

    /**
     * The constant INTENT_MOVIE_DETAIL.
     */
    public final static String INTENT_MOVIE_DETAIL = "INTENT_MOVIE_DETAIL";

    /**
     * The constant Youtube base url.
     */
    public final static String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
}
