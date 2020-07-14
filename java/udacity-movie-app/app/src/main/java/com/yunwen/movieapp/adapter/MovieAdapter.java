package com.yunwen.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.yunwen.movieapp.activities.DetailsActivity;
import com.yunwen.movieapp.R;
import com.yunwen.movieapp.constant.Constant;
import com.yunwen.movieapp.data.Movie;
import com.yunwen.movieapp.data.TmdbMovie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context mContext;
    private List<TmdbMovie> movieList;
    final private ListItemClickListener mOnClickListener;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieAdapter(Context mContext, List<TmdbMovie> movieList, ListItemClickListener listener){
        this.mContext = mContext;
        this.movieList = movieList;
        mOnClickListener = listener;
    }

    public void setMovieList(List<TmdbMovie> movieList){
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_item_list, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder viewHolder, int position){
        String poster = Constant.MOVIEDB_LARGE_POSTER_URL + movieList.get(position).getPosterPath();
        Picasso.get()
                .load(poster)
                .placeholder(R.drawable.ic_favorite_white_24dp)
                .into(viewHolder.movieImage);
    }

    @Override
    public int getItemCount(){
        return movieList == null ? 0 : movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView movieImage;

        public MovieViewHolder(View view){
            super(view);
            movieImage = (ImageView) view.findViewById(R.id.img_movie);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION){
                TmdbMovie tmdbMovie = movieList.get(clickedPosition);
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, tmdbMovie);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
