package com.yunwen.movieapp.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yunwen.movieapp.data.TmdbMovie;



@Database(entities = {TmdbMovie.class}, version = 2)
public abstract class TmdbMovieRoomDatabase extends RoomDatabase {

    public abstract TmdbMovieDao tmdbMovieDao();

    private static volatile TmdbMovieRoomDatabase INSTANCE;

    public static TmdbMovieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TmdbMovieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TmdbMovieRoomDatabase.class, "movie_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TmdbMovieDao mDao;

        PopulateDbAsync(TmdbMovieRoomDatabase db) {
            mDao = db.tmdbMovieDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mDao.deleteAll();
            //Video video = new Video("Hello");
            //mDao.insert(word);
            return null;
        }
    }

}
