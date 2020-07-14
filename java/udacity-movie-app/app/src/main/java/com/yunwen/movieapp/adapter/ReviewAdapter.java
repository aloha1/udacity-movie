package com.yunwen.movieapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.yunwen.movieapp.R;
import com.yunwen.movieapp.data.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Context mContext;
    private List<Review> reviews;

    public ReviewAdapter(Context mContext, List<Review> reviews){
        this.mContext = mContext;
        this.reviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.review_item_list, viewGroup, false);
        return new ReviewViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ReviewViewHolder viewHolder, int position){
        String author = reviews.get(position).getAuthor();
        String content = reviews.get(position).getContent();
        viewHolder.tvReview.setText(author );
        viewHolder.expTv1.setText(content);
    }

    @Override
    public int getItemCount(){
        return reviews == null ? 0 : reviews.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView tvReview;
        public ExpandableTextView expTv1;
        public ReviewViewHolder(View view){
            super(view);
            tvReview = view.findViewById(R.id.tv_review_content);
            expTv1 =  view.findViewById(R.id.expand_text_view);
        }

        @Override
        public void onClick(View view) {
        }
    }

}
