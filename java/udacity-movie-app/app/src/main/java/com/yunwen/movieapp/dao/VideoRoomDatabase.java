package com.yunwen.movieapp.dao;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.yunwen.movieapp.data.Video;


@Database(entities = {Video.class}, version = 1)
public abstract class VideoRoomDatabase extends RoomDatabase {

    public abstract VideoDao videoDao();

    private static volatile VideoRoomDatabase INSTANCE;

    public static VideoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VideoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            VideoRoomDatabase.class, "video_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final VideoDao mDao;

        PopulateDbAsync(VideoRoomDatabase db) {
            mDao = db.videoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            //Video video = new Video("Hello");
            //mDao.insert(word);
            return null;
        }
    }

}
