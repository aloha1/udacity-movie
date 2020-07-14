package com.yunwen.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yunwen.movieapp.R;
import com.yunwen.movieapp.constant.Constant;
import com.yunwen.movieapp.data.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.TrailerViewHolder>{

    private Context mContext;
    private List<Video> trailerList;
    final private ListItemClickListener mOnClickListener;

    /**
     * The interface that receives onClick messages.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public VideoAdapter(Context mContext, List<Video> movieList, ListItemClickListener listener){
        this.mContext = mContext;
        this.trailerList = movieList;
        mOnClickListener = listener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.trailer_item_list, viewGroup, false);
        return new TrailerViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final TrailerViewHolder viewHolder, int position){
        String name = trailerList.get(position).getName();
        viewHolder.trailerName.setText(name);
    }

    @Override
    public int getItemCount(){
        return trailerList == null ? 0 : trailerList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public ImageView movieImage;
        public TextView trailerName;

        public TrailerViewHolder(View view){
            super(view);
            movieImage = (ImageView) view.findViewById(R.id.iv_play);
            trailerName = (TextView) view.findViewById(R.id.tv_trailer_content);
            movieImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION){
                Video video = trailerList.get(clickedPosition);
                String url = Constant.YOUTUBE_URL + video.getKey();
                openYouTube(url);
            }
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
    private void openYouTube(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setPackage("com.google.android.youtube");
        mContext.startActivity(intent);
    }
}
