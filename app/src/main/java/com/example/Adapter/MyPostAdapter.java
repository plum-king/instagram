package com.example.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Model.Post;
import com.example.login.R;


import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.MyPostViewHolder> {

    private Context context;
    private List<Post> mPosts;

    public MyPostAdapter(Context context, List<Post> mPosts) {
        this.context = context;
        this.mPosts = mPosts;
    }


    @NonNull
    @Override
    public MyPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos, parent, false);

        return new MyPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostViewHolder holder, int position) {
        Post post = mPosts.get(position);
        Glide.with(holder.post_img.getContext())
                .load(post.getPostImg())
                .into(holder.post_img);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class MyPostViewHolder extends RecyclerView.ViewHolder {
        public ImageView post_img;

        public MyPostViewHolder(@NonNull View itemView) {
            super(itemView);

            post_img = itemView.findViewById(R.id.post_img);
        }
    }

}
